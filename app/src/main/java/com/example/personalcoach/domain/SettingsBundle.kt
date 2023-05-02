package com.example.personalcoach.domain

import com.example.personalcoach.ui.theme.ExtendedJetCorners
import com.example.personalcoach.ui.theme.ExtendedJetSize
import com.example.personalcoach.ui.theme.ExtendedJetStyle

data class SettingsBundle(
    val isDarkMode: Boolean,
    val textSize: ExtendedJetSize,
    val paddingSize: ExtendedJetSize,
    val cornerStyle: ExtendedJetCorners,
    val style: ExtendedJetStyle
)