package com.example.loginlesson26

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.loginlesson26.data.AppDatabase
import com.example.loginlesson26.data.TopTrackEntity
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val okHttpClient = OkHttpClient.Builder().build()
    private val gson = Gson()
    private val appDatabase=AppDatabase.build(context)



    override fun doWork(): Result {
        while (true) {
            try {
                val apiKey = "de2b582fe3d72b0e79b3ea5223800054"
                val urlAdressGetTracks =
                    "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=${apiKey}&format=json"
                val responseTrack =
                    okHttpClient
                        .newCall(
                            Request.Builder()
                                .url(urlAdressGetTracks)
                                .build()
                        ).execute()
                val jsonString = responseTrack.body?.string().orEmpty()
                val json = gson.fromJson(jsonString, TrackDTO::class.java)

                val trackEntity = json.tracks?.track?.map {

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
    }

}