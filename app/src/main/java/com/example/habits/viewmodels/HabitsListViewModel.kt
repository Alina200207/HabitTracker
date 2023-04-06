package com.example.habits.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habits.data.HabitsList
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.HabitType

class HabitsListViewModel(private val listType: HabitType) : ViewModel() {
    private var goodHabits =
        MutableLiveData(HabitsList.getGoodHabits())
    private var badHabits =
        MutableLiveData(HabitsList.getBadHabits())
    val habits: LiveData<ArrayList<HabitInformation>>
        get() =
            when (listType) {
                HabitType.Good -> goodHabits
                HabitType.Bad -> badHabits
            }


    fun getHabitsType(): HabitType {
        return listType
    }

    fun sortByTitle() {
        when (listType) {
            HabitType.Good -> goodHabits.value?.sortBy { it.habitTitle }
            HabitType.Bad -> badHabits.value?.sortBy { it.habitTitle }
        }
    }

    fun reverseSortByTitle() {
        when (listType) {
            HabitType.Good -> goodHabits.value?.sortByDescending { it.habitTitle }
            HabitType.Bad -> badHabits.value?.sortByDescending { it.habitTitle }
        }
    }

    fun sortByPriority() {
        when (listType) {
            HabitType.Good -> goodHabits.value?.sortBy { it.habitPriority }
            HabitType.Bad -> badHabits.value?.sortBy { it.habitPriority }
        }
    }

    fun reverseSortByPriority() {
        when (listType) {
            HabitType.Good -> goodHabits.value?.sortByDescending { it.habitPriority }
            HabitType.Bad -> badHabits.value?.sortByDescending { it.habitPriority }
        }
    }

    fun filteredByTitle(titlePart: String) {
        when (listType) {
            HabitType.Good -> goodHabits.value =
                goodHabits.value?.filter { elem -> elem.habitTitle.contains(titlePart) }
                    ?.let { ArrayList(it) }
            HabitType.Bad -> badHabits.value =
                badHabits.value?.filter { elem -> elem.habitTitle.contains(titlePart) }
                    ?.let { ArrayList(it) }
        }
    }

    fun setOriginState(){
        goodHabits =
            MutableLiveData(HabitsList.getGoodHabits())
        badHabits =
            MutableLiveData(HabitsList.getBadHabits())
    }

    companion object {
        class Factory(private val listType: HabitType) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                Log.i("main", "factory")
                return modelClass.getConstructor(HabitType::class.java).newInstance(listType)
            }
        }
    }
}