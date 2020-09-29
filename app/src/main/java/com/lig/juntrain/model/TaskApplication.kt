package com.lig.juntrain.model

import android.app.Application
import androidx.room.Room

class TaskApplication : Application() {
    private val TASK_DATABASE = "task_db"

    companion object {
        lateinit var database: TaskDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, TaskDatabase::class.java,
            TASK_DATABASE).build()
    }
}