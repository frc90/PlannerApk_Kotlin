package com.frc90.plannerapk_kotlin.base

import android.content.Context
import android.content.SharedPreferences

class Pref(context: Context){
    private val PREFS_NAME = ""
    private val SHARED_IS_LOGGED = "id_logged"
    private val SHARED_USER_NAME = "user_name"
    private val SHARED_PASSWORD = "password"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var isLogged: Boolean
        get() = prefs.getBoolean(SHARED_IS_LOGGED, false)
        set(value) = prefs.edit().putBoolean(SHARED_IS_LOGGED, value).apply()

    var userName: String
        get() = prefs.getString(SHARED_USER_NAME, "").toString()
        set(value) = prefs.edit().putString(SHARED_USER_NAME, value).apply()

    var password: String
        get() = prefs.getString(SHARED_PASSWORD, "").toString()
        set(value) = prefs.edit().putString(SHARED_PASSWORD, value).apply()
}
