package com.duc.beproductivekotlin

import android.app.Application
import androidx.room.Room
import com.duc.beproductivekotlin.db.AppDB

class App : Application() {

    lateinit var storage: Storage
        private set

    lateinit var db: AppDB
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        storage = Storage()
        db = Room.databaseBuilder(this, AppDB::class.java, "database")
            .createFromAsset("databases/database.db").build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
