package com.example.studentdiary.roomdatabase

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase

class StudentViewModel : ViewModel() {

    fun insert(context: Context, studentDetails: StudentDetails)
    {
        StudentRepository.insert(context,studentDetails)
        getAllUserData(context)
    }

    fun getAllUserData(context: Context):LiveData<List<StudentDetails>>
    {
        return StudentRepository.getAllUserData(context)

    }
    fun delete(context: Context,studentDetails: StudentDetails){
      StudentRepository.delete(context,studentDetails)
        getAllUserData(context)

    }
    fun update(context: Context,studentDetails: StudentDetails){
        StudentRepository.update(context,studentDetails)
        getAllUserData(context)
    }
}


