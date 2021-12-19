package com.trailblazing.groovy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.trailblazing.groovy.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist.*
import kotlinx.android.synthetic.main.fragment_playlist_detail.*
import kotlinx.android.synthetic.main.playlist_item.playlist_name
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailsFragment : Fragment() {

    lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    val args: PlaylistDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)

        val id = args.playlistId

        setupViewModel()
        viewModel.getPlaylistDetails(id)
        observePlaylistDetails()
        observeLoader()

        return view
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner, { loading ->
            when (loading) {
                true -> details_loader.visibility = View.VISIBLE
                else -> details_loader.visibility = View.GONE
            }
        })
    }
    private fun observePlaylistDetails() {
        viewModel.playlistDetails.observe(this as LifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                setupUI(playlistDetails)
            } else {
                TODO()
            }
        }
    }

    private fun setupUI(playlistDetails: Result<PlaylistDetails>) {
        playlist_name.text = playlistDetails.getOrNull()!!.name
        playlist_details.text = playlistDetails.getOrNull()!!.details
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistDetailsViewModel::class.java]
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistDetailsFragment()
    }
}
