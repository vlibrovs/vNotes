package com.vlibrovs.vnotesfinal.other.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlibrovs.vnotesfinal.data.entity.Note
import com.vlibrovs.vnotesfinal.databinding.NoteRecyclerItemBinding
import com.vlibrovs.vnotesfinal.other.values.*
import com.vlibrovs.vnotesfinal.ui.activity.ViewNoteActivity

class DefaultNoteAdapter(private val notes: List<Note>) : RecyclerView.Adapter<DefaultNoteAdapter.DefaultNoteViewHolder>() {

    inner class DefaultNoteViewHolder(val binding: NoteRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultNoteViewHolder {
        val binding = NoteRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefaultNoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DefaultNoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.apply {
            noteTitle.text = note.title
            noteText.text = note.content
            root.setOnClickListener {
                viewNote(note, root.context)
            }
        }
    }

    override fun getItemCount() = notes.size

    private fun viewNote(note: Note, context: Context) {
        val intent = Intent(context, ViewNoteActivity::class.java)
        intent.putExtra(TITLE, note.title)
        intent.putExtra(CONTENT, note.content)
        intent.putExtra(DATE, note.date)
        intent.putExtra(ID, note.id)
        context.startActivity(intent)
    }
}