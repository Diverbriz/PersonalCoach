package com.example.personalcoach

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.example.personalcoach.data.features.LocaleSettingsEventBus
import com.example.personalcoach.data.features.UserSettingImpl
import com.example.personalcoach.data.features.UserSettings
import com.example.personalcoach.domain.SettingsBundle
import com.example.personalcoach.domain.model.SlideScreen
import com.example.personalcoach.ui.theme.*
import com.example.personalcoach.view.Slider
import com.example.personalcoach.ui.theme.MainTheme
import com.example.personalcoach.view.WelcomeScreen
import com.example.personalcoach.view.auth.LoginScreen
import com.example.personalcoach.view.auth.RegistrationScreen
import com.example.personalcoach.view.auth.VerificationScreen
import com.example.personalcoach.view.bottomNavigation.BottomNav
import com.example.personalcoach.view.bottomNavigation.MainScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userSettings: UserSettings

    lateinit var setting: SettingsBundle

    private val startForResultSignIn =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    println(it.resultCode)
                } catch (e: RuntimeException) {
                    e.stackTrace
                }
            }
        }
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val context = LocalContext.current


//            val currentTheme = userSettings._currentSettings.collectAsState()


            val items = mutableListOf(
                SlideScreen(
                    bitmap = R.drawable.slider_img1,
                    header = "Easy to use",
                    description = "Even for women",
                ),
                SlideScreen(
                    bitmap =  R.drawable.slider_img2,
                    header = "Reach the unknown spot",
                    description = "To your destination",
                ),
                SlideScreen(
                    bitmap = R.drawable.slider_img3,
                    header = "Make connects with explora",
                    description = "To your dream trip",
                ),
            )
            //Use states
            //State of slider screen
            val settingsEventBus = LocaleSettingsEventBus.current

            // StateFlow.value should not be called within composition
            val currentSettings = settingsEventBus.currentSettings.collectAsState()

            LaunchedEffect(Unit){
                settingsEventBus.updateStyle(userSettings.getSettings())

            }

            val auth = Firebase.auth
            val isDarkModeValue = isSystemInDarkTheme()


            val isDarkMode = remember {
                mutableStateOf(isDarkModeValue)
            }
            val systemUiController = rememberSystemUiController()

            MainTheme(
                style = currentSettings.value.style,
                textSize = currentSettings.value.textSize,
                darkTheme = currentSettings.value.isDarkMode,
                corners = currentSettings.value.cornerStyle,
                paddingSize = currentSettings.value.paddingSize
            ) {

                //Set status toolbar
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = if(isDarkMode.value) baseDarkPalette.primaryBackground else baseLightPalette.primaryBackground,
                        darkIcons = !isDarkMode.value
                    )
                }

                Surface(color = ExtendedJetTheme.colors.primaryBackground) {
                    val navController = rememberNavController()
                    val navIntroductionItems = listOf("welcome", "introduction", "login", "registration", "verification", "home")
//            AuthenticationScreen(context = applicationContext, startForResultSignIn = startForResultSignIn)
                    NavHost(navController = navController, startDestination ="welcome"){
                        composable("welcome"){ WelcomeScreen(navController = navController, auth) }
                        composable("introduction"){
                            Slider(
                                items = items,
//                                pagerState = pagerState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 40.dp),
                                navigation = navController
                            )
                        }
//                        composable("login"){
//                            AuthenticationScreen(context = applicationContext,
//                                startForResultSignIn = startForResultSignIn)
//                        }

                        composable("login"){
                            LoginScreen(
                                navController = navController,
                                context = context,
                                auth = auth
                            )
                        }
                        composable("registration"){
                            RegistrationScreen(
                                navController = navController
                            )
                        }
                        composable("verification"){
                            VerificationScreen(
                                navController = navController
                            )
                        }

                        composable("home"){
                            MainScreen(navController = navController, context = context, auth)
                        }

                        composable("setting"){
                            MainScreen(navController = navController, context = context, auth)
                        }

                        composable("bookmark"){
                            MainScreen(navController = navController, context = context, auth)
                        }

                        composable("play"){
                            MainScreen(navController = navController, context = context, auth)
                        }
                    }
                }
            }
        }
    }


}


@Composable
fun SettingsTheme(context: Context){
    val currentStyle = remember{ mutableStateOf("") }
    val currentFontSize = remember{ mutableStateOf("") }

    val currentPaddingSize = remember{ mutableStateOf("") }
    val currentCornerStyle = remember { mutableStateOf("") }

    val masterKey = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        MasterKey.Builder(context)
            .setKeyGenParameterSpec(MasterKeys.AES256_GCM_SPEC)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    } else {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "theme_preferences",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

}