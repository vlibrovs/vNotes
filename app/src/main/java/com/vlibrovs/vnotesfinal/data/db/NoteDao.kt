package com.vlibrovs.vnotesfinal.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vlibrovs.vnotesfinal.data.entity.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getNotes(): LiveData<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

}