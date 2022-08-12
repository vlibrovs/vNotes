package com.vlibrovs.vnotesfinal.other.di

import androidx.room.Room
import com.vlibrovs.vnotesfinal.data.db.NoteDatabase
import com.vlibrovs.vnotesfinal.other.repository.NoteRepository
import com.vlibrovs.vnotesfinal.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            NoteDatabase::class.java,
            "vnote_note_database"
        ).build()
    }
    single {
        NoteRepository(get())
    }
    viewModel {
        NoteViewModel(get())
    }
}