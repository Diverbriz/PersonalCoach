package com.example.personalcoach.presenter.view

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.personalcoach.domain.model.SlideScreen
import com.example.personalcoach.presenter.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SlideScreens(
    slideScreen: SlideScreen,
    navigation: Navigation
){
    var visibility by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit){
        delay(300)
        visibility = true
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(start = 20.dp, top = 165.dp, end = 40.dp),
        verticalArrangement = Arrangement.Top
        ) {
//        AnimatedVisibility(visible = visibility,
//            enter = slideInHorizontally() + expandHorizontally(expandFrom = Alignment.End)
//                    + fadeIn(),
//            exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth })
//                    + shrinkHorizontally() + fadeOut(),) {
//            }
        with(slideScreen){
            Image(bitmap = ImageBitmap.imageResource(id = bitmap), contentDescription = description,  contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth())
            Column(Modifier.offset(y = 95.dp)) {
                Text(text = header, fontFamily = ExtendedJetTheme.typography.heading.fontFamily,
                    fontSize = 36.sp,  color = Color.Black, lineHeight = 44.sp)
                Text(text = description, fontFamily = Typography.bodyLarge.fontFamily, fontWeight = FontWeight.W300,
                    fontSize = 24.sp,  color = Color.Black, modifier = Modifier.offset(y = 5.dp))
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun Slider(
    items: List<SlideScreen>,
//    pagerState: PagerState,
    modifier: Modifier=Modifier,
    navigation: NavController
){
    val pagerState = rememberPagerState()
    Box(modifier = modifier){
        Column(horizontalAlignment = Alignment.Start,) {
            HorizontalPager(state = pagerState, pageCount = items.size,
            userScrollEnabled = false) { page ->
                with(items[page]){
                    Column()
                    {
                        Box(modifier = Modifier
                            .background(ExtendedJetTheme.colors.primaryBackground)
                            .fillMaxWidth()
                            .height(400.dp),
                            contentAlignment = Alignment.BottomCenter){
                            Image(painter = painterResource(id = bitmap), contentDescription = header,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Fit)
                        }
                        Column(modifier = Modifier
                            .background(ExtendedJetTheme.colors.primaryBackground)
                            .fillMaxWidth()
                            .height(214.dp)
                            .padding(top = 90.dp),
                            verticalArrangement = Arrangement.Top

                        ){
                            Text(text = header,
                                style = ExtendedJetTheme.typography.heading,
                                fontSize = 36.sp,
                                color = ExtendedJetTheme.colors.primaryText,
                                lineHeight = 44.sp
                            )
                            Text(text = description, style = ExtendedJetTheme.typography.body, fontSize = 24.sp)
                        }

                        Box(modifier = Modifier
                            .fillMaxSize()
                            ){
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                PagerIndicator(items = items, currentPage = pagerState.currentPage)
                                val scope = rememberCoroutineScope()

                                Image(
                                    imageVector = createRoundedImageVector(50.dp, 50f, Color.Black),
                                    contentDescription = "Next button",
                                    modifier = Modifier
                                        .clickable {
                                            Log.e(TAG, "Следующая страница ${pagerState.currentPage + 1}")
                                            // Запускаем корутину при нажатии на кнопку next
                                            scope.launch {
                                                Log.e(TAG, "Запустили корутину")

                                                if(pagerState.currentPage != items.size-1){
                                                    pagerState.scrollToPage(pagerState.currentPage + 1)
                                                }
                                                else{
                                                    navigation.navigate("login")
                                                }
                                            }
                                        }
                                )
                            }
                        }
                    }
                }
            }

            //Slider dots and next button

        }
    }

}

@Composable
fun PagerIndicator(items: List<SlideScreen>, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 20.dp)
    ) {
        repeat(items.size){
            Indicator(
                isSelected = it == currentPage,
            )
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if(isSelected) 16.dp else 16.dp)

    Box(modifier = Modifier
        .padding(2.dp)
        .height(6.dp)
        .width(width.value)
        .clip(CircleShape)
        .background(
            if(!isSelected) blueDarkPalette.tintColor else blueLightPalette.tintColor
        )
    )

}

@Composable
@Preview(showBackground = true)
fun Svg(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = ". . .")
        Image(
            imageVector = createRoundedImageVector(50.dp, 50f, Color.Black),
            contentDescription = "Значок электронной почты",
            modifier = Modifier.size(50.dp, 50.dp)
        )
    }
}
//createRoundedImageVector(size = 50.dp,50f, Color.Black)




//Column(Modifier.fillMaxSize()) {
//    Box(modifier = Modifier.background(ExtendedJetTheme.colors.primaryBackground).fillMaxWidth().weight(1f)){
//        slideScreen()
//    }
//    Box(modifier = Modifier.background(Color.Yellow).fillMaxWidth().height(150.dp))
//}