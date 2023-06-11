package com.example.personalcoach.presenter.view.auth

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.personalcoach.MainActivity
import com.example.personalcoach.R
import com.example.personalcoach.presenter.ui.theme.ExtendedJetTheme
import com.example.personalcoach.presenter.ui.theme.baseDarkPalette
import com.example.personalcoach.presenter.ui.theme.baseLightPalette
import com.example.personalcoach.presenter.ui.theme.createRoundedImageVector
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    auth: FirebaseAuth,
    context: Context,
//    startForResultSignIn: ActivityResultLauncher<Intent>
){

    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    var isRememberMe by remember {
        mutableStateOf(false)
    }

    val snackbarHostState = SnackbarHostState()
    val coroutineLogin = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(start = 30.dp)
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(235.dp)
        ){
            Box(modifier = Modifier
//                .border(border = BorderStroke(1.dp, Color.Blue), shape = RoundedCornerShape(0.dp))
                .fillMaxSize()){
                Image(bitmap = ImageBitmap.imageResource(id = R.drawable.registration_img), contentDescription = "Login",
                    contentScale = ContentScale.FillHeight, modifier = Modifier
                        .height(251.dp)
                        .fillMaxWidth()
//                        .border(border = BorderStroke(1.dp, Color.Red), shape = RoundedCornerShape(0.dp))
                        .offset(x = 20.dp, y = 30.dp))
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .offset(x = 20.dp),
                contentAlignment = Alignment.BottomStart){
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .offset(x = 26.dp, y = (-20).dp)
                ) {
                    Text(text = "Welcome back", style = ExtendedJetTheme.typography.heading)
                    Text(text = "sign in to access your account",  style = ExtendedJetTheme.typography.body)
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(end = 30.dp)
        ) {
            // Outlined Text Input Field
            OutlinedTextField(
                value = email.value,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Email", style = ExtendedJetTheme.typography.body, fontSize = 14.sp) },
                placeholder = { Text(text = "***@mail.ru") },
                onValueChange = {
                    email.value = it
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                trailingIcon = { Icon(Icons.Default.Email, contentDescription = "Email",
                    tint = ExtendedJetTheme.colors.tintColor)}
            )

            OutlinedTextField(
                value = password.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp),
                label = { Text(text = "Password", style = ExtendedJetTheme.typography.body, fontSize = 14.sp) },
                placeholder = { Text(text = "12334444") },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    password.value = it
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = { Icon(Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = ExtendedJetTheme.colors.tintColor)}
            )

            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = isRememberMe, onCheckedChange = {
                        isRememberMe = it
                    })
                    Text(text = "Remember me")
                }

                Text(text = "Forget password ?", color = ExtendedJetTheme.colors.clickableText,
                modifier = Modifier
                    .clickable {
                        println(isRememberMe)
                    })
            }
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 170.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable {
                            if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                                auth
                                    .signInWithEmailAndPassword(email.value, password.value)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {

                                            val user = auth.currentUser
                                            Log.d(TAG, "createUserWithEmail:success ${user?.email}")
                                        } else {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Please check that your email address and password are correct",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    }
                            } else {
                                coroutineLogin.launch {
                                    snackbarHostState.showSnackbar(
                                        message = if (email.value.isNotEmpty()) "Password is null" else "Email is null",
                                        actionLabel = "Login",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
//                            navController.navigate("verification")
                        }
                        .background(
                            color = ExtendedJetTheme.colors.tintColor,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .fillMaxWidth()
                ){
                    Text(text = "Next", style = ExtendedJetTheme.typography.heading, fontSize = 20.sp, color = Color.White)
                    Image(imageVector = createRoundedImageVector(50.dp, 50f, ExtendedJetTheme.colors.tintColor), contentDescription = "Next btn")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 16.dp)
                ){

                    Text(text = "New Member? ", color = ExtendedJetTheme.colors.primaryText,
                        style = ExtendedJetTheme.typography.body)
                    Text(text = "Register now", style = ExtendedJetTheme.typography.heading,fontSize=16.sp,
                        color = ExtendedJetTheme.colors.clickableText,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("registration")
                            })
                }


            }

            // Outlined Input text with icon on the left
            // inside leadingIcon property add the icon

        }
        SnackbarHost(snackbarHostState)
    }
}

suspend fun getAuth(auth: FirebaseAuth,
                    context: Context,
                    startForResultSignIn: ActivityResultLauncher<Intent>) = coroutineScope {
    launch {
        val currentUser = auth.currentUser
        if(currentUser != null){
            println("User: $currentUser")
        }
        else{
//            auth.signInWithEmailAndPassword("lkue.steam@mail.ru", "admin1")
//                .addOnCompleteListener()
        }
    }
}