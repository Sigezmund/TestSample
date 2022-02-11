package com.example.loginlesson26

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.loginlesson26.data.AppDatabase
import com.example.loginlesson26.data.Repositories
import com.example.loginlesson26.data.TopTrackEntity
import com.example.loginlesson26.domain.TrackEntity
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private lateinit var repositories: Repositories


    override fun doWork(): Result {

        return try {
            repositories.saveTrackInBackground()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}