package com.example.habits.viewmodels

import android.graphics.Color
import androidx.lifecycle.*
import com.example.data.database.HabitsDatabaseRepository
import com.example.domain.entities.*
import com.example.domain.usecases.HabitsStoragesUpdatingUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.lastOrNull

class HabitAddendumViewModel @AssistedInject constructor(
    @Assisted private val id: Long,
    private val databaseRepository: HabitsDatabaseRepository,
    private val habitsUseCase: HabitsStoragesUpdatingUseCase,
    private val appScope: CoroutineScope
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
        suspend fun updateId() {
            habitId.value?.let {
                if (it != -1L) {
                    val habit = databaseRepository.getHabitById(it)
                    val newValue = habit.lastOrNull()
                    value = when (newValue) {
                        null -> value
                        else -> newValue
                    }
                }
            }
        }


        fun updateHabit() {
            value = _habit.value
        }
        addSource(_habit)
        { updateHabit() }
        addSource(habitId)
        { viewModelScope.launch { (updateId()) } }
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
                stringColor, ServerSynchronization.NotSynchronizedChange, request.value?.uid ?: "0",
                request.value?.doneDates ?: listOf()
            )
        }
    }

    fun saveHabit() {
        request.value?.let {
            appScope.launch {
                habitsUseCase.saveHabit(it)
            }
        }
    }

    fun deleteHabit() {
        if (habitId.value != -1L) {
            request.value?.let {
                appScope.launch {
                    habitsUseCase.deleteHabit(it)
                }
            }
        }
    }
}