package com.vlibrovs.vnotesfinal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlibrovs.vnotesfinal.data.entity.Note
import com.vlibrovs.vnotesfinal.other.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    fun getNotes() =  repository.getNotes()
    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.updateNote(note)
            }
            catch (e: Exception) {
                Log.d("EXC", "${e.message}")
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            try {
                repository.addNote(note)
            }
            catch (e: Exception) {
                Log.d("EXC", "${e.message}")
            }
        }
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        try {
            repository.deleteNote(note)
        }
        catch (e: Exception) {
            Log.d("EXC", "${e.message}")
        }
    }
}