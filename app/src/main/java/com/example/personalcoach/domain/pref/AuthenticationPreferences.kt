package com.example.personalcoach.domain.pref

import android.content.SharedPreferences
import javax.inject.Inject

class AuthenticationPreferences @Inject constructor(
    prefs: SharedPreferences
) : BaseSharedPreferences(prefs) {

    private val PREF_SESSION_TOKEN_KEY = "ApplicationPreference.SessionToken"

    fun storeToken(token: String) {
        prefs.edit().putString(PREF_SESSION_TOKEN_KEY, token).apply()
    }

    fun getToken(): String = getString(PREF_SESSION_TOKEN_KEY)

}