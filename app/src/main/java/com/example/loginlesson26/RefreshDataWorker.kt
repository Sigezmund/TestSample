package com.example.loginlesson26

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.loginlesson26.data.LoginRepositoryImpl

class RefreshDataWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private lateinit var repositories: LoginRepositoryImpl


    override suspend fun doWork(): Result {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentIntent(PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE))
                .setSmallIcon(R.drawable.ic_baseline_refresh_24)
                .setOngoing(true)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setContentTitle(context.getString(R.string.app_name))
                .setLocalOnly(true)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setContentText("Updating Top Tracks")
                .build()


        repositories.saveTrackInBackground()
        setForeground(ForegroundInfo(1, notification))
        return Result.success()
}

companion object {
            private const val NOTIFICATION_CHANNEL_ID = "1"
        private const val NOTIFICATION_CHANNEL_NAME = "Work Service"
    const val WORK_NAME = "RefreshDataWorker"
}
}