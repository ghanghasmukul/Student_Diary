package com.example.studentdiary.roomdatabase

import android.content.Context
import androidx.room.*
import com.example.studentdiary.typeConverters.Converters


@Database(entities = [StudentDetails::class], version = 16, exportSchema = false)
@TypeConverters(Converters::class)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao


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