package com.example.studentdiary.ui.add_records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddRecordsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Add Records Fragment"
    }
    val text: LiveData<String> = _text
}