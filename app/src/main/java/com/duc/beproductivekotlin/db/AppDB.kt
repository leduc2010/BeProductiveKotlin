package com.duc.beproductivekotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duc.beproductivekotlin.db.DAO.ExploreDAO
import com.duc.beproductivekotlin.db.DAO.NoteDAO
import com.duc.beproductivekotlin.db.entities.Explore
import com.duc.beproductivekotlin.db.entities.Note

@Database(entities = [Note::class, Explore::class], version = 2, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun getNoteDao(): NoteDAO
    abstract fun getExploreDao(): ExploreDAO
}
