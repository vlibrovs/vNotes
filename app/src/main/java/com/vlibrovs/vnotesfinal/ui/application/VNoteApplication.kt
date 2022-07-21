package com.vlibrovs.vnotesfinal.ui.application

import android.app.Application
import com.vlibrovs.vnotesfinal.other.repository.NoteRepository

class VNoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }

}