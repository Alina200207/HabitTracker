package com.example.habits.viewmodels

import androidx.lifecycle.*
import com.example.data.database.HabitsDatabaseRepository
import com.example.domain.entities.*
import com.example.domain.usecases.HabitsSortAndFilterUseCase
import javax.inject.Inject

class HabitsListViewModel @Inject constructor(
    private val databaseRepository: HabitsDatabaseRepository,
    private val habitsSortAndFilterUseCase: HabitsSortAndFilterUseCase
) :
    ViewModel() {
    private var goodHabits = databaseRepository.goodHabits.asLiveData()
    private var badHabits = databaseRepository.badHabits.asLiveData()

    private var sortHabits = MutableLiveData<SortData>()
    private var filterHabits = MutableLiveData("")

    private fun updateValue(listType: HabitType): List<HabitInformation> {
        val habitsList = when (listType) {
            HabitType.Bad -> badHabits
            HabitType.Good -> goodHabits
        }.value?.filter { habit -> habit.isSynced != ServerSynchronization.NotSynchronizedDeletion }
            ?: arrayListOf()
        val filter = filterHabits.value ?: ""
        val sort = sortHabits.value
            ?: return habitsList.filter { elem -> elem.habitTitle.contains(filter) }

        return habitsSortAndFilterUseCase.sortAndFilterHabits(sort, filter, habitsList)
    }

    val resultGoodHabits = MediatorLiveData<List<HabitInformation>>().apply {
        addSource(goodHabits) { value = updateValue(HabitType.Good) }
        addSource(sortHabits) { value = updateValue(HabitType.Good) }
        addSource(filterHabits) { value = updateValue(HabitType.Good) }
    }
    val resultBadHabits = MediatorLiveData<List<HabitInformation>>().apply {
        addSource(badHabits) { value = updateValue(HabitType.Bad) }
        addSource(sortHabits) { value = updateValue(HabitType.Bad) }
        addSource(filterHabits) { value = updateValue(HabitType.Bad) }
    }

    fun sortByTitle() {
        sortHabits.value = SortData(SortType.Ascending, SortField.Title)
    }

    fun reverseSortByTitle() {
        sortHabits.value = SortData(SortType.Descending, SortField.Title)
    }

    fun sortByPriority() {
        sortHabits.value = SortData(SortType.Ascending, SortField.Priority)
    }

    fun reverseSortByPriority() {
        sortHabits.value = SortData(SortType.Descending, SortField.Priority)
    }

    fun filteredByTitle(titlePart: String) {
        filterHabits.value = titlePart
    }

    fun getFilterText(): String {
        return filterHabits.value ?: ""
    }
}