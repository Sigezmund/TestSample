package com.example.loginlesson26


import com.example.loginlesson26.data.AppDatabase
import com.example.loginlesson26.data.TopTrackEntity
import com.example.loginlesson26.data.TrackService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Repositories(
    private val appDatabase: AppDatabase
) {
    private val trackService: TrackService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        trackService = retrofit.create(TrackService::class.java)
    }

    suspend fun getTrack(): List<TrackEntity> {
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