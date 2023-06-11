package com.example.personalcoach.presenter.view.bottomNavigation.setting.view

import android.Manifest.permission.READ_CALENDAR
import android.Manifest.permission.WRITE_CALENDAR
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.personalcoach.domain.model.calendar.CalendarEvent
import com.example.personalcoach.domain.model.calendar.CalendarItem
import com.example.personalcoach.domain.provider.CalendarEventProvider
import com.example.personalcoach.presenter.ui.theme.ExtendedJetTheme
import com.example.personalcoach.presenter.view.bottomNavigation.setting.viewmodel.CalendarViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.input
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

private lateinit var calendarRequest: ActivityResultLauncher<String>



@Composable
fun CalendarScreen(
    mViewModel: CalendarViewModel = viewModel(),
) {
//    var date by remember {
//        mutableStateOf("")
//    }



    val dialogTimerState = rememberMaterialDialogState(false)

    val dialogState = rememberMaterialDialogState(false)



    val observeEvent = mViewModel._calendarEvent.observeAsState()

    val context = LocalContext.current
    val calendarEventProvider = CalendarEventProvider(context = context)
    val scope = rememberCoroutineScope()

    val descriptionState = rememberMaterialDialogState(false)
    val beginTime = remember {
        mutableStateOf(Calendar.getInstance())
    }

    val endTime = remember {
        mutableStateOf(Calendar.getInstance())
    }

    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }

    calendarRequest = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            scope.launch {
                Toast.makeText(
                    context,
                    "Разрешения есть",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            Toast.makeText(
                context,
                "Please allow this app to access your calendar",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
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
        dialogState = descriptionState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        },
        onCloseRequest = {
            if(title.value != "" && description.value != ""){
                it.hide()
                dialogState.show()
            }
        }
    ) {
        Text(
            text = "Настройка события",
            style = ExtendedJetTheme.typography.heading.copy(fontSize = 18.sp),
            color = ExtendedJetTheme.colors.inputText
        )
        input(label = "Title"){
            title.value = it
        }
        input(label = "Description"){
            description.value = it
            dialogState.show()
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
            beginTime.value.set(date.year, date.monthValue, date.dayOfMonth)
            endTime.value.set(date.year, date.monthValue, date.dayOfMonth)
            dialogTimerState.show()
        }
    }

    MaterialDialog(
        dialogState = dialogTimerState,
        buttons = {
            positiveButton("Add")
            negativeButton("Cancel")
        }
    ) {
        timepicker { time ->
            beginTime.value.set(Calendar.HOUR, time.hour)
            beginTime.value.set(Calendar.MINUTE, time.minute)

            endTime.value.set(Calendar.HOUR, time.hour + 60*12*36)
            endTime.value.set(Calendar.MINUTE, time.minute)
            val obj = CalendarEvent(
                1,
                "",
                title = title.value,
                description = description.value,
                beginTime.value.timeInMillis.toString(),
                endTime.value.timeInMillis.toString(),
                "Sport"
            )

            scope.launch {
                if (ContextCompat.checkSelfPermission(
                        context,
                        WRITE_CALENDAR
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    println("Разрешение есть")
                    calendarEventProvider.createEvent(obj)
                } else {
                    calendarRequest.launch(WRITE_CALENDAR)
                }

            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                text = { Text("Добавить") },
                onClick = {
//                    dialogState.show()
                    descriptionState.show()
                }
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


@Composable
fun MyAlertDialog(
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismiss) {
            Column {
                Column(Modifier.padding(24.dp)) {
                    title.invoke()
                    Spacer(Modifier.size(16.dp))
                    content.invoke()
                }
                Spacer(Modifier.size(4.dp))
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    dismissButton.invoke()
                    confirmButton.invoke()
                }
            }
    }
}
