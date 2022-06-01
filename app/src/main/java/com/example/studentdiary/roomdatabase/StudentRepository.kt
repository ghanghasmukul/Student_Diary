package com.example.studentdiary.roomdatabase

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class StudentRepository {

    companion object {
        private var studentDatabase1 : StudentDatabase? = null

        private fun intialiseDB(context: Context): StudentDatabase? {
            return StudentDatabase.getInstance(context)
        }

        fun insert(context: Context, studentDatabase: StudentDetails) {
            studentDatabase1  = intialiseDB(context)

            CoroutineScope(IO).launch {
               studentDatabase1?.studentDao()?.insert(studentDatabase)
            }
        }

        fun getAllUserData(context: Context): LiveData<List<StudentDetails>> {
            studentDatabase1 = intialiseDB(context)
            return studentDatabase1!!.studentDao().getAllUserData()
        }
        fun delete(context: Context, studentDatabase: StudentDetails){
          studentDatabase1 = intialiseDB(context)


        }

    }
}