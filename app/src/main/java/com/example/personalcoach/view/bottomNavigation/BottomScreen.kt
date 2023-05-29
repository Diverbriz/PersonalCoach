package com.example.personalcoach.view.bottomNavigation

//import androidx.compose.material.TopAppBar
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.personalcoach.R
import com.example.personalcoach.ui.theme.ExtendedJetTheme
import java.io.*
import com.example.personalcoach.view.bottomNavigation.setting.view.SettingScreen
import com.example.personalcoach.view.bottomNavigation.view.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    context: Context,
    auth: FirebaseAuth
){
//    val scaffoldState = rememberScaffoldState()
    val openDialog = remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            AppToolBar(auth, context, openDialog)
        },
        bottomBar = { BottomNav(navController = navController) },

    ) {
        val currentNav by navController.currentBackStackEntryAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when(currentNav?.destination?.route){
                "home" -> HomeScreen(context, auth)
                "setting" -> SettingScreen(context)
                "play" -> PlayScreen()
                "bookmark" -> BookmarkScreen()
            }
        }
             if(openDialog.value){
                 AlertDialog(
                     onDismissRequest = {
                         openDialog.value = false
                     },
                     title = { Text(text = "Подтверждение действия") },
                     text = { Text("Вы действительно хотите выйти?") },
                     confirmButton = {
                         Button(
                             modifier = Modifier.
                             fillMaxWidth(0.4f),
                             onClick = { openDialog.value = false }
                         ) {
                             Text("Ok")
                         }
                     },
                     dismissButton = {
                         Button(
                             modifier = Modifier.
                             fillMaxWidth(0.4f),
                             onClick = { openDialog.value = false }
                         ) {
                             Text("Cancel")
                         }
                     }
                 )
             }
    }
}

@Composable
fun BottomNav(
    navController: NavController
){
    val listItems = listOf(

        BottomItem.HomeScreen,
        BottomItem.BookmarkScreen,
        BottomItem.SettingScreen,
        BottomItem.PlayScreen
    )

    BottomNavigation(
        backgroundColor = ExtendedJetTheme.colors.primaryBackground
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = backStackEntry?.destination?.route

        listItems.forEach{
            item ->

                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        println(item.title + " " + currentRoute)
                        navController.navigate(item.route)
                },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = item.iconId),
                            contentDescription = item.title,
                            tint = if (currentRoute == item.route) ExtendedJetTheme.colors.tintColor else ExtendedJetTheme.colors.logoBackground
                        )

                },
                    label = {
//                          if(currentRoute == item.route){
                              Text(text = item.title, fontSize = 9.sp, color = ExtendedJetTheme.colors.primaryText)
//                          }
                    },
                    selectedContentColor = Color.Red,
                    unselectedContentColor = Color.Cyan
                    )
        }
    }

}

@Composable
fun PlayScreen(){
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "Play Screen",
        textAlign = TextAlign.Center
    )
}

@Composable
fun BookmarkScreen(){
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "Bookmark Screen",
        textAlign = TextAlign.Center
    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBar(
    auth: FirebaseAuth,
    context: Context,
    openDialog: MutableState<Boolean>
//    navController: NavController
) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(text = "Teamer", style = ExtendedJetTheme.typography.heading, color = ExtendedJetTheme.colors.primaryText,
        fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.g_logo_1),
                    contentDescription = "Logo",
                    tint = ExtendedJetTheme.colors.tintColor
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification icon",
                    tint = ExtendedJetTheme.colors.primaryText
                )
            }
            IconButton(onClick = {
                scope.launch {
                    if(auth.currentUser != null){
                        println(auth.currentUser?.email)
                        openDialog.value = true
                    }else{
                        println("nullllllllllllll")
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account icon",
                    tint = ExtendedJetTheme.colors.primaryText
                )
            }
        },
        colors =
        TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = ExtendedJetTheme.colors.primaryBackground
        )

    )
}

