package com.example.habits.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitInformation(
    @PrimaryKey val id: Int,
    val habitTitle: String,
    val habitDescription: String,
    val habitPriority: HabitPriority,
    val habitType: HabitType,
    val habitNumberExecution: Int,
    val frequency: String,
    val habitColor: Int,
    val stringHabitColor: String
)
