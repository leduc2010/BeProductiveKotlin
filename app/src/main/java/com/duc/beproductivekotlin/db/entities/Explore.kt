package com.duc.beproductivekotlin.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "explore")
class Explore(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var type: String,
    var title: String,
    var date: String,
    var image: String,
    var content: String,
    var author: String
): Serializable


