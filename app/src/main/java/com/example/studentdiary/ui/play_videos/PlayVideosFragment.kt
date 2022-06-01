package com.example.studentdiary.ui.play_videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.studentdiary.databinding.FragmentPlayVideosBinding

class PlayVideosFragment : Fragment() {

    private var _binding: FragmentPlayVideosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val playVideosViewModel =
            ViewModelProvider(this)[PlayVideosViewModel::class.java]

        _binding = FragmentPlayVideosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPlayVideos
        playVideosViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}