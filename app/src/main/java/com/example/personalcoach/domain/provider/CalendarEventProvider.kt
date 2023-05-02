package com.example.personalcoach.domain.provider

import android.annotation.SuppressLint
import android.content.Context
import android.provider.CalendarContract
import com.example.personalcoach.domain.model.calendar.CalendarItem

class CalendarEventProvider(private val context: Context) {

    companion object {
        private val EVENT_PROJECTION = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars.SYNC_EVENTS,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.ACCOUNT_TYPE,
        )
        private const val PROJECTION_ID_INDEX = 0
        private const val PROJECTION_DISPLAY_NAME_INDEX = 1
        private const val PROJECTION_NAME_INDEX = 2
        private const val PROJECTION_CALENDAR_COLOR_INDEX = 3
        private const val PROJECTION_VISIBLE_INDEX = 4
        private const val PROJECTION_SYNC_EVENTS_INDEX = 5
        private const val PROJECTION_ACCOUNT_NAME_INDEX = 6
        private const val PROJECTION_ACCOUNT_TYPE_INDEX = 7
    }

    fun cleanData(
        list: MutableList<CalendarItem>
    ){
        list.clear()
    }

    @SuppressLint("Recycle")
    fun getCalendars(
        list: MutableList<CalendarItem>
    ){
//        calendarItemAdapter.clearData()
        list.clear()
        val uri = CalendarContract.Calendars.CONTENT_URI

        val selectionKey = ""

        val selectionArgs = emptyArray<String>()

        val cursor = context.contentResolver
            .query(uri, EVENT_PROJECTION,  selectionKey, selectionArgs, null)

        while (cursor?.moveToNext() == true){
            val calId = cursor.getLong(PROJECTION_ID_INDEX)
            val displayName = cursor.getString(PROJECTION_DISPLAY_NAME_INDEX)
            val name = cursor.getString(PROJECTION_NAME_INDEX)
            val color = cursor.getInt(PROJECTION_CALENDAR_COLOR_INDEX)
            val visible = cursor.getInt(PROJECTION_VISIBLE_INDEX)
            val syncEvents = cursor.getInt(PROJECTION_SYNC_EVENTS_INDEX)
            val accountName = cursor.getString(PROJECTION_ACCOUNT_NAME_INDEX)
            val accountType = cursor.getString(PROJECTION_ACCOUNT_TYPE_INDEX)

            list.add(
                CalendarItem(
                    id = calId,
                    name = name,
                    displayName = displayName,
                    color = color,
                    visible = visible == 1,
                    syncEvents = syncEvents == 1,
                    accountName = accountName,
                    accountType = accountType,
                )
            )

        }

        cursor?.close()
    }


}