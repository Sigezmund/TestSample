package com.example.loginlesson26


import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.loginlesson26.domain.User

class LoginManager(private val preference: CustomPreference) {

    val isLoggedInLiveData = MutableLiveData<Boolean>()

    val isLoggedIn: Boolean
        get() = isLoggedInLiveData.value ?: false

    fun login(user: User) {
        preference.login = user.userName
        preference.password = user.password
        isLoggedInLiveData.value=true
    }

    fun logout() {
        preference.login = ""
        preference.password = ""
        isLoggedInLiveData.value=false
    }

}

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