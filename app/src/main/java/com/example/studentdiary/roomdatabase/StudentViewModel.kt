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
        StudentRepository.insert(studentDetails)
        getAllUserData(context)
    }

    fun getAllUserData(context: Context):LiveData<List<StudentDetails>>
    {
        return StudentRepository.getAllUserData(context)

    }
    fun delete(studentDetails: StudentDetails){
      StudentRepository.delete(studentDetails)

    }

   fun updateStudentDetails(id : Int, studentDetails: StudentDetails){
       StudentRepository.updateStudent(id,studentDetails)
   }
}


