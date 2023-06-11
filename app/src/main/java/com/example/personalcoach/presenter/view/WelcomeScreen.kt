package com.example.personalcoach.presenter.view

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.personalcoach.R
import com.example.personalcoach.presenter.ui.theme.ExtendedJetTheme
import com.example.personalcoach.presenter.ui.theme.PrimaryBlue
import com.example.personalcoach.presenter.ui.theme.montserattBold
import com.example.personalcoach.presenter.viewmodel.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@Composable
fun WelcomeScreen (
    navController: NavHostController,
    auth: FirebaseAuth,
    viewModel: SplashViewModel = viewModel()
){

    val state = viewModel.state.value

    val currentUser = auth.currentUser

    val composableScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PrimaryBlue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(170.dp)
            .padding(start = 114.dp)
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(25))){
            AnimatedVisibility(visible = state.isAnimated,
                enter = slideInHorizontally() + expandHorizontally(expandFrom = Alignment.End)
                        + fadeIn(),

                exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth })
                        + shrinkHorizontally() + fadeOut(),) {
                Image(bitmap = ImageBitmap.imageResource(id = R.drawable.preview), contentDescription = "Preview Image",
                    contentScale = ContentScale.Fit, modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Text(text = "Teamer", fontFamily = montserattBold,
            fontSize = ExtendedJetTheme.typography.heading.fontSize,  color = Color.White, modifier = Modifier.padding(start = 103.dp))

        LaunchedEffect(Unit){
            delay(500)
            viewModel.onEvent(SplashViewModel.UIEvent.ChangeIsAnimated)
            viewModel.onEvent(SplashViewModel.UIEvent.IncrementCounter)

            println(state.isAnimated)
            println(state.counter)

            delay(2000)
            if(auth.currentUser != null){
                navController.navigate("home")
            }
            else{
                navController.navigate("introduction")
            }
//            println(currentUser?.email)
//            val route = delayNavigation(auth.currentUser);
//            println(route.first())
//            navController.navigate(route.first())
        }

    }
}


fun delayNavigation(currentUser: FirebaseUser): Flow<String> = flow{
    delay(2000)

    if (currentUser.email != null){
        emit("home")
    }
    else {
        emit("introduction")
    }
}





//suspend fun main()= coroutineScope{
//
//    launch{
//        for(i in 0..5){
//            delay(400L)
//            println(i)
//        }
//    }
//    launch{
//        for(i in 6..10){
//            delay(400L)
//            println(i)
//        }
//    }
//
//    println("Hello Coroutines")
//}