package com.example.habits.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.habits.constants.Constants

@Entity(tableName = Constants.databaseName)
data class HabitInformation(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val habitTitle: String,
    val habitDescription: String,
    val habitPriority: HabitPriority,
    val habitType: HabitType,
    val habitNumberExecution: Int,
    val frequency: Int,
    val habitColor: Int,
    val stringHabitColor: String,
    var isSynced: Boolean = false,
    var uid: String
) {
    constructor(
        habitTitle: String,
        habitDescription: String,
        habitPriority: HabitPriority,
        habitType: HabitType,
        habitNumberExecution: Int,
        frequency: Int,
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
        stringHabitColor,
        false,
        "0"
    )

}
