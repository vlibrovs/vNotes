package com.vlibrovs.vnotesfinal.other.adapter

import android.content.Context
import android.content.Intent
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
            noteTitle.text = if (note.title.length > 50) note.title.substring(0..50)+"..." else note.title
            noteText.text = if (note.content.length > 150) note.content.substring(0..150)+"..." else note.content
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