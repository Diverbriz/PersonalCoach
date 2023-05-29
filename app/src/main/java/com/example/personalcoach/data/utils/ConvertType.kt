package com.example.personalcoach.data.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Этот класс представляет собой конвертер даты и времени
 * в качестве параметров поступает date  разных параметрах
 */
/**
* @param time время в timestamp формате.
* @return format.time время переведенное в строку.
*/
fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}

/**
 * @return возвращает текущую дату.
 */
fun currentTimeToLong(): Long {
    return System.currentTimeMillis()
}

/**
 * @param date время в timestamp формате(String).
 * @return возврат числа в формате "yyyy.MM.dd HH:mm".
 */
fun convertDateToLong(date: String): Long {
    val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return df.parse(date)?.time ?: -1
}