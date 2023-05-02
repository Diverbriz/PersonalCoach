package com.example.personalcoach.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SplashViewModel: ViewModel() {

    private val _state = mutableStateOf(UIState())
    val state: State<UIState> = _state


    sealed class UIEvent (){
        object IncrementCounter: UIEvent()
        object ChangeIsAnimated: UIEvent()
    }

    fun onEvent(event: UIEvent){
        when(event){
            is UIEvent.IncrementCounter ->
                _state.value = state.value.copy(
                    counter = state.value.counter + 1
                )
            is UIEvent.ChangeIsAnimated ->
            _state.value = state.value.copy(
                isAnimated = !state.value.isAnimated
            )
        }
    }
}

data class UIState(
    val counter: Int = 0,
    val isAnimated: Boolean = false
)