package com.example.studentdiary.ui.play_videos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studentdiary.databinding.FragmentPlayVideosBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class PlayVideosFragment : Fragment() {
    private var player: ExoPlayer? = null
    private var _binding: FragmentPlayVideosBinding? = null
    private lateinit var playerView: StyledPlayerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val playVideosViewModel =
            ViewModelProvider(this)[PlayVideosViewModel::class.java]
        _binding = FragmentPlayVideosBinding.inflate(inflater, container, false)
        playerView = _binding!!.exoPlayerView
        initPlayer()
        return _binding!!.root
    }


    private fun initPlayer() {
        player = ExoPlayer.Builder(requireContext()).build()
        playerView.player = player

        val videoUrl: ArrayList<String> = arrayListOf(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4"
        )

        videoUrl.forEach {
            val s = MediaItem.fromUri(it)
            player?.addMediaItem(s)
        }
        player?.prepare()
        player?.play()

    }

    private fun releasePlayer() {
        if (player != null) {
            player!!.release()
            player = null
        }
    }

    override fun onDestroyView() {
        releasePlayer()
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        player?.playWhenReady = false
    }

    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true

    }
}