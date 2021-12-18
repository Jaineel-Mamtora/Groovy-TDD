package com.trailblazing.groovy.playlist

import com.trailblazing.groovy.R
import com.trailblazing.groovy.utils.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class PlaylistMapperShould : BaseUnitTest() {

    private val playlistRaw = PlaylistRaw("1", "da name", "da category")
    private val playlistRawRock = PlaylistRaw("1", "da name", "rock")
    private val mapper = PlaylistMapper()
    private val playlists = mapper(listOf(playlistRaw))
    private val playlist = playlists.first()
    private val playlistRock = mapper(listOf(playlistRawRock)).first()

    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.mipmap.playlist, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.mipmap.rock, playlistRock.image)
    }

}
