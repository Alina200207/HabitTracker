package com.example.habits.entities

import com.example.habits.entities.HabitPriority
import com.example.habits.entities.HabitType

data class HabitInformation(
    var id: Int,
    var habitTitle: String,
    var habitDescription: String,
    var habitPriority: HabitPriority,
    var habitType: HabitType,
    var habitNumberExecution: Int,
    var frequency: String,
    var habitColor: Int,
    var stringHabitColor: String
) : java.io.Serializable
