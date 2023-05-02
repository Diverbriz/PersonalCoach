package com.example.personalcoach.view.bottomNavigation

import com.example.personalcoach.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String){
    object HomeScreen: BottomItem("Home", iconId = R.drawable.home, "home")
    object BookmarkScreen: BottomItem("Bookmark", iconId = R.drawable.bookmark, "bookmark")
    object SettingScreen: BottomItem("Setting", iconId = R.drawable.ic_baseline_settings_24, "Setting")
    object PlayScreen: BottomItem("Play", iconId = R.drawable.play, "play")
}


