package com.example.domain.entities

import com.example.domain.constants.Constants
import kotlin.math.ceil
import kotlin.math.floor

data class HabitInformation(
    var id: Long = 0,
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
        "0",
        listOf()
    )

    companion object {
        fun getHabitDoneInfo(habit: HabitInformation): Pair<Int, Int> {
            val doneDates = habit.doneDates
            return if (doneDates.size == 1) {
                Pair(habit.habitNumberExecution - 1, habit.frequency)
            } else {
                val daysFromStart =
                    floor((((System.currentTimeMillis() / 1000).toInt() - doneDates[0]) / (Constants.secondsInDay).toDouble())).toInt()
                val daysSinceBeginningOfCurrentPeriod = daysFromStart % habit.frequency
                val countDone = getCountDone(habit)
                val numberOfRemainingExecutions = habit.habitNumberExecution - countDone
                val daysRemained = habit.frequency - daysSinceBeginningOfCurrentPeriod
                Pair(numberOfRemainingExecutions, daysRemained)
            }
        }

        fun getCountDone(habit: HabitInformation): Int {
            val doneDates = habit.doneDates
            return if (doneDates.isEmpty())
                0
            else {
                val daysFromStart =
                    floor((((System.currentTimeMillis() / 1000).toInt() - doneDates[0]) / (Constants.secondsInDay).toDouble())).toInt()
                val daysSinceBeginningOfCurrentPeriod = daysFromStart % habit.frequency
                val startOfCurrentPeriodInSeconds =
                    doneDates[0] + (daysFromStart - daysSinceBeginningOfCurrentPeriod) * Constants.secondsInDay
                doneDates.count { elem -> elem >= startOfCurrentPeriodInSeconds }
            }
        }
    }
}
