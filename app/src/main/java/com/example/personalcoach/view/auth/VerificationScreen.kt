package com.example.personalcoach.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.personalcoach.ui.theme.ExtendedJetTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val RESET_TIME = 30

@Composable
fun VerificationScreen(
    navController: NavController
){
    var verificationCode by remember{
        mutableStateOf("")
    }

    var resetTimer by remember {
        mutableStateOf(30)
    }

    val coroutineVerify = rememberCoroutineScope()

    val snackbarHostState = SnackbarHostState()

    Box(

    ){
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = 95.dp, start = 22.dp, end = 14.dp)
        ) {
            Text(text = "Almost there",
                style = ExtendedJetTheme.typography.body, fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 30.dp))
            
            Text(text = "Please enter the 6-digit code sent to your \n" +
                    "email hemmyhtec@gmail.com for verification.", style = ExtendedJetTheme.typography.body,
            modifier = Modifier
                .padding(top = 35.dp))

            Row(modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
                repeat(6){
                    VerificationInput()
                }

            }

            Row(modifier = Modifier
                .padding(top = 100.dp, start = 14.dp, end = 14.dp)
                .fillMaxWidth()
                .background(
                    color = ExtendedJetTheme.colors.tintColor,
                    shape = ExtendedJetTheme.shapes.cornersStyle
                )
                .padding(vertical = 14.dp)
                .clickable {
                    coroutineVerify.launch {
                        snackbarHostState.showSnackbar(
                            message = "Request new code in 00:${resetTimer}s",
                            actionLabel = "HIDE",
                            duration = SnackbarDuration.Long
                        )
                        println(resetTimer)
                    }
                },
            horizontalArrangement = Arrangement.Center){
                Text(text = "VERIFY",
                    style = ExtendedJetTheme.typography.body,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }



            Text(text =resetTimer.toString(), style = ExtendedJetTheme.typography.heading)
            LaunchedEffect(key1 = Unit, block ={
                try {
                    startTimer(400L){
                        resetTimer--
                    }
                }catch (e: Exception){
                    e.stackTrace
                }
            })
            SnackbarHost(snackbarHostState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun VerificationInput(

){
    var input by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = input,
        onValueChange = {
            input = it
        },
        modifier = Modifier
            .height(36.dp)
            .width(36.dp)
            .background(
                shape = RoundedCornerShape(5.dp),
                color = Color(0xFFC4C4C4),

                ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text("0") }
    )
}

suspend fun startTimer(time: Long, onTimeEnd: () -> Unit){
    delay(time)
    onTimeEnd()
}

@Composable
fun SnackbarDemo(){
    Snackbar (
       modifier = Modifier
           .background(ExtendedJetTheme.colors.secondaryBackground),
        action = {
            Text(
                text = "HIDE",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        println()
                    },
                style = ExtendedJetTheme.typography.heading,
                fontSize = 22.sp
            )
        }
    ){
        Text(text = "Jetpack Compose Rocks")
    }
}