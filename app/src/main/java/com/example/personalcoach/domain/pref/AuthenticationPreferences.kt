package com.example.personalcoach.domain.pref

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationPreferences @Inject constructor(
        @ApplicationContext context: Context
) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    private val PREF_SESSION_TOKEN_KEY = "ApplicationPreference.SessionToken"

    fun storeToken(token: String) {
        prefs.edit().putString(PREF_SESSION_TOKEN_KEY, token).apply()
    }

    fun getToken(): String = prefs.getString(PREF_SESSION_TOKEN_KEY, null) ?:""
}