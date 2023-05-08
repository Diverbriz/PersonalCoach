package com.example.personalcoach.view.bottomNavigation.setting.view

import android.Manifest.permission.READ_CALENDAR
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.personalcoach.domain.model.calendar.CalendarItem
import com.example.personalcoach.domain.provider.CalendarEventProvider
import com.example.personalcoach.ui.theme.ExtendedJetTheme
import com.example.personalcoach.view.bottomNavigation.setting.viewmodel.CalendarViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CalendarScreen(
    mViewModel: CalendarViewModel = viewModel(),
){
//    var date by remember {
//        mutableStateOf("")
//    }
    lateinit var calendarEventProvider: CalendarEventProvider

    val scrollState = rememberScrollState()


    val observeItem = mViewModel._calendarItem.observeAsState()

    val observeEvent = mViewModel._calendarEvent.observeAsState()



    val context = LocalContext.current
    calendarEventProvider = CalendarEventProvider(context = context)

    requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                mViewModel._calendarItem.value = calendarEventProvider.getCalendars()
            } else {
                Toast.makeText(
                    context,
                    "Please allow this app to access your calendar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        AndroidView(factory = {CalendarView(it)}, update = {
//            it.setOnDateChangeListener { calendarView, year,
//                                         month, day ->
//                date = "$day - ${month+1} - $year"
//            }
//        })

            Column(
                Modifier
                    .background(ExtendedJetTheme.colors.tintColor)
                    .clickable {
                        scope.launch(Dispatchers.Main) {
//                            requestPermissionLauncher.launch(READ_CALENDAR)
                            mViewModel._calendarEvent.value = calendarEventProvider.getEvents(1)
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Get Calendars", modifier = Modifier
                    .padding(ExtendedJetTheme.shapes.padding), style = ExtendedJetTheme.typography.heading, color = ExtendedJetTheme
                    .colors.clickableText)
            }

        LazyColumn(){
            observeItem.value?.let {data -> items(data.size){
                ListItem(calendarItem = data[it], modifier =
                Modifier
                    .fillMaxWidth(0.6f)
                    .padding(ExtendedJetTheme.shapes.padding)
                    .border(
                        width = 2.dp,
                        color = ExtendedJetTheme.colors.tintColor,
                        shape = ExtendedJetTheme.shapes.cornersStyle
                    ).
                clickable {
                    scope.launch {
                        mViewModel._calendarEvent.value = calendarEventProvider.getEvents(data[it].id)
                    }
                })
            } }
        }


        LaunchedEffect(Unit) {
            val job = launch(start = CoroutineStart.DEFAULT) {
                println("Зашли в корутин скоуп")
                if (ContextCompat.checkSelfPermission(
                        context,
                        READ_CALENDAR
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    println("Разрешение есть")
                    mViewModel._calendarItem.value = calendarEventProvider.getCalendars()
                } else {
                    requestPermissionLauncher.launch(READ_CALENDAR)
                }
            }
            job.start()
            println("Старт корутины")
            job.join()

            println("Дождались выполнения корутины" +
                    " dataSize = ${mViewModel._calendarItem.value?.size}")

        }
    }
}

@Composable
fun ListItem(
    calendarItem: CalendarItem,
    modifier: Modifier = Modifier
){
    println(calendarItem.color)
    Row(
        modifier = modifier

    ) {
        Box(modifier = Modifier
            .width(5.dp)
            .fillMaxHeight()
            .background(ExtendedJetTheme.colors.secondaryBackground))

        Column(
            modifier = Modifier
                .padding(ExtendedJetTheme.shapes.padding)
        ) {
            with(calendarItem){
                name?.let { Text(text = it, style = ExtendedJetTheme.typography.heading.copy(fontSize = 16.sp),
                    color = ExtendedJetTheme.colors.primaryText)}
                accountName?.let { Text(text = it, style = ExtendedJetTheme.typography.body, color = ExtendedJetTheme.colors.secondaryText) }
                visible?.let { Text(text = "$it", style = ExtendedJetTheme.typography.body, color = ExtendedJetTheme.colors.secondaryText) }
                accountType?.let { Text(text = it, style = ExtendedJetTheme.typography.body, color = ExtendedJetTheme.colors.secondaryText) }
            }
        }
    }
}