package com.example.domain.entities

import com.example.domain.constants.Constants
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class HabitExecutionInformationTest {
    private val habit =
        HabitInformation("Медитация", "", HabitPriority.High, HabitType.Good, 8, 4, 0, "red")

    @Before
    fun setup() {
        val currentTime = (System.currentTimeMillis() / 1000).toInt()
        val habitBegin = 1684943751
        val additionalTimeInCurrentPeriod =
            currentTime - (((currentTime - habitBegin) / Constants.secondsInDay) % habit.frequency) * Constants.secondsInDay
        habit.doneDates = listOf(habitBegin, additionalTimeInCurrentPeriod)
    }

    @Test
    fun countDone_zero() {
        habit.doneDates = listOf()
        val result = HabitInformation.getCountDone(habit)
        Assertions.assertEquals(0, result)
    }

    @Test
    fun countDone_onePeriod() {
        val currentTime = (System.currentTimeMillis() / 1000).toInt()
        habit.doneDates = listOf(currentTime, currentTime + 1000)
        val result = HabitInformation.getCountDone(habit)
        Assertions.assertEquals(2, result)
    }

    @Test
    fun countDone_manyPeriods() {
        val currentTime = (System.currentTimeMillis() / 1000).toInt()
        habit.doneDates += listOf(currentTime, currentTime + 1000)
        val result = HabitInformation.getCountDone(habit)
        Assertions.assertEquals(3, result)
    }

    @Test
    fun getHabitDoneInfo_zeroExecutions() {
        habit.doneDates = listOf()
        val result = HabitInformation.getHabitDoneInfo(habit)
        Assertions.assertEquals(Pair(habit.habitNumberExecution, habit.frequency), result)
    }

    @Test
    fun getHabitDoneInfo_manyExecutions() {
        val currentTime = (System.currentTimeMillis() / 1000).toInt()
        habit.doneDates += listOf(currentTime)
        val restDays =
            habit.frequency - (((currentTime - habit.doneDates[0]) / Constants.secondsInDay) % habit.frequency)
        val result = HabitInformation.getHabitDoneInfo(habit)
        Assertions.assertEquals(Pair(habit.habitNumberExecution - 2, restDays), result)
    }
}