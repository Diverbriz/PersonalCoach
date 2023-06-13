package com.example.personalcoach.domain.pref

import android.content.SharedPreferences

abstract class BaseSharedPreferences(
    protected val prefs: SharedPreferences
) {

    fun store(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun store(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun store(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun store(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun getBoolean(key: String) = prefs.getBoolean(key, false)

    fun getString(key: String): String = prefs.getString(key, null) ?: ""

    fun getInt(key: String) = prefs.getInt(key, 0)

    fun getLong(key: String) = prefs.getLong(key, 0)
}