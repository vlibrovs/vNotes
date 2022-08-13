package com.vlibrovs.vnotesfinal.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vlibrovs.vnotesfinal.data.entity.Note
import com.vlibrovs.vnotesfinal.databinding.ActivityNoteViewBinding
import com.vlibrovs.vnotesfinal.other.contract.EditNoteContract
import com.vlibrovs.vnotesfinal.viewmodel.NoteViewModel
import com.vlibrovs.vnotesfinal.other.values.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteViewBinding
    private val viewModel: NoteViewModel by viewModel()
    private val noteEditor = registerForActivityResult(EditNoteContract()) { note ->
        if (note != null) {
            binding.apply {
                title.text = note.title
                content.text = note.content
                date.text = note.date
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val note = Note(
            id = intent.getIntExtra(ID, -1),
            title = intent.getStringExtra(TITLE)!!,
            content = intent.getStringExtra(CONTENT)!!,
            date = intent.getStringExtra(DATE)!!
        )

        binding.apply {
            title.text = note.title
            content.text = note.content
            date.text = note.date

            editButton.setOnClickListener {
                noteEditor.launch(note)
            }

            deleteButton.setOnClickListener {
                makeConfirmRequest(this)
            }

            backButton.setOnClickListener {
                deleteConfirmRequest(this)
                finish()
            }

            positiveButton.setOnClickListener {
                deleteConfirmRequest(this)
                viewModel.deleteNote(note)
                finish()
            }

            negativeButton.setOnClickListener {
                deleteConfirmRequest(this)
            }
        }
    }

    // Shows editButton and deleteButton, hides positiveButton and negativeButton
    private fun deleteConfirmRequest(binding: ActivityNoteViewBinding) {
        binding.apply {
            deleteButton.visibility = View.VISIBLE
            editButton.visibility = View.VISIBLE
            positiveButton.visibility = View.GONE
            negativeButton.visibility = View.GONE
        }
    }

    // Hides editButton and deleteButton, shows positiveButton and negativeButton
    private fun makeConfirmRequest(binding: ActivityNoteViewBinding) {
        binding.apply {
            deleteButton.visibility = View.GONE
            editButton.visibility = View.GONE
            positiveButton.visibility = View.VISIBLE
            negativeButton.visibility = View.VISIBLE
        }
    }

}