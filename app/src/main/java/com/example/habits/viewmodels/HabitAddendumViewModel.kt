package com.example.habits.viewmodels

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habits.data.HabitsList
import com.example.habits.R
import com.example.habits.entities.*

class HabitAddendumViewModel(private val position: Int, private val previousType: HabitType): ViewModel() {
    var habitTitle: String = ""
    var habitDescription: String = ""
    var habitFrequencyCount: String = ""
    var habitFrequencyPeriod: String = ""
    var habitPriority = 0
    var habitType = 0
    var habitColor = ""

    init {
        if (position != -1){
            val habitItem = when (previousType) {
                HabitType.Good -> HabitsList.getGoodHabits()[position]
                HabitType.Bad -> HabitsList.getBadHabits()[position]
            }
            habitTitle = habitItem.habitTitle
            habitDescription = habitItem.habitDescription
            habitFrequencyCount = habitItem.habitNumberExecution.toString()
            habitFrequencyPeriod = habitItem.frequency
            habitPriority = habitItem.habitPriority.text
            habitType = habitItem.habitType.text
            habitColor = habitItem.stringHabitColor
        }
    }


    fun addHabit(habitPrioritySelected: Int, checkedButtonId: Int, checkedColorButtonId: Int){
        HabitsList.addHabit(
            HabitInformation(
                Id.getId(),
                habitTitle, habitDescription,
                HabitPriority.values()[habitPrioritySelected],
                HabitType.values()[checkedButtonId],
                habitFrequencyCount.toInt(),
                habitFrequencyPeriod,
                if (HabitColors.values()[checkedColorButtonId].text == R.string.red_color_text) {
                    Color.rgb(255, 0, 0)
                } else
                    Color.rgb(133, 200, 55),
                habitColor
            )
        )
    }

    fun changeHabit(habitPrioritySelected: Int, checkedButtonId: Int, checkedColorButtonId: Int){
        HabitsList.changeHabit(
            position, HabitInformation(
                when (previousType) {
                    HabitType.Good -> HabitsList.getGoodHabits()
                    HabitType.Bad -> HabitsList.getBadHabits()
                }[position].id,
                habitTitle, habitDescription,
                HabitPriority.values()[habitPrioritySelected],
                HabitType.values()[checkedButtonId],
                habitFrequencyCount.toInt(),
                habitFrequencyPeriod,
                if (HabitColors.values()[checkedColorButtonId].text == R.string.red_color_text) {
                    Color.rgb(255, 0, 0)
                } else
                    Color.rgb(133, 200, 55),
                habitColor
            ), previousType
        )
    }

    companion object {
        class Factory(private val position: Int, private val previousType: HabitType): ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(Int::class.java, HabitType::class.java).newInstance(position, previousType)
            }
        }
    }
}