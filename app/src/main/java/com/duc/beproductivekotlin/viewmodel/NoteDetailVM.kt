package com.duc.beproductivekotlin.viewmodel

import com.duc.beproductivekotlin.App
import com.duc.beproductivekotlin.db.entities.Note


class NoteDetailVM : BaseViewModel() {
    var color = 0

    fun updateNote(note: Note) {
        Thread { App.instance.db.getNoteDao()?.update(note) }.start()
    }
}
