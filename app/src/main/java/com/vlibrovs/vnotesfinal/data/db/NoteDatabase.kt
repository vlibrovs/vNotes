package com.vlibrovs.vnotesfinal.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vlibrovs.vnotesfinal.data.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}