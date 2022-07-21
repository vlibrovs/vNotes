package com.vlibrovs.vnotesfinal.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.vlibrovs.vnotesfinal.R
import com.vlibrovs.vnotesfinal.data.entity.Note
import com.vlibrovs.vnotesfinal.databinding.ActivityMainBinding
import com.vlibrovs.vnotesfinal.other.adapter.DefaultNoteAdapter
import com.vlibrovs.vnotesfinal.other.adapter.DeletableNoteAdapter
import com.vlibrovs.vnotesfinal.other.values.CREATE_NEW
import com.vlibrovs.vnotesfinal.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel
    private val currentNotes = mutableListOf<Note>()
    private lateinit var defaultNoteAdapter: DefaultNoteAdapter
    private lateinit var deletableNoteAdapter: DeletableNoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        viewModel.getNotes().observe(this, Observer {
            currentNotes.clear()
            currentNotes.addAll(it)
        })

        defaultNoteAdapter = DefaultNoteAdapter(currentNotes)
        deletableNoteAdapter = DeletableNoteAdapter(currentNotes, viewModel, binding.noteRecyclerView, defaultNoteAdapter, this)

        binding.apply {
            noteRecyclerView.adapter = defaultNoteAdapter

            addButton.setOnClickListener {
                createNote()
            }
            deleteButton.setOnClickListener {
                enterDeleteMode()
            }
            completeButton.setOnClickListener {
                leaveDeleteMode()
            }
            searchButton.setOnClickListener {
                if (searchField.text.toString().isEmpty()) {
                    viewModel.getNotes().observe(this@MainActivity, Observer {
                        currentNotes.clear()
                        currentNotes.addAll(it)
                        defaultNoteAdapter.notifyDataSetChanged()
                        deletableNoteAdapter.notifyDataSetChanged()
                    })
                }
                else {
                    val tempList = mutableListOf<Note>()
                    for (note in currentNotes) {
                        if (note.title.contains(searchField.text.toString(), true)) {
                            tempList.add(note)
                        }
                    }
                    currentNotes.clear()
                    currentNotes.addAll(tempList)
                    defaultNoteAdapter.notifyDataSetChanged()
                    deletableNoteAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun enterDeleteMode() {
        binding.apply {
            addButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
            completeButton.visibility = View.VISIBLE
            noteRecyclerView.adapter = deletableNoteAdapter
            deletableNoteAdapter.notifyDataSetChanged()
        }
    }

    fun leaveDeleteMode() {
        binding.apply {
            addButton.visibility = View.VISIBLE
            deleteButton.visibility = View.VISIBLE
            completeButton.visibility = View.GONE
            noteRecyclerView.adapter = defaultNoteAdapter
            defaultNoteAdapter.notifyDataSetChanged()
        }
    }

    private fun createNote() {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra(CREATE_NEW, true)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes().observe(this, Observer {
            currentNotes.clear()
            currentNotes.addAll(it)
        })

        binding.apply {
            noteRecyclerView.adapter = defaultNoteAdapter
            defaultNoteAdapter.notifyDataSetChanged()
        }
    }
}