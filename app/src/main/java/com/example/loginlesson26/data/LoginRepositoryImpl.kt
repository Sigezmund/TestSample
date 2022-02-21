package com.example.loginlesson26.data


import com.example.loginlesson26.domain.TrackEntity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest


interface LoginRepository {
    suspend fun getAuthorization(login: String, password: String): Boolean
    suspend fun getTrack(): List<TrackEntity>
}

class LoginRepositoryImpl(
    private val appDatabase: AppDatabase
) : LoginRepository {
    private val trackService: TrackService
    private val okHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        trackService = retrofit.create(TrackService::class.java)
    }

    override suspend fun getAuthorization(login: String, password: String): Boolean {
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
            val result = withContext(Dispatchers.IO) {
                response.body?.string()
            }
            return result.toString().contains("ok")

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }


    override suspend fun getTrack(): List<TrackEntity> {
        try {
            val trackEntity = trackService.loadTrack()
                .tracks?.track?.map {
                    TrackEntity(
                        artist = it.artist?.name.orEmpty(),
                        image = it.image?.get(1)?.text.orEmpty(),
                        name = it.name.orEmpty(),
                    )
                }.orEmpty()
            val topTrackEntity = trackEntity.map {
                TopTrackEntity(
                    name = it.name,
                    artist = it.artist,
                    image = it.image
                )
            }
            appDatabase.getTopTrackEntity().saveTrack(topTrackEntity)
            return trackEntity
        } catch (e: Exception) {
            e.printStackTrace()
            val trackAppDatabase = appDatabase.getTopTrackEntity().getTrack().map {
                TrackEntity(
                    name = it.name,
                    artist = it.artist,
                    image = it.image
                )
            }
            return trackAppDatabase
        }
    }

    suspend fun saveTrackInBackground() {
        try {
            val trackEntity = trackService.loadTrack()
                .tracks?.track?.map {
                    TrackEntity(
                        artist = it.artist?.name.orEmpty(),
                        image = it.image?.get(1)?.text.orEmpty(),
                        name = it.name.orEmpty(),
                    )
                }.orEmpty()
            val topTrackEntity = trackEntity.map {
                TopTrackEntity(
                    name = it.name,
                    artist = it.artist,
                    image = it.image
                )
            }
            appDatabase.getTopTrackEntity().saveTrack(topTrackEntity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    companion object {
        const val APIKEY: String = "de2b582fe3d72b0e79b3ea5223800054"
        const val APISIG: String = "060a6afbf90488af42d093118b6b4909"
    }

}


//    private val okHttpClient = OkHttpClient.Builder().build()
//    private val gson = Gson()
//
//    fun getTrack(): List<TrackEntity> {
//        try {
//            val urlAdressGetTracks =
//                "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=${APIKEY}&format=json"
//            val responseTrack =
//                okHttpClient
//                    .newCall(
//                        Request.Builder()
//                            .url(urlAdressGetTracks)
//                            .build()
//                    ).execute()
//            val jsonString = responseTrack.body?.string().orEmpty()
//            val json = gson.fromJson(jsonString, TrackDTO::class.java)
//
//            val trackEntity = json.tracks?.track?.map {
//
//                TrackEntity(
//                    artist = it.artist?.name.orEmpty(),
//                    image = it.image?.get(1)?.text.orEmpty(),
//                    name = it.name.orEmpty(),
//                )
//            }.orEmpty()
//            val topTrackEntity = trackEntity.map {
//                TopTrackEntity(
//                    name = it.name,
//                    artist = it.artist,
//                    image = it.image
//                )
//            }
//            appDatabase.getTopTrackEntity().saveTrack(topTrackEntity)
//            return trackEntity
//        } catch (e: Exception) {
//            e.printStackTrace()
//            val trackAppDatabase = appDatabase.getTopTrackEntity().getTrack().map {
//                TrackEntity(
//                    name = it.name,
//                    artist = it.artist,
//                    image = it.image
//                )
//            }
//            return trackAppDatabase
//        }
//    }
//
//    companion object {
//        const val APIKEY: String = "de2b582fe3d72b0e79b3ea5223800054"
//    }
//}