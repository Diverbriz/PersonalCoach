package com.example.personalcoach.view.bottomNavigation.setting.view

import android.Manifest.permission.READ_CALENDAR
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.personalcoach.data.utils.convertLongToTime
import com.example.personalcoach.domain.model.calendar.CalendarItem
import com.example.personalcoach.domain.provider.CalendarEventProvider
import com.example.personalcoach.ui.theme.ExtendedJetTheme
import com.example.personalcoach.view.bottomNavigation.setting.viewmodel.CalendarViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>



@Composable
fun CalendarScreen(
    mViewModel: CalendarViewModel = viewModel(),
) {
//    var date by remember {
//        mutableStateOf("")
//    }
    lateinit var calendarEventProvider: CalendarEventProvider

    val scrollState = rememberScrollState()

    val dialogState = rememberMaterialDialogState(false)

    val observeItem = mViewModel._calendarItem.observeAsState()

    val observeEvent = mViewModel._calendarEvent.observeAsState()


    val context = LocalContext.current
    calendarEventProvider = CalendarEventProvider(context = context)
    val scope = rememberCoroutineScope()
    requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                scope.launch {
                    mViewModel._calendarItem.value = calendarEventProvider.getCalendars()

                }
            } else {
                Toast.makeText(
                    context,
                    "Please allow this app to access your calendar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        datepicker { date ->
            println(date.dayOfMonth)
            println(date.month)
            println(date.dayOfYear)
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        AndroidView(factory = {CalendarView(it)}, update = {
//            it.setOnDateChangeListener { calendarView, year,
//                                         month, day ->
//                date = "$day - ${month+1} - $year"
//            }
//        })


        Spacer(modifier = Modifier.height(20.dp))
        Column(
            Modifier
                .background(ExtendedJetTheme.colors.tintColor)
                .clickable {
//                    scope.launch(Dispatchers.Main) {
////                            requestPermissionLauncher.launch(READ_CALENDAR)
////                        mViewModel._calendarEvent.value = calendarEventProvider.getEvents()
//
//                    }

                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                text = { Text("Добавить") },
                onClick = { dialogState.show()}
            )
        }

            LazyColumn {
                observeEvent.value?.let { data ->
                    items(data.size) {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Divider(
                                modifier = Modifier
                                    .padding(vertical = ExtendedJetTheme.shapes.padding),
                                thickness = 0.5.dp,
                                color = ExtendedJetTheme.colors.secondaryText.copy(
                                    alpha = 0.3f
                                )
                            )
                            Row(
                                modifier = Modifier
                                    .padding(ExtendedJetTheme.shapes.padding)
                                    .background(ExtendedJetTheme.colors.primaryBackground)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = if(data[it].description == null || data[it].description == "") "Описание ивента" else data[it].description.toString(),
                                    style = ExtendedJetTheme.typography.body,
                                    color = ExtendedJetTheme.colors.primaryText,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = ExtendedJetTheme.shapes.padding)
                                )
                                val sdf = SimpleDateFormat("HH:mm")

                                val dateStart = data[it].dtstart?.toLong()
                                val dateEnd = data[it].dtend?.toLong()


                                var date = ""

                                date = if(dateEnd != null){
                                    "${sdf.format(dateStart)} - ${sdf.format(dateEnd)}"
                                } else{
                                    "${sdf.format(dateStart)} - ?"
                                }

                                Text(
                                    text = date,
                                    style = ExtendedJetTheme.typography.body,
                                    color = ExtendedJetTheme.colors.secondaryText
                                )
                            }
                        }
                    }
                }
            }


            LaunchedEffect(Unit) {
                val job = launch(start = CoroutineStart.LAZY) {
                    println("Зашли в корутин скоуп")
                    if (ContextCompat.checkSelfPermission(
                            context,
                            READ_CALENDAR
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        println("Разрешение есть")
                        mViewModel._calendarItem.value = calendarEventProvider.getCalendars()
                        println("Получили Items есть")
                        mViewModel._calendarEvent.value = calendarEventProvider.getEvents()
                    } else {
                        requestPermissionLauncher.launch(READ_CALENDAR)
                    }
                }
                job.start()
                println("Старт корутины")
                job.join()

                println(
                    "Дождались выполнения корутины" +
                            " itemSize = ${mViewModel._calendarItem.value?.size}" +
                            "\neventSize = ${mViewModel._calendarEvent.value?.size}"
                )

            }
        }
    }

    @Composable
    fun ListItem(
        calendarItem: CalendarItem,
        modifier: Modifier = Modifier
    ) {
        println(calendarItem.color)
        Row(
            modifier = modifier

        ) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(130.dp)
                    .background(Color(calendarItem.color!!))
            )

            Column(
                modifier = Modifier
                    .padding(ExtendedJetTheme.shapes.padding)
            ) {
                with(calendarItem) {
                    name?.let {
                        Text(
                            text = it,
                            style = ExtendedJetTheme.typography.heading.copy(fontSize = 16.sp),
                            color = ExtendedJetTheme.colors.primaryText
                        )
                    }
                    accountName?.let {
                        Text(
                            text = it,
                            style = ExtendedJetTheme.typography.body,
                            color = ExtendedJetTheme.colors.secondaryText
                        )
                    }
                    visible?.let {
                        Text(
                            text = "$it",
                            style = ExtendedJetTheme.typography.body,
                            color = ExtendedJetTheme.colors.secondaryText
                        )
                    }
                    accountType?.let {
                        Text(
                            text = it,
                            style = ExtendedJetTheme.typography.body,
                            color = ExtendedJetTheme.colors.secondaryText
                        )
                    }
                }
            }
        }
    }

