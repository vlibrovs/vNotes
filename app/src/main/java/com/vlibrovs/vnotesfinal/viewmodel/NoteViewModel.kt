package com.vlibrovs.vnotesfinal.viewmodel

import androidx.lifecycle.ViewModel
import com.vlibrovs.vnotesfinal.data.entity.Note
import com.vlibrovs.vnotesfinal.other.repository.NoteRepository

class NoteViewModel : ViewModel() {
    private val repository = NoteRepository.get()

    fun getNotes() = repository.getNotes()
    fun updateNote(note: Note) = repository.updateNote(note)
    fun addNote(note: Note) = repository.addNote(note)
    fun deleteNote(note: Note) = repository.deleteNote(note)
}