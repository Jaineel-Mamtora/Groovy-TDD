package com.trailblazing.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.trailblazing.groovy.utils.BaseUnitTest
import com.trailblazing.groovy.utils.getValueForTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class PlaylistDetailsViewModelShould : BaseUnitTest() {

    private lateinit var viewModel: PlaylistDetailsViewModel
    private val id = "1"
    private val service: PlaylistDetailsService = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistDetailsFromService() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.playlistDetails.getValueForTest()
        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun emitPlaylistDetailsFromService() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails() = runBlockingTest {
        mockFailureCase()

        assertEquals(exception, viewModel.playlistDetails.getValueForTest()!!.exceptionOrNull())
    }

    private suspend fun mockSuccessfulCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(expected)
            }
        )
        viewModel = PlaylistDetailsViewModel(service)

        viewModel.getPlaylistDetails(id)
    }

    private suspend fun mockFailureCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        viewModel = PlaylistDetailsViewModel(service)

        viewModel.getPlaylistDetails(id)
    }

}
