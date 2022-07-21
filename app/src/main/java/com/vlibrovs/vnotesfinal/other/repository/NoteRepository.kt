package com.vlibrovs.vnotesfinal.other.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.vlibrovs.vnotesfinal.data.db.NoteDatabase
import com.vlibrovs.vnotesfinal.data.entity.Note
import java.util.concurrent.Executors

class NoteRepository private constructor(context: Context){

    companion object {
        private var instance: NoteRepository? = null

        fun initialize(context: Context) {
            if (instance == null) instance = NoteRepository(context)
        }

        fun get(): NoteRepository {
            return instance ?:
            throw IllegalStateException("NoteRepository must be initialized (NoteRepository.initialize(context: Context))")
        }
    }

    private val database = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        "vnote_note_database"
    ).build()
    private val dao = database.getNoteDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getNotes() = dao.getNotes()

    fun addNote(note: Note) {
        executor.execute {
            dao.insert(note)
        }
    }

    fun updateNote(note: Note) {
        executor.execute {
            dao.update(note)
        }
    }

    fun deleteNote(note: Note) {
        executor.execute {
            dao.delete(note)
        }
    }

}