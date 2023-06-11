package com.example.personalcoach.presenter.view.bottomNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navHostController: NavHostController
){
    NavHost(navController = navHostController, startDestination = "home"){
//        composable("search"){
//            BottomNav(navController = navController)
//        }
//
//        composable("bookmark"){
//            BottomNav(navController = navController)
//        }
//
//        composable("play"){
//            BottomNav(navController = navController)
//        }
    }
}