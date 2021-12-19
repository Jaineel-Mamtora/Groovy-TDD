package com.trailblazing.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.trailblazing.groovy.utils.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PlaylistDetailsServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistDetailsService
    private val api: PlaylistDetailsAPI = mock()
    private val id = "1"
    private val playlistDetails: PlaylistDetails = mock()
    private val exception = RuntimeException("Damn backend developers again 500!!")

    @Test
    fun fetchPlaylistDetailsFromAPI() = runBlockingTest {
        mockSuccessfulCase()

        service.fetchPlaylistDetails(id).single()

        verify(api, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).first())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylistDetails(id).single().exceptionOrNull()?.message
        )
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchPlaylistDetails(id)).thenThrow(exception)

        service = PlaylistDetailsService(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchPlaylistDetails(id)).thenReturn(playlistDetails)

        service = PlaylistDetailsService(api)
    }

}