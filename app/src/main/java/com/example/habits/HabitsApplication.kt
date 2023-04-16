package com.example.habits

import android.app.Application
import com.example.habits.database.HabitsDatabase
import com.example.habits.database.HabitsRepository

class HabitsApplication: Application() {
    private val database by lazy {
        HabitsDatabase.getDatabase(this)
    }
    val repository by lazy{
        HabitsRepository(database.habitsDao())
    }
}