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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.personalcoach.R
import com.example.personalcoach.ui.theme.ExtendedJetTheme
import java.io.*
import com.example.personalcoach.view.bottomNavigation.setting.view.SettingScreen
import com.example.personalcoach.view.bottomNavigation.view.HomeScreen
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    context: Context,
    auth: FirebaseAuth
){
//    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            AppToolBar()
        },
        bottomBar = { BottomNav(navController = navController) },

    ) {
        val currentNav by navController.currentBackStackEntryAsState()
        when(currentNav?.destination?.route){
            "home" -> HomeScreen(context, auth)
            "setting" -> SettingScreen(context)
            "play" -> PlayScreen()
            "bookmark" -> BookmarkScreen()
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


//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.SpaceEvenly,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        val fileName = remember { mutableStateOf("") }
//        val fileData = remember { mutableStateOf("") }
//
//        OutlinedTextField(value = fileName.value, onValueChange = { fileName.value = it }, label = {
//            Text(
//                text = "Type a file name"
//            )
//        })
//        OutlinedTextField(value = fileData.value, onValueChange = { fileData.value = it }, label = {
//            Text(
//                text = "Type a file data"
//            )
//        })
//        Button(onClick = {
//            val file: String = fileName.value
//            val data: String = fileData.value
//            val fileOutputStream: FileOutputStream
//            try {
//                fileOutputStream = context.openFileOutput(file, Context.MODE_PRIVATE)
//                fileOutputStream.write(data.toByteArray())
//            } catch (e: FileNotFoundException) {
//                e.printStackTrace()
//            } catch (e: NumberFormatException) {
//                e.printStackTrace()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            Toast.makeText(context, "data save", Toast.LENGTH_LONG).show()
//            fileName.value = ""
//            fileData.value = ""
//        }) { Text(text = "Save") }
//        Button(onClick = {
//            val filename = fileName.value
//            if (filename.trim() != "") {
//                val fileInputStream = context.openFileInput(filename)
//                val inputStreamReader = InputStreamReader(fileInputStream)
//                val bufferedReader = BufferedReader(inputStreamReader)
//                val stringBuilder: StringBuilder = StringBuilder()
//                var text: String?
//                while (run {
//                        text = bufferedReader.readLine()
//                        text
//                    } != null) {
//                    stringBuilder.append(text)
//                }
//                fileData.value = stringBuilder.toString()
//            } else {
//                Toast.makeText(context, "file name cannot be blank", Toast.LENGTH_LONG).show()
//            }
//        }) { Text(text = "View") }
//    }



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
@Preview(showBackground = true)
fun AppToolBar(
//    navController: NavController
) {

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
            IconButton(onClick = { }) {
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

