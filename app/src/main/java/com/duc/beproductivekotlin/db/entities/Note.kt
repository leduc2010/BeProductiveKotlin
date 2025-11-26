package com.duc.beproductivekotlin.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "note")
class Note(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var note: String = "",
    var date: String = "",
    var color: Int = 0
) : Serializable {
    constructor(note: String) : this() {
        val currentTime = System.currentTimeMillis()
        val sdf = SimpleDateFormat("'Thg' M dd yyyy, HH:mm", Locale.getDefault())
        this.date = sdf.format(java.util.Date(currentTime))
        this.note = note
    }
}