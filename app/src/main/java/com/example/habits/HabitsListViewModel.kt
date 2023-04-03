package com.example.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HabitsListViewModel : ViewModel() {
    private val goodHabits: MutableLiveData<ArrayList<HabitInformation>> = MutableLiveData(HabitsList.getGoodHabits())
    private val badHabits: MutableLiveData<ArrayList<HabitInformation>> = MutableLiveData(HabitsList.getBadHabits())
    fun getGoodHabits(): LiveData<ArrayList<HabitInformation>>{
        return goodHabits
    }
    fun getBadHabits(): LiveData<ArrayList<HabitInformation>>{
        return badHabits
    }
}