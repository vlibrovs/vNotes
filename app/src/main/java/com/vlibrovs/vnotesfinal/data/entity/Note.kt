package com.vlibrovs.vnotesfinal.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var content: String,
    val date: String
) {
    constructor(title: String, content: String, date: Date) : this(
        null,
        title,
        content,
        DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(date).apply {
            replaceFirst(this[0], this[0].uppercaseChar())
        }
    )
}