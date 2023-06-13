package com.example.personalcoach.data.network.repository

import com.example.personalcoach.data.network.AuthApiService
import com.example.personalcoach.domain.model.user.JwtResponse
import com.example.personalcoach.domain.model.user.LoginRequest
import com.example.personalcoach.domain.pref.AuthenticationPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authApiService: AuthApiService,
) {
    fun login( loginRequest: LoginRequest): Flow<JwtResponse> = flow {
        emit(authApiService.signIn(loginRequest))
    }.flowOn(Dispatchers.IO)
}