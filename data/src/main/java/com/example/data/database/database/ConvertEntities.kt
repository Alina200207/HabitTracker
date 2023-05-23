package com.example.data.database.database

import com.example.domain.entities.HabitInformation

object ConvertEntities {
    fun fromHabitInformationToHabitInformationEntity(habit: HabitInformation): HabitInformationEntity {
        return HabitInformationEntity(
            habit.id,
            habit.habitTitle,
            habit.habitDescription,
            habit.habitPriority,
            habit.habitType,
            habit.habitNumberExecution,
            habit.frequency,
            habit.habitColor,
            habit.stringHabitColor,
            habit.isSynced,
            habit.uid
        )
    }

    fun fromHabitInformationEntityToHabitInformation(habit: HabitInformationEntity): HabitInformation {
        return HabitInformation(
            habit.id,
            habit.habitTitle,
            habit.habitDescription,
            habit.habitPriority,
            habit.habitType,
            habit.habitNumberExecution,
            habit.frequency,
            habit.habitColor,
            habit.stringHabitColor,
            habit.isSynced,
            habit.uid
        )
    }
}