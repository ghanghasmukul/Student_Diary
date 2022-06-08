package com.example.studentdiary.roomdatabase

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class StudentRepository {

    companion object {
        private var studentDatabase1: StudentDatabase? = null

        private fun initialiseDB(context: Context): StudentDatabase? {
            return StudentDatabase.getInstance(context)
        }

        fun insert(studentDetails: StudentDetails) {
            CoroutineScope(IO).launch {
                studentDatabase1?.studentDao()?.insert(studentDetails)
            }
        }

        fun getAllUserData(context: Context): LiveData<List<StudentDetails>> {
            studentDatabase1 = initialiseDB(context)
            return studentDatabase1!!.studentDao().getAllUserData()
        }

        fun delete(studentDetails: StudentDetails) {
            CoroutineScope(IO).launch {
                studentDatabase1?.studentDao()?.delete(studentDetails)
            }
        }


        fun updateStudent(id : Int,studentDetails: StudentDetails) {
            CoroutineScope(IO).launch {
                studentDetails.address_line?.let {
                    studentDetails.name?.let { it1 ->
                        studentDetails.phone_no?.let { it2 ->
                                studentDetails.roll_no?.let { it4 ->
                                    studentDatabase1!!.studentDao().updateStudentDetails(
                                        id,
                                        it1, it4, it2,
                                        it, studentDetails.locality
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }


