package com.example.data.database.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.constants.Constants
import com.example.domain.entities.HabitPriority
import com.example.domain.entities.HabitType
import com.example.domain.entities.ServerSynchronization


@Entity(tableName = Constants.database)
data class HabitInformationEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val habitTitle: String,
    val habitDescription: String,
    val habitPriority: HabitPriority,
    val habitType: HabitType,
    val habitNumberExecution: Int,
    val frequency: Int,
    val habitColor: Int,
    val stringHabitColor: String,
    var isSynced: ServerSynchronization = ServerSynchronization.NotSynchronizedChange,
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
        ServerSynchronization.NotSynchronizedChange,
        "0"
    )

}

