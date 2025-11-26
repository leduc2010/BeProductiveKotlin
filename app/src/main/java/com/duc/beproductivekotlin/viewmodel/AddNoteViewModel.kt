package com.duc.beproductivekotlin.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.duc.beproductivekotlin.App
import com.duc.beproductivekotlin.db.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddNoteViewModel : BaseViewModel() {
    private var color = 0

    fun setColor(color: Int) {
        this.color = color
    }

    fun saveNote(content: String) {
        val note = Note(content)
        note.color = color
        viewModelScope.launch(Dispatchers.IO) {
            App.instance.db.getNoteDao().insert(note)
        }
    }
}
