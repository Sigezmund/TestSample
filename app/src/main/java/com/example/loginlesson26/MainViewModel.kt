package com.example.loginlesson26

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import java.security.MessageDigest

class MainViewModel : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)
    private val okHttpClient = OkHttpClient.Builder().build()

    val userLiveData = MutableLiveData<User>()
    val authIsSuccessful = MutableLiveData<Boolean>()

    fun autorization() {
        scope.launch {

            try {
                var user = userLiveData.value
                val apiSignature =
                    "api_key" + user?.apiKey + "methodauth.getMobileSessionpassword" + user?.password + "username" + user?.userName + user?.apiSig
                val hexString = StringBuilder()
                val digest: MessageDigest = MessageDigest.getInstance("MD5")
                digest.update(apiSignature.toByteArray(charset("UTF-8")))
                val messageDigest: ByteArray = digest.digest()

                for (aMessageDigest in messageDigest) {
                    var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                    while (h.length < 2) h = "0$h"
                    hexString.append(h)
                }

                val urlParameter =
                    "method=auth.getMobileSession&api_key=" + user?.apiKey + "&password=" + user?.password + "&username=" + user?.userName + "&api_sig=" + hexString
                val urlAdress = "https://ws.audioscrobbler.com/2.0/?$urlParameter"

                withContext(Dispatchers.IO) {
                    val response = okHttpClient
                        .newCall(
                            Request.Builder()
                                .url(urlAdress)
                                .build()
                        ).execute()
                    authIsSuccessful.value = response.body.toString().contains("ok")
                    Log.d("error1",response.body.toString())
                }

            } catch (ex: Exception) {
                authIsSuccessful.value = false
                Log.d("error2","err")
            }
        }
    }
}
