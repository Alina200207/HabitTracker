package com.example.habits.viewmodels

import android.graphics.Color
import androidx.lifecycle.*
import com.example.habits.database.HabitsDatabaseRepository
import com.example.habits.entities.*
import com.example.habits.network.HabitsServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HabitAddendumViewModel(
    private val id: Long,
    private val repository: HabitsDatabaseRepository,
    private val serverRepository: HabitsServerRepository,
    private val coroutineScope: CoroutineScope
) :
    ViewModel() {
    private var _habit = MutableLiveData(
        HabitInformation(
            "", "",
            HabitPriority.High, HabitType.Good, 0, 0, 0, ""
        )
    )

    private var habitId = MutableLiveData<Long>()
    val request = MediatorLiveData<HabitInformation>().apply {
        fun updateId() {
            habitId.value?.let {
                repository.getHabitById(it).observeForever { valuet ->
                    value = when (valuet) {
                        null -> value
                        else -> valuet

                    }
                }
            }
        }

        fun updateHabit() {
            value = _habit.value
        }
        addSource(_habit) { updateHabit() }
        addSource(habitId) { updateId() }
    }


    init {
        habitId.value = id
    }

    fun changeViewModelState(
        title: String,
        description: String,
        priority: HabitPriority,
        type: HabitType,
        executionNumber: Int,
        frequency: Int,
        habitColors: HabitColors,
        stringColor: String
    ) {
        _habit.value = request.value?.id?.let {
            HabitInformation(
                it, title, description, priority,
                type, executionNumber, frequency,
                when (habitColors) {
                    HabitColors.Green -> Color.rgb(0, 255, 0)
                    HabitColors.Red -> Color.rgb(255, 0, 0)
                },
                stringColor, false, request.value?.uid ?: "0"
            )
        }
    }

    fun addHabit() {
        request.value?.let {
            coroutineScope.launch {
                serverRepository.insertOrUpdateHabit(it, repository.insert(it))
            }
        }
    }

    fun changeHabit() {
        request.value?.let {
            viewModelScope.launch {
                repository.update(it)
            }
        }
        request.value?.let {
            coroutineScope.launch {
                habitId.value?.let { it1 -> serverRepository.insertOrUpdateHabit(it, it1) }
            }
        }

    }

    fun deleteHabit() {
        if (habitId.value != -1L) {
            request.value?.let {
                viewModelScope.launch {
                    repository.delete(it)
                }
                coroutineScope.launch {
                    serverRepository.deleteHabit(it)
                }
            }
        }
    }


    companion object {
        class Factory(
            private val position: Long,
            private val repository: HabitsDatabaseRepository,
            private val serverRepository: HabitsServerRepository,
            private val coroutineScope: CoroutineScope
        ) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(
                    Long::class.java,
                    HabitsDatabaseRepository::class.java,
                    HabitsServerRepository::class.java,
                    CoroutineScope::class.java
                )
                    .newInstance(
                        position,
                        repository,
                        serverRepository,
                        coroutineScope
                    )
            }
        }
    }
}