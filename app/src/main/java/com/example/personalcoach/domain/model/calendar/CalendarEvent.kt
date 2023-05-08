package com.example.personalcoach.domain.model.calendar

data class CalendarEvent(
    val id: Long,
    val organizer: String?,
    val title: String?,
    val description: String?,
    val dtstart: String?,
    val dtend: String?
){
    override fun toString(): String = "$id, $organizer, $title, $description, $dtstart, $dtend"
}
