package com.example.studentdiary.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(studentDetails: StudentDetails)

    @Query("SELECT * FROM StudentDetails ORDER BY id ASC")
    fun getAllUserData(): LiveData<List<StudentDetails>>

    @Query("UPDATE StudentDetails SET name = :name,phone_no = :phoneNo,roll_no = :rollNo,address_line = :addressLine  Where id = :studentID")
    suspend fun updateStudentDetails(
        studentID: Int,
        name: String,
        rollNo: String,
        phoneNo: String,
        addressLine: String,
    )

    @Delete
    suspend fun delete(studentDetails: StudentDetails)

    @Query("SELECT *  FROM StudentDetails WHERE name LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<StudentDetails>>
}

// '%' || :searchQuery || '%'")