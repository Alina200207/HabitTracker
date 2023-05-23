package com.example.data.database.database

import android.content.Context
import androidx.room.*
import com.example.domain.constants.Constants
import com.example.domain.entities.HabitInformation

@Database(entities = [HabitInformationEntity::class], version = 1, exportSchema = false)
abstract class HabitsDatabase: RoomDatabase() {
    abstract fun habitsDao(): HabitsDao

    companion object {
        @Volatile
        private var INSTANCE: HabitsDatabase? = null

        fun getDatabase(context: Context): HabitsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitsDatabase::class.java,
                    Constants.database
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}