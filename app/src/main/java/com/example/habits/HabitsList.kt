package com.example.habits

import android.util.Log

object HabitsList {
    private val goodHabits: ArrayList<HabitInformation> = ArrayList()
    private val badHabits: ArrayList<HabitInformation> = ArrayList()
    fun addHabit(habit: HabitInformation) {
        Log.i("main", habit.habitType.toString())
        when (habit.habitType) {
            HabitType.Good -> goodHabits.add(habit)
            HabitType.Bad -> badHabits.add(habit)
        }
    }

    fun removeHabit(index: Int, habitType: HabitType) {
        when (habitType) {
            HabitType.Bad -> badHabits.removeAt(index)
            HabitType.Good -> goodHabits.removeAt(index)
        }
    }

    fun getGoodHabits(): ArrayList<HabitInformation> {
        return goodHabits
    }

    fun getBadHabits(): ArrayList<HabitInformation> {
        return badHabits
    }

    fun changeHabit(position: Int, habit: HabitInformation, previousType: HabitType) {
        if (previousType == habit.habitType) {
            when (habit.habitType) {
                HabitType.Good -> goodHabits[position] = habit
                HabitType.Bad -> badHabits[position] = habit
            }
        } else {
            when (habit.habitType) {
                HabitType.Good -> {
                    goodHabits.add(habit)
                    badHabits.removeAt(position)
                }
                HabitType.Bad -> {
                    badHabits.add(habit)
                    goodHabits.removeAt(position)
                }
            }
        }

    }

    fun size(): Int {
        return goodHabits.size
    }
}