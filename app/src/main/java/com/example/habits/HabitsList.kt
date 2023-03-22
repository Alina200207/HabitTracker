package com.example.habits

object HabitsList {
    private val habits: ArrayList<HabitInformation> = ArrayList()
    fun addHabit(habit: HabitInformation){
        habits.add(habit)
    }
    fun removeHabit(index: Int){
        habits.removeAt(index)
    }
    fun getHabits(): ArrayList<HabitInformation>{
        return habits
    }
    fun changeHabit(position: Int, habit: HabitInformation){
        habits[position] = habit
    }
}