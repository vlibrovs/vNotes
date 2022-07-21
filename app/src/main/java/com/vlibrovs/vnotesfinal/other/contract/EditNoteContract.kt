package com.vlibrovs.vnotesfinal.other.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.vlibrovs.vnotesfinal.data.entity.Note
import com.vlibrovs.vnotesfinal.other.values.*
import com.vlibrovs.vnotesfinal.ui.activity.EditNoteActivity

class EditNoteContract : ActivityResultContract<Note, Note?>() {
    override fun createIntent(context: Context, input: Note): Intent {
        val intent = Intent(context, EditNoteActivity::class.java).apply {
            putExtra(TITLE, input.title)
            putExtra(CONTENT, input.content)
            putExtra(DATE, input.date)
            putExtra(ID, input.id)
            putExtra(CREATE_NEW, false)
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }

        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Note? {
        if (resultCode != Activity.RESULT_OK || intent == null) return null
        return intent.run { Note(
            id = getIntExtra(ID, -1),
            title = getStringExtra(TITLE) ?: "",
            content = getStringExtra(CONTENT) ?: "",
            date = getStringExtra(DATE) ?: ""
        )}

    }
}