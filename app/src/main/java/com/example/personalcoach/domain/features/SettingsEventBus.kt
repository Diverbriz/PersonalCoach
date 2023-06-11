package com.example.personalcoach.domain.features

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.personalcoach.domain.SettingsBundle
import com.example.personalcoach.presenter.ui.theme.ExtendedJetCorners
import com.example.personalcoach.presenter.ui.theme.ExtendedJetSize
import com.example.personalcoach.presenter.ui.theme.ExtendedJetStyle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsEventBus {

    private val _currentSettings: MutableStateFlow<SettingsBundle> = MutableStateFlow(
        SettingsBundle(
            isDarkMode = true,
            cornerStyle = ExtendedJetCorners.Rounded,
            style = ExtendedJetStyle.Blue,
            textSize = ExtendedJetSize.Medium,
            paddingSize = ExtendedJetSize.Medium
        )
    )
    val currentSettings: StateFlow<SettingsBundle> = _currentSettings

    fun updateDarkMode(isDarkMode: Boolean) {
        _currentSettings.value = _currentSettings.value.copy(isDarkMode = isDarkMode)
    }

    fun updateCornerStyle(corners: ExtendedJetCorners) {
        _currentSettings.value = _currentSettings.value.copy(cornerStyle = corners)
    }

    fun updateStyle(style: ExtendedJetStyle) {
        _currentSettings.value = _currentSettings.value.copy(style = style)
    }

    fun updateTextSize(textSize: ExtendedJetSize) {
        _currentSettings.value = _currentSettings.value.copy(textSize = textSize)
    }

    fun updatePaddingSize(paddingSize: ExtendedJetSize) {
        _currentSettings.value = _currentSettings.value.copy(paddingSize = paddingSize)
    }

    fun updateStyle(settingsBundle: SettingsBundle){
        _currentSettings.value = settingsBundle
    }
}

internal val LocaleSettingsEventBus = staticCompositionLocalOf {
    SettingsEventBus()
}