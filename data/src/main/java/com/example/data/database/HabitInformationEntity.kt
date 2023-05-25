package com.example.data.database

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
    var uid: String,
    var doneDates: List<Int>
)

