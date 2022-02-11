package com.example.loginlesson26.presentation


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginlesson26.data.Repositories
import com.example.loginlesson26.domain.TrackEntity
import com.example.loginlesson26.domain.User
import kotlinx.coroutines.*
import okhttp3.*
import java.security.MessageDigest

class LoginViewModel(
    private val repositories: Repositories
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)
    val userLiveData = MutableLiveData<User>()
    val authIsSuccessful = MutableLiveData<Boolean>()

    fun onSignInClick(login: String, password: String) {
        scope.launch {
            try {
                val resultAuth = withContext(Dispatchers.IO) {
                    repositories.getAuthorization(login, password)
                }
                authIsSuccessful.value = resultAuth
                if (resultAuth) {
                    userLiveData.value = User(login, password)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}


//    fun onSignInClick(login: String, password: String) {
//
//        scope.launch {
//            try {
//                val apiSignature =
//                    "api_key" + APIKEY + "methodauth.getMobileSessionpassword" + password + "username" + login + APISIG
//                val hexString = StringBuilder()
//                val digest: MessageDigest = MessageDigest.getInstance("MD5")
//                digest.update(apiSignature.toByteArray(charset("UTF-8")))
//                val messageDigest: ByteArray = digest.digest()
//                for (aMessageDigest in messageDigest) {
//                    var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
//                    while (h.length < 2) h = "0$h"
//                    hexString.append(h)
//                }
//                val urlParameter =
//                    "method=auth.getMobileSession&api_key=$APIKEY&password=$password&username=$login&api_sig=$hexString"
//                val urlAdress = "https://ws.audioscrobbler.com/2.0/?$urlParameter"
//                val response = withContext(Dispatchers.IO) {
//                    okHttpClient
//                        .newCall(
//                            Request.Builder()
//                                .url(urlAdress)
//                                .post(RequestBody.create(null, ByteArray(0)))
//                                .build()
//                        ).execute()
//                }
//                authIsSuccessful.value = response.body.toString().contains("ok")
//                userLiveData.value = User(login, password)
//
//            } catch (e: Exception) {
//                authIsSuccessful.value = false
//                e.printStackTrace()
//            }
//        }
//    }
//
//    fun loadTrack() {
//        scope.launch {
//            try {
//                val tracks = withContext(Dispatchers.IO) {
//                    repositories.getTrack()
//                }
//                tracksLiveData.value = tracks
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    override fun onCleared() {
//        scope.cancel()
//        super.onCleared()
//    }
//
//    companion object {
//        const val APIKEY: String = "de2b582fe3d72b0e79b3ea5223800054"
//        const val APISIG: String = "060a6afbf90488af42d093118b6b4909"
//    }
//}




