package com.example.personalcoach.presenter.view.bottomNavigation.setting

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalcoach.domain.features.LocaleSettingsEventBus
import com.example.personalcoach.domain.features.UserSettingImpl
import com.example.personalcoach.domain.features.UserSettings
import com.example.personalcoach.presenter.ui.theme.*
import com.example.personalcoach.presenter.view.bottomNavigation.setting.view.MenuItem
import com.example.personalcoach.presenter.view.bottomNavigation.setting.view.MenuItemModel
import kotlinx.coroutines.launch


@Composable
fun SettingTabs(){
    val checkedState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(0.1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = ExtendedJetTheme.colors.tintColor,
                    uncheckedColor = ExtendedJetTheme.colors.primaryText,
                    checkmarkColor = ExtendedJetTheme.colors.primaryText,

                    )
            )

            Text(text = if(checkedState.value) "Light theme" else "Dark theme", style = ExtendedJetTheme.typography.heading,
            fontSize = 18.sp)
        }
    }
}

@Composable
internal fun SettingThemeScreen(context: Context){
    val settingsEventBus = LocaleSettingsEventBus.current

    // StateFlow.value should not be called within composition
    val currentSettings = settingsEventBus.currentSettings.collectAsState()

    val coroutineContext = rememberCoroutineScope()

    val userSettings: UserSettings = UserSettingImpl(context)

    Surface(
        color = ExtendedJetTheme.colors.secondaryBackground
    ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
//                TopAppBar(
//                    backgroundColor = ExtendedJetTheme.colors.secondaryBackground,
//                    elevation = 8.dp
//                ) {
//                    Text(
//                        text = ""
//                    )
//                }

                Row(
                    modifier = Modifier.padding(ExtendedJetTheme.shapes.padding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Dark Theme?",
                        color = ExtendedJetTheme.colors.primaryText,
                        style = ExtendedJetTheme.typography.body
                    )
                    Checkbox(
                        checked = currentSettings.value.isDarkMode, onCheckedChange = {
//                            userSettings.getSettings()
                            settingsEventBus.updateDarkMode(!currentSettings.value.isDarkMode)
                            coroutineContext.launch {
                                userSettings.saveSettings(settingsEventBus.currentSettings.value)
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = ExtendedJetTheme.colors.tintColor,
                            uncheckedColor = ExtendedJetTheme.colors.secondaryText
                        )
                    )
                }

                Divider(
                    modifier = Modifier.padding(start = ExtendedJetTheme.shapes.padding),
                    thickness = 0.5.dp,
                    color = ExtendedJetTheme.colors.secondaryText.copy(
                        alpha = 0.3f
                    )
                )

                MenuItem(
                    model = MenuItemModel(
                        title = "Font Size",
                        currentIndex = when (currentSettings.value.textSize) {
                            ExtendedJetSize.Small -> 0
                            ExtendedJetSize.Medium -> 1
                            ExtendedJetSize.Big -> 2
                        },
                        values = listOf(
                            "Small",
                            "Medium",
                            "Big"
                        )
                    ),
                    onItemSelected = {
                        println(ExtendedJetSize.Small.toString())
                        settingsEventBus.updateTextSize(
                            when (it) {
                                0 -> ExtendedJetSize.Small
                                1 -> ExtendedJetSize.Medium
                                2 -> ExtendedJetSize.Big
                                else -> throw NotImplementedError("No valid value for this $it")
                            }
                        )
                        coroutineContext.launch {
                            userSettings.saveSettings(settingsEventBus.currentSettings.value)
                        }
                    }
                )

                MenuItem(
                    model = MenuItemModel(
                        title = "Padding Size",
                        currentIndex = when (currentSettings.value.paddingSize) {
                            ExtendedJetSize.Small -> 0
                            ExtendedJetSize.Medium -> 1
                            ExtendedJetSize.Big -> 2
                        },
                        values = listOf(
                            "Small",
                            "Medium",
                            "Big"
                        )
                    ),
                    onItemSelected = {
                        settingsEventBus.updatePaddingSize(
                            when (it) {
                                0 -> ExtendedJetSize.Small
                                1 -> ExtendedJetSize.Medium
                                2 -> ExtendedJetSize.Big
                                else -> throw NotImplementedError("No valid value for this $it")
                            }
                        )
                        coroutineContext.launch {
                            userSettings.saveSettings(settingsEventBus.currentSettings.value)
                        }
                    }
                )

                MenuItem(
                    model = MenuItemModel(
                        title = "Corner Style",
                        currentIndex = when (currentSettings.value.cornerStyle) {
                            ExtendedJetCorners.Rounded -> 0
                            ExtendedJetCorners.Float -> 1
                        },
                        values = listOf(
                            "Rounded",
                            "Float"
                        )
                    ),
                    onItemSelected = {
                        settingsEventBus.updateCornerStyle(
                            when (it) {
                                0 ->  ExtendedJetCorners.Rounded
                                1 -> ExtendedJetCorners.Float
                                else -> throw NotImplementedError("No valid value for this $it")
                            }
                        )
                        coroutineContext.launch {
                            userSettings.saveSettings(settingsEventBus.currentSettings.value)
                        }
                    }
                )

                Text(text = "Color Sheme", style = ExtendedJetTheme.typography.heading.copy(fontSize = 18.sp), color = ExtendedJetTheme.colors.primaryText,
                modifier = Modifier
                    .padding(ExtendedJetTheme.shapes.padding))

                Row(
                    modifier = Modifier
                        .padding(ExtendedJetTheme.shapes.padding)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ColorCard(color = if (currentSettings.value.isDarkMode) purpleDarkPalette.tintColor else purpleLightPalette.tintColor,
                        onClick = {
                            settingsEventBus.updateStyle(ExtendedJetStyle.Purple)
                            coroutineContext.launch {
                                userSettings.saveSettings(settingsEventBus.currentSettings.value)
                            }
                        })
                    ColorCard(color = if (currentSettings.value.isDarkMode) orangeDarkPalette.tintColor else orangeLightPalette.tintColor,
                        onClick = {
                            settingsEventBus.updateStyle(ExtendedJetStyle.Orange)
                            coroutineContext.launch {
                                userSettings.saveSettings(settingsEventBus.currentSettings.value)
                            }
                        })
                    ColorCard(color = if (currentSettings.value.isDarkMode) blueDarkPalette.tintColor else blueLightPalette.tintColor,
                        onClick = {
                            settingsEventBus.updateStyle(ExtendedJetStyle.Blue)
                            coroutineContext.launch {
                                userSettings.saveSettings(settingsEventBus.currentSettings.value)
                            }
                        })

                    ColorCard(color = if (currentSettings.value.isDarkMode) redDarkPalette.tintColor else redLightPalette.tintColor,
                        onClick = {
                            settingsEventBus.updateStyle(ExtendedJetStyle.Red)
                            coroutineContext.launch {
                                userSettings.saveSettings(settingsEventBus.currentSettings.value)
                            }
                        })
                }

            }
        }
//        LaunchedEffect(Unit){
//            userSettings.saveSettings(settingsEventBus.currentSettings.value)
//        }
    }



@Composable
fun ColorCard(
    color: Color,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .size(60.dp)
            .clickable {
                onClick.invoke()
            },
        backgroundColor = color,
        elevation = 8.dp,
        shape = ExtendedJetTheme.shapes.cornersStyle
    ) {
        
    }

}