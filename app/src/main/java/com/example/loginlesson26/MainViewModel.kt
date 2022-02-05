package com.example.loginlesson26

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import java.security.MessageDigest

class MainViewModel : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)
    private val okHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()
    val userLiveData = MutableLiveData<User>()
    val tracksLiveData = MutableLiveData<List<TopTrack>>()
    val authIsSuccessful = MutableLiveData<Boolean>()

    init {
        getTracks()
    }

    fun onSignInClick(login: String, password: String) {

        scope.launch {
            try {
                val apiSignature =
                    "api_key" + APIKEY + "methodauth.getMobileSessionpassword" + password + "username" + login + APISIG
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
                    "method=auth.getMobileSession&api_key=$APIKEY&password=$password&username=$login&api_sig=$hexString"
                val urlAdress = "https://ws.audioscrobbler.com/2.0/?$urlParameter"
                val response = withContext(Dispatchers.IO) {
                    okHttpClient
                        .newCall(
                            Request.Builder()
                                .url(urlAdress)
                                .post(RequestBody.create(null, ByteArray(0)))
                                .build()
                        ).execute()
                }
                authIsSuccessful.value = response.body.toString().contains("ok")
                userLiveData.value = User(login, password)
            } catch (e: Exception) {
                authIsSuccessful.value = false
                e.printStackTrace()
            }
        }
    }

    fun getTracks() {
        scope.launch {
            val urlAdressGetTracks =
                "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=$APIKEY&format=json"
            try {
                val responseTrack = withContext(Dispatchers.IO) {
                    okHttpClient
                        .newCall(
                            Request.Builder()
                                .url(urlAdressGetTracks)
                                .build()
                        ).execute()
                }
                val jsonString = responseTrack.body?.string().orEmpty()
                val json = gson.fromJson(jsonString, TrackDTO::class.java)
                tracksLiveData.value = json.tracks?.track?.map {
                    TopTrack(
                        artist = it.artist,
                        duration = it.duration,
                        image = it.image,
                        listeners = it.listeners,
                        mbid = it.mbid,
                        name = it.name,
                        playcount = it.playcount,
                        streamable = it.streamable,
                        url = it.url
                    )
                }.orEmpty()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val APIKEY: String = "de2b582fe3d72b0e79b3ea5223800054"
        private const val APISIG: String = "060a6afbf90488af42d093118b6b4909"
    }
}
