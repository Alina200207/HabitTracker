package com.example.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HabitsViewModel : ViewModel(), java.io.Serializable {
    val habits: LiveData<ArrayList<HabitInformation>> = MutableLiveData(HabitsList.getHabits())
}