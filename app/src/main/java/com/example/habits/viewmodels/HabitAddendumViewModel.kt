package com.example.habits.viewmodels

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habits.data.HabitsList
import com.example.habits.entities.*

class HabitAddendumViewModel(private val position: Int, private val previousType: HabitType) :
    ViewModel() {
    private val _habit = MutableLiveData(
        HabitInformation(
            0, "", "",
            HabitPriority.High, HabitType.Good, 0, "", 0, ""
        )
    )

    val habit: LiveData<HabitInformation> get() = _habit

    init {
        if (position != -1) {
            _habit.value = when (previousType) {
                HabitType.Good -> HabitsList.getGoodHabits()[position]
                HabitType.Bad -> HabitsList.getBadHabits()[position]
            }
        }
    }

    fun changeViewModelState(
        title: String, description: String, priority: HabitPriority,
        type: HabitType, executionNumber: Int, frequency: String, habitColors: HabitColors, stringColor: String
    ) {
        _habit.value = _habit.value?.id?.let {
            HabitInformation(
                it, title, description, priority,
                type, executionNumber, frequency,
                when (habitColors) {
                    HabitColors.Green -> Color.rgb(0, 255, 0)
                    HabitColors.Red -> Color.rgb(255, 0, 0)
                },
            stringColor)
        }
    }

    fun addHabit() {
        _habit.value?.let {
            HabitsList.addHabit(
                it
            )
        }
    }

    fun changeHabit() {
        _habit.value?.let {
            HabitsList.changeHabit(
                position, it, previousType
            )
        }
    }

    companion object {
        class Factory(private val position: Int, private val previousType: HabitType) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(Int::class.java, HabitType::class.java)
                    .newInstance(position, previousType)
            }
        }
    }
}