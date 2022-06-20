package com.example.studentdiary.roomdatabase

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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

    fun deleteSelected(list: ArrayList<Int>){
        StudentRepository.deleteSelected(list)
    }

   fun updateStudentDetails(id : Int, studentDetails: StudentDetails){
       StudentRepository.updateStudent(id,studentDetails)
   }
    fun searchDatabase(searchQuery : String): LiveData<List<StudentDetails>>? {
        return StudentRepository.searchDatabase(searchQuery)
    }
}




