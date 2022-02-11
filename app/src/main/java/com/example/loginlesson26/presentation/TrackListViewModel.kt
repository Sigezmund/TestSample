package com.example.loginlesson26.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginlesson26.data.Repositories
import com.example.loginlesson26.domain.TrackEntity
import com.example.loginlesson26.domain.User
import kotlinx.coroutines.*
import okhttp3.OkHttpClient

class TrackListViewModel(
    private val repositories: Repositories
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)
    val tracksLiveData = MutableLiveData<List<TrackEntity>>()

    fun loadTrack() {
        scope.launch {
            try {
                val tracks = withContext(Dispatchers.IO) {
                    repositories.getTrack()
                }
                tracksLiveData.value = tracks
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