package com.example.studentdiary.ui.view_records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewRecordsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is View Records Fragment"
    }
    val text: LiveData<String> = _text
}