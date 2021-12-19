package com.trailblazing.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.trailblazing.groovy.utils.BaseUnitTest
import com.trailblazing.groovy.utils.getValueForTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class PlaylistDetailsViewModelShould : BaseUnitTest() {

    private lateinit var viewModel: PlaylistDetailsViewModel
    private val id = "1"
    private val service: PlaylistDetailsService = mock()

    @Test
    fun getPlaylistDetailsFromService() {
        viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)
        viewModel.playlistDetails.getValueForTest()
        verify(service, times(1)).fetchPlaylistDetails(id)
    }

}
