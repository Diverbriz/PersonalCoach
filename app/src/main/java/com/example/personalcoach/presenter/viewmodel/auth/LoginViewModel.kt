package com.example.personalcoach.presenter.viewmodel.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalcoach.data.network.repository.UserRepository
import com.example.personalcoach.domain.model.user.JwtResponse
import com.example.personalcoach.domain.model.user.LoginRequest
import com.example.personalcoach.domain.pref.AuthenticationPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationPreferences: AuthenticationPreferences
): ViewModel() {
    val response: MutableState<AuthState> = mutableStateOf(AuthState.Empty)

    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            userRepository.login(loginRequest)
                .onStart {
                    response.value = AuthState.Loading
                    println(response.value)
                }.catch {
                    response.value = AuthState.Failure(it)
                    println(it)
                }.collect{
                    println("Токен из flow ${ it.token }")
                    response.value = AuthState.Success(it)
                    authenticationPreferences.storeToken("Bearer ${it.token}")
                    println("Токен из преференсев ${ authenticationPreferences.getToken() }")
                }
        }
    }
}

sealed class AuthState {
    class Success(val data: JwtResponse) : AuthState()
    class Failure(val msg: Throwable) : AuthState()
    object Loading:AuthState()
    object Empty: AuthState()
}