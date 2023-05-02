package com.example.personalcoach.view.bottomNavigation.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalcoach.data.features.LocaleSettingsEventBus
import com.example.personalcoach.ui.theme.*
import com.example.personalcoach.view.bottomNavigation.setting.view.MenuItem
import com.example.personalcoach.view.bottomNavigation.setting.view.MenuItemModel

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
internal fun SettingThemeScreen(){
    val settingsEventBus = LocaleSettingsEventBus.current

    // StateFlow.value should not be called within composition
    val currentSettings = settingsEventBus.currentSettings.collectAsState()

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
                            settingsEventBus.updateDarkMode(!currentSettings.value.isDarkMode)
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
                        settingsEventBus.updateTextSize(
                            when (it) {
                                0 -> ExtendedJetSize.Small
                                1 -> ExtendedJetSize.Medium
                                2 -> ExtendedJetSize.Big
                                else -> throw NotImplementedError("No valid value for this $it")
                            }
                        )
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
                        })
                    ColorCard(color = if (currentSettings.value.isDarkMode) orangeDarkPalette.tintColor else orangeLightPalette.tintColor,
                        onClick = {
                            settingsEventBus.updateStyle(ExtendedJetStyle.Orange)
                        })
                    ColorCard(color = if (currentSettings.value.isDarkMode) blueDarkPalette.tintColor else blueLightPalette.tintColor,
                        onClick = {
                            settingsEventBus.updateStyle(ExtendedJetStyle.Blue)
                        })

                    ColorCard(color = if (currentSettings.value.isDarkMode) redDarkPalette.tintColor else redLightPalette.tintColor,
                        onClick = {
                            settingsEventBus.updateStyle(ExtendedJetStyle.Red)
                        })
                }

            }
        }
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