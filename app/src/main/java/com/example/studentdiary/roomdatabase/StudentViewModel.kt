package com.example.studentdiary.roomdatabase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class StudentViewModel :ViewModel() {

    fun insert(context: Context, studentDetails: StudentDetails)
    {
        StudentRepository.insert(context,studentDetails)
    }

    fun getAllUserData(context: Context):LiveData<List<StudentDetails>>
    {
        return StudentRepository.getAllUserData(context)
    }
    fun delete(context: Context,studentDetails: StudentDetails){
      StudentRepository.delete(context,studentDetails)

    }
}


