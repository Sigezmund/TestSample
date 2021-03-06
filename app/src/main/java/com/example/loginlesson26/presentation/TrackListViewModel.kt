package com.example.loginlesson26.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginlesson26.data.LoginRepositoryImpl
import com.example.loginlesson26.domain.TrackEntity
import kotlinx.coroutines.*

class TrackListViewModel(
    private val repositories: LoginRepositoryImpl
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