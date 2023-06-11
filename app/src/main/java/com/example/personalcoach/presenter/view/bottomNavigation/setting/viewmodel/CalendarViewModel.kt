package com.example.personalcoach.presenter.view.bottomNavigation.setting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalcoach.domain.SettingsBundle
import com.example.personalcoach.domain.model.calendar.CalendarEvent
import com.example.personalcoach.domain.model.calendar.CalendarItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface BaseViewModel<T>{

}


class CalendarViewModel:ViewModel(){
     val _calendarItem = MutableLiveData<List<CalendarItem>>()

     val _calendarEvent = MutableLiveData<List<CalendarEvent>>()

    val _currentCalendar = MutableLiveData<CalendarItem>()

    fun fetchCalendarItem(
        data: List<CalendarItem>
    ){
        viewModelScope.launch{
            _calendarItem.value = data
        }
    }

}

data class CurrentCalendarState(
    val calendarItems: List<CalendarItem>,
    val calendarEvents: List<CalendarEvent>
)