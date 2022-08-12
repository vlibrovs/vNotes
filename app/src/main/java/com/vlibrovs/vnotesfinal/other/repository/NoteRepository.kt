package com.vlibrovs.vnotesfinal.other.repository

import com.vlibrovs.vnotesfinal.data.db.NoteDatabase
import com.vlibrovs.vnotesfinal.data.entity.Note

class NoteRepository(database: NoteDatabase) {

    private val dao = database.getNoteDao()

    fun getNotes() = dao.getNotes()

    suspend fun addNote(note: Note) = dao.insert(note)

    suspend fun updateNote(note: Note) = dao.update(note)

    suspend fun deleteNote(note: Note) = dao.delete(note)

}