package com.vlibrovs.vnotesfinal.other.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlibrovs.vnotesfinal.data.entity.Note
import com.vlibrovs.vnotesfinal.databinding.DeletableNoteRecyclerItemBinding
import com.vlibrovs.vnotesfinal.ui.activity.MainActivity
import com.vlibrovs.vnotesfinal.viewmodel.NoteViewModel

class DeletableNoteAdapter(
    private val notes: MutableList<Note>,
    private val viewModel: NoteViewModel,
    private val recyclerView: RecyclerView,
    private val defaultAdapter: DefaultNoteAdapter,
    private val activity: MainActivity
) : RecyclerView.Adapter<DeletableNoteAdapter.DeletableNoteViewHolder>() {

    inner class DeletableNoteViewHolder(val binding: DeletableNoteRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeletableNoteViewHolder {
        val binding = DeletableNoteRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeletableNoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeletableNoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.apply {
            noteTitle.text = if (note.title.length > 50) note.title.substring(0..50)+"..." else note.title
            noteText.text = if (note.content.length > 150) note.content.substring(0..150)+"..." else note.content
            deleteButton.setOnClickListener {
                makeConfirmRequest(this)
            }
            negativeButton.setOnClickListener {
                deleteConfirmRequest(this)
            }
            positiveButton.setOnClickListener {
                viewModel.deleteNote(note)
                notes.remove(note)
                this@DeletableNoteAdapter.notifyItemRemoved(position)
                recyclerView.adapter = defaultAdapter
                activity.leaveDeleteMode()
            }
        }
    }

    override fun getItemCount() = notes.size

    private fun makeConfirmRequest(binding: DeletableNoteRecyclerItemBinding) {
        binding.apply {
            deleteButton.visibility = View.GONE
            buttonsLayout.visibility = ViewGroup.VISIBLE
        }
    }

    private fun deleteConfirmRequest(binding: DeletableNoteRecyclerItemBinding) {
        binding.apply {
            deleteButton.visibility = View.VISIBLE
            buttonsLayout.visibility = ViewGroup.GONE
        }
    }
}