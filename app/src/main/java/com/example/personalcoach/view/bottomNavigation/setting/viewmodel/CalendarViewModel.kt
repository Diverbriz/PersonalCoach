package com.example.personalcoach.view.bottomNavigation.setting.viewmodel

import androidx.lifecycle.ViewModel
import com.example.personalcoach.domain.model.calendar.CalendarItem

class CalendarViewModel:ViewModel(){
    var data: MutableList<CalendarItem> = mutableListOf()

    fun clearData() {
        data.clear()
    }

    fun pushData(calendarItem: CalendarItem) {
        data.add(calendarItem)
    }
}