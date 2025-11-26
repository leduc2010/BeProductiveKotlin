package com.duc.beproductivekotlin.viewmodel

import androidx.lifecycle.LiveData
import com.duc.beproductivekotlin.App
import com.duc.beproductivekotlin.db.entities.Note

class HomeVM : BaseViewModel() {
    val listNotes: LiveData<List<Note>> = App.instance.db.getNoteDao().getAllNotes()

    fun addNote(note: Note) {
        Thread { App.instance.db.getNoteDao()!!.insert(note) }.start()
    }

    fun deleteNote(note: Note) {
        Thread { App.instance.db.getNoteDao()!!.delete(note) }.start()
    }
}
