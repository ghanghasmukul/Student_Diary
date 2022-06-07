package com.example.studentdiary.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [StudentDetails::class], version = 4, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): Dao


    companion object{
        @Volatile
        var instance:StudentDatabase?=null
        private const val DATABASE_NAME="StudentDetails"

        fun getInstance(context: Context):StudentDatabase?
        {
            if(instance == null)
            {
                synchronized(StudentDatabase::class.java)
                {
                    if(instance == null)
                    {
                        Room.databaseBuilder(context,StudentDatabase::class.java,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build().also { instance = it }
                    }
                }
            }

            return instance
        }

    }
}