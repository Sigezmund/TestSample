package com.example.loginlesson26.data

import com.example.loginlesson26.TrackDTO
import okhttp3.internal.format
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrackService {

    @GET("$API_VERSION/")
    suspend fun loadTrack(
        @Query("method") method: String= METHOD,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("format") format: String = RESPONSE_FORMAT_JSON
    )
            : TrackDTO

    companion object {
        private const val API_VERSION = "2.0"
        private const val METHOD = "chart.gettoptracks"
        private const val API_KEY = "de2b582fe3d72b0e79b3ea5223800054"
        private const val RESPONSE_FORMAT_JSON = "json"
    }
}
