package com.example.studentdiary.ui.play_videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayVideosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Play Videos Fragment"
    }
    val text: LiveData<String> = _text
}