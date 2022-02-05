package com.example.loginlesson26


import android.content.Context

class CustomPreference(context: Context) {
    private val prefs = context.getSharedPreferences("com.example.login", Context.MODE_PRIVATE)

    var login: String = ""
        get() {
            return prefs.getString(LOGIN, "").orEmpty()
        }
        set(value) {
            field = value
            prefs.edit().putString(LOGIN, value).apply()
        }
    var password: String = ""
        get() {
            return prefs.getString(PASSWORD, "").orEmpty()
        }
        set(value) {
            field = value
            prefs.edit().putString(PASSWORD, value).apply()
        }

    companion object {
        private const val LOGIN = "login"
        private const val PASSWORD = "password"
    }
}