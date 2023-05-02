package com.example.personalcoach.view.bottomNavigation.setting.view

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.personalcoach.R
import com.example.personalcoach.ui.theme.ExtendedJetTheme
import com.example.personalcoach.view.bottomNavigation.setting.SettingThemeScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun SettingScreen(){
    TabLayout()
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TabLayout(){
    val pagerState = rememberPagerState(initialPage = 1)

    Column(
        modifier = Modifier
            .background(ExtendedJetTheme.colors.primaryBackground)
            .fillMaxSize()
            .padding(top = 20.dp),
        // on below line we are providing alignment for our
        // column to center of our top app bar.
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // on below line we are specifying a text and
        // specifying a text as "Tab Layout Example"
        Text(
            text = "Tab Layout Example",
            style = TextStyle(color = Color.Gray),
            fontWeight = FontWeight.Bold,
            fontSize = TextUnit(
                18F,
                TextUnitType.Sp
            ),
            // on below line we are specifying a modifier
            // to our text and adding passing from all sides.
            modifier = Modifier.padding(all = Dp(5F)),
            // on below line we are aligning
            // our text to center.
            textAlign = TextAlign.Center
        )
        // on below line we are calling tabs
        Tabs(pagerState = pagerState)
        // on below line we are calling tabs content
        // for displaying our page for each tab layout
        TabsContent(pagerState = pagerState)

    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    // in this function we are creating a list
    // in this list we are specifying data as
    // name of the tab and the icon for it.
    val list = listOf(
        "Theme" to R.drawable.adjustments_alt,
        "Notification" to R.drawable.bell_up,
        "Calendar" to R.drawable.calendar_plus
    )
    // on below line we are creating
    // a variable for the scope.
    val scope = rememberCoroutineScope()
    // on below line we are creating a
    // individual row for our tab layout.
    TabRow(
        // on below line we are specifying
        // the selected index.
        selectedTabIndex = pagerState.currentPage,

        // on below line we are specifying content color.
        backgroundColor = ExtendedJetTheme.colors.secondaryBackground,
        contentColor = Color.White,
        indicator = { tabPositions ->
            // on below line we are specifying the styling
            // for tab indicator by specifying height
            // and color for the tab indicator.
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState = pagerState, tabPositions),
                height = 2.dp,
                color = ExtendedJetTheme.colors.tintColor
            )
        }
    ) {
        // on below line we are specifying icon
        // and text for the individual tab item
        list.forEachIndexed { index, _ ->
            // on below line we are creating a tab.
            Tab(
                // on below line we are specifying icon
                // for each tab item and we are calling
                // image from the list which we have created.
                icon = {
                    Icon(imageVector = ImageVector.vectorResource(id = list[index].second), contentDescription = null,
                        tint = ExtendedJetTheme.colors.tintColor)
                },
                // on below line we are specifying the text for
                // the each tab item and we are calling data
                // from the list which we have created.
                text = {
                    Text(
                        list[index].first,
                        // on below line we are specifying the text color
                        // for the text in that tab
                        color = if (pagerState.currentPage == index) ExtendedJetTheme.colors.primaryText else ExtendedJetTheme.colors.secondaryText
                    )
                },
                // on below line we are specifying
                // the tab which is selected.
                selected = pagerState.currentPage == index,
                // on below line we are specifying the
                // on click for the tab which is selected.
                onClick = {
                    // on below line we are specifying the scope.
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(pagerState: PagerState) {

    // on below line we are creating
    // horizontal pager for our tab layout.
    HorizontalPager(state = pagerState, pageCount = 3) {
            page ->
        when (page) {
            // on below line we are calling tab content screen
            // and specifying data as Home Screen.
            0 -> SettingThemeScreen()
            // on below line we are calling tab content screen
            // and specifying data as Shopping Screen.
            1 -> TabContentScreen(data = "Welcome to Shopping Screen")
            // on below line we are calling tab content screen
            // and specifying data as Settings Screen.
            2 -> CalendarScreen()
        }
    }
}


@Composable
fun TabContentScreen(data: String) {
    // on below line we are creating a column
    Column(
        // in this column we are specifying modifier
        // and aligning it center of the screen on below lines.
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // in this column we are specifying the text
        Text(
            // on below line we are specifying the text message
            text = data,

            // on below line we are specifying the text style.
            style = ExtendedJetTheme.typography.body,

            // on below line we are specifying the text color
            color = Color.Green,

            // on below line we are specifying the font weight
            fontWeight = FontWeight.Bold,

            //on below line we are specifying the text alignment.
            textAlign = TextAlign.Center
        )
    }
}