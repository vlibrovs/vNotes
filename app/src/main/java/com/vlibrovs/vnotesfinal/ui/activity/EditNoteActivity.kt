package com.vlibrovs.vnotesfinal.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vlibrovs.vnotesfinal.data.entity.Note
import com.vlibrovs.vnotesfinal.databinding.ActivityEditNoteBinding
import com.vlibrovs.vnotesfinal.other.values.*
import com.vlibrovs.vnotesfinal.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private val viewModel: NoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // If activity called from main activity
        // Creating new note
        if (intent.getBooleanExtra(CREATE_NEW, true)) {
            val note = Note("", "", Date())
            binding.apply {
                date.text = note.date
                saveButton.setOnClickListener {
                    saveNote(note)
                }
                backButton.setOnClickListener {
                    finish()
                }
            }
        }
        // If activity called from view-note activity
        // Editing existing note
        else {
            val note = Note(
                id = intent.getIntExtra(ID, -1),
                title = intent.getStringExtra(TITLE)!!,
                content = intent.getStringExtra(CONTENT)!!,
                date = intent.getStringExtra(DATE)!!
            )

            binding.apply {
                title.setText(note.title)
                content.setText(note.content)
                date.text = note.date
                saveButton.setOnClickListener {
                    saveNote(note)
                }
            }
        }
    }

    private fun saveNote(note: Note) {
        val titleValue = binding.title.text.toString()
        val contentValue = binding.content.text.toString()
        // Do not allow user save note without a title or/and a content
        if (titleValue.isNotEmpty() && contentValue.isNotEmpty()) {
            note.title = titleValue
            note.content = contentValue
            if (note.id == null) viewModel.addNote(note)
            else {
                viewModel.updateNote(note)
                val data = Intent().apply {
                    putExtra(ID, note.id)
                    putExtra(TITLE, note.title)
                    putExtra(CONTENT, note.content)
                    putExtra(DATE, note.date)
                }
                setResult(Activity.RESULT_OK, data)
            }
            finish()
            overridePendingTransition(0, 0)
        }
    }

}