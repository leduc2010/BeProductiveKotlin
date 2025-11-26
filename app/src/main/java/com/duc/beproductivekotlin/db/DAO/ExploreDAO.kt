package com.duc.beproductivekotlin.db.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.duc.beproductivekotlin.db.entities.Explore
import com.duc.beproductivekotlin.db.entities.Note

@Dao
interface ExploreDAO {
    @Insert
    fun insert(explore: Explore)

    @Delete
    fun delete(explore: Explore)

    @Query("SELECT * FROM explore Where type = :type")
    fun getAllExploresByType(type: String): List<Explore>

    @Update
    fun update(note: Note)
}
