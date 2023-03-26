package com.example.habits

import android.graphics.Color

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
