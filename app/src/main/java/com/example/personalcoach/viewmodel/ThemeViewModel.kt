package com.example.personalcoach.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.personalcoach.ui.theme.ExtendedJetCorners
import com.example.personalcoach.ui.theme.ExtendedJetSize
import com.example.personalcoach.ui.theme.ExtendedJetStyle
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ThemeViewModel(): ViewModel(){

    private val _state = mutableStateOf(ExtendedJetState())
    val state: MutableState<ExtendedJetState> = _state


    sealed class ExtendedJet(){
        class IsDarkTheme(isDark: Boolean)
    }

}

data class ExtendedJetState(
    val style: ExtendedJetStyle = ExtendedJetStyle.Purple,
    val textSize: ExtendedJetSize = ExtendedJetSize.Medium,
    val paddingSize: ExtendedJetSize = ExtendedJetSize.Medium,
    val corners: ExtendedJetCorners = ExtendedJetCorners.Rounded,
    val darkTheme: Boolean = false,
)