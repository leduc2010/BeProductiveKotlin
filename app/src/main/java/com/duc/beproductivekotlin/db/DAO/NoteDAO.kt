package com.duc.beproductivekotlin.db.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.duc.beproductivekotlin.db.entities.Note

@Dao
interface NoteDAO {
    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    fun update(note: Note)
}
