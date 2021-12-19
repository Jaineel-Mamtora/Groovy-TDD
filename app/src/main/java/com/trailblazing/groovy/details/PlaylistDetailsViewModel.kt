package com.trailblazing.groovy.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistDetailsViewModel(
    private val service: PlaylistDetailsService
) : ViewModel() {

    val playlistDetails: MutableLiveData<Result<PlaylistDetails>> = MutableLiveData()

    fun getPlaylistDetails(id: String) {
        service.fetchPlaylistDetails(id)
    }

}
