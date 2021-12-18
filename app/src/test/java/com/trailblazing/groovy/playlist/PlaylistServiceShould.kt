package com.trailblazing.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.trailblazing.groovy.utils.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val playlistAPI: PlaylistAPI = mock()
    private val playlists: List<PlaylistRaw> = mock()

    @Test
    fun fetchPlaylistsFromAPI() = runBlockingTest {
        service = PlaylistService(playlistAPI)

        service.fetchPlaylists().first()

        verify(playlistAPI, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest {

        mockFailureCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockSuccessfulCase() {
        whenever(playlistAPI.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(playlistAPI)
    }

    private suspend fun mockFailureCase() {
        whenever(playlistAPI.fetchAllPlaylists()).thenThrow(RuntimeException("Damn backend developer"))

        service = PlaylistService(playlistAPI)
    }
}
