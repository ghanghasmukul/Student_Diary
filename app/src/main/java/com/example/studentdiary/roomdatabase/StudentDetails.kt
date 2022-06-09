package com.example.studentdiary.roomdatabase


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "StudentDetails")
data class StudentDetails (
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "roll_no") var roll_no: String?,
    @ColumnInfo(name = "phone_no") var phone_no: String?,
    @ColumnInfo(name = "address_line") var address_line : String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
):Parcelable



