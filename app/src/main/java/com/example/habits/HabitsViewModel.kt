package com.example.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HabitsViewModel(habits: ArrayList<HabitInformation>) : ViewModel() {
    val habits: LiveData<ArrayList<HabitInformation>> = MutableLiveData(habits)
}