package com.example.habits.database

import android.content.Context
import androidx.room.*
import com.example.habits.HabitTypeConverter
import com.example.habits.constants.Constants
import com.example.habits.entities.HabitInformation

@Database(entities = [HabitInformation::class], version = 1, exportSchema = false)
@TypeConverters(HabitTypeConverter::class)
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