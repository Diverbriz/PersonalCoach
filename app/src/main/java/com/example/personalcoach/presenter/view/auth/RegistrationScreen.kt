package com.example.personalcoach.presenter.view.auth

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.personalcoach.R
import com.example.personalcoach.presenter.ui.theme.ExtendedJetStyle
import com.example.personalcoach.presenter.ui.theme.ExtendedJetTheme
import com.example.personalcoach.presenter.ui.theme.MainTheme
import com.example.personalcoach.presenter.ui.theme.createRoundedImageVector
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavController,
    auth: FirebaseAuth,
    context: Context
){
    val scope = rememberCoroutineScope()

    var name by remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    val phone = remember {
        mutableStateOf("")
    }

    var isAgree by remember {
        mutableStateOf(false)
    }
        Column(
                modifier = Modifier
                    .background(ExtendedJetTheme.colors.secondaryBackground)
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
                            Text(text = "Get Started", style = ExtendedJetTheme.typography.heading, color = ExtendedJetTheme.colors.primaryText)
                            Text(text = "by creating a free account.",  style = ExtendedJetTheme.typography.body, color = ExtendedJetTheme.colors.primaryText)
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(end = 30.dp)
                ) {
                    // Outlined Text Input Field Phone number
                    OutlinedTextField(
                        value = name,
                        modifier = Modifier
                            .padding(top = 18.dp)
                            .fillMaxWidth(),
                        label = { Text(text = "Name", style = ExtendedJetTheme.typography.body, fontSize = 14.sp) },
                        placeholder = { Text(text = "Lkue") },
//                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = {
                            name = it
                        },
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        trailingIcon = { Icon(
                            Icons.Default.AccountCircle, contentDescription = "Email",
                            tint = ExtendedJetTheme.colors.tintColor)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = ExtendedJetTheme.colors.primaryText
                        )
                    )

                    OutlinedTextField(
                        value = email.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp),
                        label = { Text(text = "Valid email", style = ExtendedJetTheme.typography.body, fontSize = 14.sp) },
                        placeholder = { Text(text = "123@mail.ru") },
//                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = {
                            email.value = it
                        },
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        trailingIcon = { Icon(
                            Icons.Default.Email,
                            contentDescription = "Valid email",
                            tint = ExtendedJetTheme.colors.tintColor)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = ExtendedJetTheme.colors.primaryText
                        )
                    )

                    OutlinedTextField(
                        value = phone.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp),
                        label = { Text(text = "Phone number", style = ExtendedJetTheme.typography.body, fontSize = 14.sp) },
                        placeholder = { Text(text = "+79991231212") },
//                        visualTransformation = ,
                        onValueChange = {
                            phone.value = it
                        },
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        trailingIcon = { Icon(
                            Icons.Default.Phone,
                            contentDescription = "Phone number",
                            tint = ExtendedJetTheme.colors.tintColor)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = ExtendedJetTheme.colors.primaryText
                        )
                    )
                    OutlinedTextField(
                        value = password.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp),
                        label = { Text(text = "Strong Password", style = ExtendedJetTheme.typography.body, fontSize = 14.sp) },
                        placeholder = { Text(text = "*******") },
                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = {
                            password.value = it
                        },
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = { Icon(
                            Icons.Default.Lock,
                            contentDescription = "Strong Password",
                            tint = ExtendedJetTheme.colors.tintColor)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = ExtendedJetTheme.colors.primaryText
                        )
                    )

                    Row (modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically){
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(checked = isAgree, onCheckedChange = {
                                isAgree = it
                            })
                            Text(text = "By checking the box you agree to our ",
                            color = ExtendedJetTheme.colors.primaryText, fontSize = 10.sp)
                        }

                        Text(text = "Terms ", color = ExtendedJetTheme.colors.clickableText,
                            modifier = Modifier
                                .clickable {
                                    println(isAgree)
                                }, fontSize = 10.sp)
                        Text(text = "and ",
                            color = ExtendedJetTheme.colors.primaryText, fontSize = 10.sp)
                        Text(text = "Conditions", color = ExtendedJetTheme.colors.clickableText,
                            modifier = Modifier
                                .clickable {
                                    println(isAgree)
                                }, fontSize = 10.sp)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = 70.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .clickable {
                                    scope.launch {
                                        delay(100)
                                        if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                                            auth
                                                .createUserWithEmailAndPassword(
                                                    email.value,
                                                    password.value
                                                )
                                                .addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        // Sign in success, update UI with the signed-in user's information
                                                        Log.d(TAG, "createUserWithEmail:success")
                                                        val user = auth.currentUser
                                                        navController.navigate("home")
                                                    } else {
                                                        // If sign in fails, display a message to the user.
                                                        Log.w(
                                                            TAG,
                                                            "createUserWithEmail:failure",
                                                            task.exception
                                                        )
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Authentication failed.",
                                                                Toast.LENGTH_SHORT,
                                                            )
                                                            .show()
                                                    }
                                                }
                                        }
                                    }

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
                            Text(text = "Already a member?", color = ExtendedJetTheme.colors.primaryText,
                                style = ExtendedJetTheme.typography.body)
                            Text(text = " Log In", style = ExtendedJetTheme.typography.heading,fontSize=16.sp,
                                color = ExtendedJetTheme.colors.clickableText,
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate("login")
                                    })
                        }


                    }

                    // Outlined Input text with icon on the left
                    // inside leadingIcon property add the icon
        }
    }
}