package com.example.habits.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habitsData")
data class HabitInformation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val habitTitle: String,
    val habitDescription: String,
    val habitPriority: HabitPriority,
    val habitType: HabitType,
    val habitNumberExecution: Int,
    val frequency: String,
    val habitColor: Int,
    val stringHabitColor: String
) {
    constructor(
        habitTitle: String,
        habitDescription: String,
        habitPriority: HabitPriority,
        habitType: HabitType,
        habitNumberExecution: Int,
        frequency: String,
        habitColor: Int,
        stringHabitColor: String
    ) : this(
        0,
        habitTitle,
        habitDescription,
        habitPriority,
        habitType,
        habitNumberExecution,
        frequency,
        habitColor,
        stringHabitColor
    )
}
