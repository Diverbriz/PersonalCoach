package com.example.personalcoach.view.bottomNavigation.view

import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.personalcoach.R
import com.example.personalcoach.domain.model.SlideScreen
import com.example.personalcoach.ui.theme.ExtendedJetTheme
import com.example.personalcoach.ui.theme.blueDarkPalette
import com.example.personalcoach.ui.theme.blueLightPalette
import com.example.personalcoach.view.Indicator
import com.example.personalcoach.view.bottomNavigation.setting.viewmodel.HomeViewModel
import com.example.personalcoach.view.bottomNavigation.setting.viewmodel.Side
import com.example.personalcoach.view.bottomNavigation.setting.viewmodel.TrendingCourse
import com.example.personalcoach.view.bottomNavigation.setting.viewmodel.Vertical
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


private val mockSlideScreen = listOf(
    SlideScreen(
        R.drawable.home_slider1,
        "CrossFit",
        "Description"
    ),
    SlideScreen(
        R.drawable.home_slider2,
        "Hard Work",
        "Description"
    ),
    SlideScreen(
        R.drawable.home_slider1,
        "CrossFit",
        "Description"
    )

)
private val mockTrendingCourse = listOf(
    TrendingCourse(
        R.drawable.personal_trainer,
        "Personal Trainer",
        "Fitness",
        4.9f,
        "5h 30m"
    ),
    TrendingCourse(
        R.drawable.healthy_salad,
        "Sport Nutrition",
        "Nutrition",
        4.8f,
        "1h 30m"
    ),
    TrendingCourse(
        R.drawable.personal_trainer,
        "Personal Trainer",
        "Fitness",
        4.9f,
        "5h 30m"
    ),
    TrendingCourse(
        R.drawable.healthy_salad,
        "Sport Nutrition",
        "Nutrition",
        4.8f,
        "1h 30m"
    )
)

/**
 * @function HomeScreen - главная страница,
 * @author Sheloumov Vladimir
 * @constructor
 * наполнение - рекомендации, слайлеры и акции
 *
 * */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    context: Context,
    auth: FirebaseAuth,
    mainViewModel: HomeViewModel = viewModel()
) {
    var search by remember {
        mutableStateOf("")
    }
    val listState = rememberLazyListState()

    val searchLiveData = mainViewModel.searchRequest

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .background(ExtendedJetTheme.colors.secondaryBackground)
            .padding(ExtendedJetTheme.shapes.padding)
            .fillMaxSize()
            .padding(top = 60.dp)
            .verticalScroll(
                state = scrollState
            )
    ) {
        OutlinedTextField(
            value = search,
            onValueChange = {
//            mainViewModel.getRequest(it)

                search = it
            },
            singleLine = true,
            textStyle = ExtendedJetTheme.typography.body.copy(color = ExtendedJetTheme.colors.inputText),
//            textStyle = ExtendedJetTheme.colors.primaryText,
            label = {
                Text(
                    text = "Search",
                    style = ExtendedJetTheme.typography.body.copy(fontSize = 14.sp)
                )
            },
            shape = ExtendedJetTheme.shapes.cornersStyle,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "Search icon",
                    modifier = Modifier
                        .size(20.dp), tint = ExtendedJetTheme.colors.inputText
                )
            },
            placeholder = {
                Text(
                    text = "What do you learn today?",
                    style = ExtendedJetTheme.typography.body,
                    color = ExtendedJetTheme.colors.inputText,
                    modifier = Modifier
                        .background(ExtendedJetTheme.colors.inputBackground)
                        .fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = ExtendedJetTheme.colors.primaryText,
                containerColor = ExtendedJetTheme.colors.inputBackground
            ),
        )

        Box(modifier = Modifier
            .padding(ExtendedJetTheme.shapes.padding + 12.dp)){
            HomeSlider(
                mockSlideScreen
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = ExtendedJetTheme.shapes.padding + 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Trending courses", style = ExtendedJetTheme.typography.heading.copy(fontSize = 16.sp),
                color = ExtendedJetTheme.colors.primaryText)
            Text(text = "See all", style = ExtendedJetTheme.typography.body.copy(fontSize = 14.sp, lineHeight = 18.sp),
                color = ExtendedJetTheme.colors.tintColor,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .clickable {
                    println("See all")
                })
        }
        LazyRow(
            modifier = Modifier
                .padding(top = ExtendedJetTheme.shapes.padding+10.dp),
            state = listState
        ){
            items(mockTrendingCourse.size){
                TrendingCourseCard(trendingItem = mockTrendingCourse[it])
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            shape = ExtendedJetTheme.shapes.cornersStyle
        ){
            Image(bitmap = ImageBitmap.imageResource(id = R.drawable.discount), contentDescription = "Discount",
            contentScale = ContentScale.Fit)
        }
        Box(modifier = Modifier
            .height(100.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeSlider(
    sliderItems: List<SlideScreen>
){
    val pagerState = rememberPagerState()

    Column() {
        HorizontalPager(pageCount = sliderItems.size, state = pagerState,) {
                currentIt ->
            if(currentIt %2 == 0){
                CardSliderItem(sliderItem = sliderItems[currentIt],
                    side = Side.Left, vertical = Vertical.Top)
            }
            else{
                CardSliderItem(sliderItem = sliderItems[currentIt],
                    side = Side.Right, vertical = Vertical.Bottom)
            }
        }
        PagerIndicator(sliderItems.size, pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PagerIndicator(
    itemsSize: Int,
    pagerState: PagerState
){

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        repeat(itemsSize){
            HomeIndicator(it, pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeIndicator(
    isSelected: Int,
    pagerState: PagerState

){
    val width = animateDpAsState(targetValue = if(isSelected == pagerState.currentPage) 16.dp else 8.dp)
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier
        .padding(2.dp)
        .height(6.dp)
        .width(width.value)
        .clip(CircleShape)
        .background(
            ExtendedJetTheme.colors.secondaryText
        )
        .clickable {
            scope.launch {
                pagerState.animateScrollToPage(isSelected)
            }
        }
    )
}

@Composable
fun CardSliderItem(
    sliderItem: SlideScreen,
    side: Side = Side.Left,
    vertical: Vertical = Vertical.Bottom
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(top = 24.dp)
            .clickable {
                println("Card description")
            },
        shape = ExtendedJetTheme.shapes.cornersStyle,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp,
            pressedElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(bitmap = ImageBitmap.imageResource(id = sliderItem.bitmap), contentDescription = "Background card",
                contentScale = ContentScale.Crop)
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = when(vertical){
                    Vertical.Top -> Arrangement.Top
                    Vertical.Center -> Arrangement.Center
                    Vertical.Bottom -> Arrangement.Bottom
                },
                horizontalAlignment = when(side){
                    Side.Left -> Alignment.Start
                    Side.Right -> Alignment.End
                    Side.Center -> Alignment.CenterHorizontally
                }
            ) {
                Column(modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .background(ExtendedJetTheme.colors.secondaryBackground)
                    .padding(ExtendedJetTheme.shapes.padding),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = when(side){
                        Side.Left -> Alignment.Start
                        Side.Right -> Alignment.End
                        Side.Center -> Alignment.CenterHorizontally
                    }){
                    Text(text = sliderItem.header, style = ExtendedJetTheme.typography.heading.copy(fontSize = 20.sp),
                        color = ExtendedJetTheme.colors.primaryText)
                    Text(text = sliderItem.description, style = ExtendedJetTheme.typography.body,
                        color = ExtendedJetTheme.colors.primaryText)
                }
            }
        }
    }
}

@Composable
fun TrendingCourseCard(
    trendingItem: TrendingCourse
){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .width(240.dp)
            .padding(start = ExtendedJetTheme.shapes.padding),
        shape = ExtendedJetTheme.shapes.cornersStyle
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(bitmap = ImageBitmap.imageResource(id = trendingItem.imageUrl), contentDescription = "Photo Preview",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp))
            Column(
                modifier = Modifier
                    .padding(ExtendedJetTheme.shapes.padding)
            ) {
                Text(
                    text = trendingItem.type,
                    style = ExtendedJetTheme.typography.body.copy(fontSize = 12.sp),
                    color = ExtendedJetTheme.colors.secondaryText
                )
                Text(
                    text = trendingItem.name,
                    style = ExtendedJetTheme.typography.body.copy(fontSize = 12.sp),
                    color = ExtendedJetTheme.colors.secondaryText
                )

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(top = ExtendedJetTheme.shapes.padding)
                ) {
                    RowIconAndText(
                        text = trendingItem.rating.toString(),
                        landingIconId = R.drawable.star,
                        landingIconTint = Color(0xFFFFAC37)
                    )
                    RowIconAndText(
                        text = trendingItem.rating.toString(),
                        landingIconId = R.drawable.time_circle,
                        modifier = Modifier
                            .padding(start = ExtendedJetTheme.shapes.padding)
                    )
                }
            }
        }
    }
}

@Composable
fun RowIconAndText(
    landingIconId: Int = R.drawable.ic_baseline_settings_24,
    landingIconTint: Color = ExtendedJetTheme.colors.secondaryText,
    text: String,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(imageVector = ImageVector.vectorResource(id = landingIconId), contentDescription = "Landing Icon",
        tint = landingIconTint, modifier = Modifier
                .size(20.dp))
        Text(
            text = text,
            style = ExtendedJetTheme.typography.body.copy(fontSize = 14.sp),
            color = ExtendedJetTheme.colors.secondaryText,
            modifier = Modifier
                .padding(start = 5.dp)
        )
    }
}