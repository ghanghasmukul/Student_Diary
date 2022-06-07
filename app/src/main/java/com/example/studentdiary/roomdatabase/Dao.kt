package com.example.studentdiary.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(studentDetails: StudentDetails)

    @Query("SELECT * FROM StudentDetails ORDER BY id ASC")
    fun getAllUserData(): LiveData<List<StudentDetails>>

   @Update(onConflict = OnConflictStrategy.REPLACE)
   suspend fun update(studentDetails: StudentDetails)

    @Delete
    suspend fun delete(studentDetails: StudentDetails)
}