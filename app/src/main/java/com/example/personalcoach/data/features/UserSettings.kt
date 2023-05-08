package com.example.personalcoach.data.features

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.personalcoach.domain.SettingsBundle
import com.example.personalcoach.ui.theme.ExtendedJetCorners
import com.example.personalcoach.ui.theme.ExtendedJetSize
import com.example.personalcoach.ui.theme.ExtendedJetStyle
import com.example.personalcoach.ui.theme.ExtendedJetTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private const val TAG = "USER SETTINGS:"

interface UserSettings{
    val _currentSettings: MutableStateFlow<SettingsBundle>
    val currentSettings: StateFlow<SettingsBundle>
    suspend fun saveSettings(theme: SettingsBundle)

    suspend fun getSettings():SettingsBundle
}

class UserSettingImpl
@Inject constructor
    (
    @ApplicationContext
    context: Context
): UserSettings{

    override val _currentSettings: MutableStateFlow<SettingsBundle> = MutableStateFlow(
        SettingsBundle(
            isDarkMode = true,
            cornerStyle = ExtendedJetCorners.Rounded,
            style = ExtendedJetStyle.Blue,
            textSize = ExtendedJetSize.Medium,
            paddingSize = ExtendedJetSize.Medium
        )
    )
    override val currentSettings: StateFlow<SettingsBundle> = _currentSettings


    private val preferences: SharedPreferences =
                context.getSharedPreferences("main_theme", Context.MODE_PRIVATE)


    override suspend fun saveSettings(
        theme: SettingsBundle
    ){
        Log.e(TAG, "Save settings")
        delay(1000)
        preferences.edit().apply{
            putBoolean("isDarkMode", theme.isDarkMode)
            putString("style", theme.style.toString())
            putString("cornerStyle", theme.cornerStyle.toString())
            putString("paddingSize", theme.paddingSize.toString())
            putString("textSize", theme.textSize.toString())
                .apply()
        }
    }

    override suspend fun getSettings(): SettingsBundle {
        val isDarkMode = preferences.getBoolean("isDarkMode", false)

        val style = when(preferences.getString("style", ExtendedJetStyle.Purple.toString())){
            ExtendedJetStyle.Purple.toString() -> ExtendedJetStyle.Purple
            ExtendedJetStyle.Red.toString() -> ExtendedJetStyle.Red
            ExtendedJetStyle.Orange.toString() -> ExtendedJetStyle.Orange
            ExtendedJetStyle.Blue.toString() -> ExtendedJetStyle.Blue
            else -> ExtendedJetStyle.Purple
        }

        val cornerStyle:ExtendedJetCorners = when(preferences.getString("cornerStyle", ExtendedJetCorners.Rounded.toString())){
            ExtendedJetCorners.Rounded.toString() -> ExtendedJetCorners.Rounded
            ExtendedJetCorners.Float.toString() -> ExtendedJetCorners.Float
            else -> ExtendedJetCorners.Rounded
        }

        val paddingSize = when(preferences.getString("paddingSize", ExtendedJetSize.Medium.toString())){
            ExtendedJetSize.Medium.toString() -> ExtendedJetSize.Medium
            ExtendedJetSize.Small.toString() -> ExtendedJetSize.Small
            ExtendedJetSize.Big.toString() -> ExtendedJetSize.Big
            else -> ExtendedJetSize.Medium
        }

        val textSize = when(preferences.getString("textSize", ExtendedJetSize.Medium.toString())){
            ExtendedJetSize.Medium.toString() -> ExtendedJetSize.Medium
            ExtendedJetSize.Small.toString() -> ExtendedJetSize.Small
            ExtendedJetSize.Big.toString() -> ExtendedJetSize.Big
            else -> ExtendedJetSize.Medium
        }

        println(
            "$isDarkMode" +
                    " $style" +
                    " $cornerStyle" +
                    " $textSize" +
                    " $paddingSize"
        )


        return SettingsBundle(
            isDarkMode,
            textSize,
            paddingSize,
            cornerStyle,
            style
        )
    }


}
