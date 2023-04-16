package com.example.habits

import androidx.room.TypeConverter
import com.example.habits.entities.HabitType

class HabitTypeConverter {
        @TypeConverter
        fun fromInt(value: Int?): HabitType {
            return HabitType.values()[value!!]
        }

        @TypeConverter
        fun toInt(habitType: HabitType): Int {
            return HabitType.values().indexOf(habitType)
        }
}