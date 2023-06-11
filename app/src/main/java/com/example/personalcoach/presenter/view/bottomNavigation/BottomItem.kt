package com.example.personalcoach.presenter.view.bottomNavigation

import com.example.personalcoach.R
import com.example.personalcoach.domain.utils.bookmark
import com.example.personalcoach.domain.utils.home
import com.example.personalcoach.domain.utils.play
import com.example.personalcoach.domain.utils.settings

sealed class BottomItem(val title: String, val iconId: Int, val route: String){
    object HomeScreen: BottomItem("Home", iconId = R.drawable.home, home)
    object BookmarkScreen: BottomItem("Bookmark", iconId = R.drawable.bookmark, bookmark)
    object SettingScreen: BottomItem("Setting", iconId = R.drawable.ic_baseline_settings_24, settings)
    object PlayScreen: BottomItem("Play", iconId = R.drawable.play, play)
}


