package com.example.mydiary.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mydiary.entities.Note

@Dao
interface notedao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToDb(note: Note)
    @Query("SELECT* FROM note")
    fun fetchFromDb():List<Note>
}