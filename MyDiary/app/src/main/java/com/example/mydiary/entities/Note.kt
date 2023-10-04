package com.example.mydiary.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note")
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "date_time")
    var date_Time:String,
    @ColumnInfo(name = "subtitle")
    var subtitle:String,
    @ColumnInfo(name = "notetext")
    var notetext:String,
    @ColumnInfo(name = "imagepath")
    var imagepath:String,
    @ColumnInfo(name = "color")
    var color:Int,
)
