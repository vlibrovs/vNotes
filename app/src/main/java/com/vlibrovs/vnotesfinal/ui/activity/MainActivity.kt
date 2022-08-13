package com.vlibrovs.vnotesfinal.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import com.vlibrovs.vnotesfinal.data.entity.Note
import com.vlibrovs.vnotesfinal.databinding.ActivityMainBinding
import com.vlibrovs.vnotesfinal.other.adapter.DefaultNoteAdapter
import com.vlibrovs.vnotesfinal.other.adapter.DeletableNoteAdapter
import com.vlibrovs.vnotesfinal.other.values.CREATE_NEW
import com.vlibrovs.vnotesfinal.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("NotifyDataSetChanged")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: NoteViewModel by viewModel()
    private val currentNotes = mutableListOf<Note>()
    private lateinit var defaultNoteAdapter: DefaultNoteAdapter
    private lateinit var deletableNoteAdapter: DeletableNoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getNotes().observe(this) {
            currentNotes.clear()
            currentNotes.addAll(it)
        }

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
            searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query?.isEmpty() == true) {
                        viewModel.getNotes().observe(this@MainActivity) {
                            currentNotes.clear()
                            currentNotes.addAll(it)
                            defaultNoteAdapter.notifyDataSetChanged()
                            deletableNoteAdapter.notifyDataSetChanged()
                        }
                    } else {
                        val tempList = mutableListOf<Note>()
                        for (note in currentNotes) {
                            if (note.title.contains(query!!, true)) {
                                tempList.add(note)
                            }
                        }
                        currentNotes.clear()
                        currentNotes.addAll(tempList)
                        defaultNoteAdapter.notifyDataSetChanged()
                        deletableNoteAdapter.notifyDataSetChanged()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isEmpty() == true) {
                        viewModel.getNotes().observe(this@MainActivity) {
                            currentNotes.clear()
                            currentNotes.addAll(it)
                            defaultNoteAdapter.notifyDataSetChanged()
                            deletableNoteAdapter.notifyDataSetChanged()
                        }
                    }
                    return true
                }
            })
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
        viewModel.getNotes().observe(this) {
            currentNotes.clear()
            currentNotes.addAll(it)
            binding.apply {
                noteRecyclerView.adapter = defaultNoteAdapter
                defaultNoteAdapter.notifyDataSetChanged()
            }
        }
        if (binding.searchField.query.isNotEmpty()) {
            val tempList = mutableListOf<Note>()
            for (note in currentNotes) {
                if (note.title.contains(binding.searchField.query, true)) {
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