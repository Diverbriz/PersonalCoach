package com.example.personalcoach.domain.provider

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.CalendarContract
import androidx.core.content.ContextCompat
import com.example.personalcoach.domain.model.calendar.CalendarEvent
import com.example.personalcoach.domain.model.calendar.CalendarItem
import java.util.*

class CalendarEventProvider(   private val context: Context) {

    companion object {
        private val CALENDAR_PROJECTION = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars.SYNC_EVENTS,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.ACCOUNT_TYPE,
        )

        private val EVENT_PROJECTION = arrayOf(
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.ORGANIZER,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.ACCOUNT_TYPE,
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


    fun getEvents(): List<CalendarEvent>{
        val list = mutableListOf<CalendarEvent>()

        val uri = CalendarContract.Events.CONTENT_URI

        val selectionArgs = emptyArray<String>()

        val cursor = context.contentResolver
            .query(uri, EVENT_PROJECTION, "", selectionArgs, null)

        while (cursor?.moveToNext() == true){
            val calendarId = cursor.getLong(0)
            val organizer = cursor.getString(1)
            val title = cursor.getString(2)
            val description = cursor.getString(3)
            val dtstart = cursor.getString(4)
            val dtend = cursor.getString(5)
            val type = cursor.getString(6)

            println("$calendarId, $organizer, $title, $description, $dtstart, $dtend, $type")

            list.add(CalendarEvent(
                calendarId, organizer, title, description, dtstart, dtend, type)
            )


        }
        cursor?.close()


//        list.forEach{
//            println(it.toString())
//        }
        val sortedList = list.sortedWith(compareBy{it.id})

        return sortedList
    }

    fun getCalendars():List<CalendarItem>
    {
//        calendarItemAdapter.clearData()
        val list = mutableListOf<CalendarItem>()

        val uri = CalendarContract.Calendars.CONTENT_URI

        val selectionKey = ""

        val selectionArgs = emptyArray<String>()

        val cursor = context.contentResolver
            .query(uri, CALENDAR_PROJECTION,  selectionKey, selectionArgs, null)

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

        return list
    }

    fun createEvent(
        calendarEvent: CalendarEvent
    ){


        try {
//            val startMillis: Long = Calendar.getInstance().run {
//                set(2012, 9, 14, 7, 30)
//                timeInMillis
//            }
//            val endMillis: Long = Calendar.getInstance().run {
//                set(2012, 9, 14, 8, 45)
//                timeInMillis
//            }

            val values = ContentValues().apply {
                put(CalendarContract.Events.DTSTART, calendarEvent.dtstart?.toLong())
                put(CalendarContract.Events.DTEND, calendarEvent.dtend?.toLong())
                put(CalendarContract.Events.TITLE, calendarEvent.title)
                put(CalendarContract.Events.DESCRIPTION, calendarEvent.description)
                put(CalendarContract.Events.CALENDAR_ID, 3)
                put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles")
            }

            println(values)

//            if (ContextCompat.checkSelfPermission(context.applicationContext, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED){
                val uri: Uri? = context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
                val eventID: Long = uri?.lastPathSegment?.toLong() ?: 0

//            }
//            else{
//                println("---------------")
//            }

        }catch (e: Exception){
            e.printStackTrace()
        }
//        val eventID = uri?.lastPathSegment?.toLong();
//        println("EventId $eventID")

    }


}