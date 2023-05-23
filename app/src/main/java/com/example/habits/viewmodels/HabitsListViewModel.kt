package com.example.habits.viewmodels

import androidx.lifecycle.*
import com.example.data.database.database.HabitsDatabaseRepository
import com.example.domain.entities.*
import javax.inject.Inject

class HabitsListViewModel @Inject constructor(private val repository: HabitsDatabaseRepository) : ViewModel() {
    private var goodHabits = repository.goodHabits.asLiveData()
    private var badHabits = repository.badHabits.asLiveData()

    private var sortHabits = MutableLiveData<SortData>()
    private var filterHabits = MutableLiveData("")

    private fun updateValue(listType: HabitType): List<HabitInformation>{
        var noValue = false
        val habitsList = when(listType){
            HabitType.Bad -> badHabits
            HabitType.Good -> goodHabits
        }.value?.filter { habit -> habit.isSynced != ServerSynchronization.NotSynchronizedDeletion } ?: arrayListOf()
        val filter = filterHabits.value ?: ""
        val sort = sortHabits.value ?: {
            noValue = true
        }
        if (noValue) {
            return habitsList.filter { elem -> elem.habitTitle.contains(filter) }
        }

        return (when (sort) {
            SortData(
                SortType.Ascending,
                SortField.Title
            ) -> ArrayList(habitsList.sortedBy { it.habitTitle })
            SortData(
                SortType.Descending,
                SortField.Title
            ) -> ArrayList(habitsList.sortedByDescending { it.habitTitle })
            SortData(
                SortType.Ascending,
                SortField.Priority
            ) -> ArrayList(habitsList.sortedBy { it.habitPriority })
            SortData(
                SortType.Descending,
                SortField.Priority
            ) -> ArrayList(habitsList.sortedByDescending { it.habitPriority })
            else -> ArrayList(habitsList.sortedBy { it.habitTitle })
        }).filter { elem -> elem.habitTitle.contains(filter) }
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


    fun getFilterText(): String{
        return filterHabits.value ?: ""
    }

//    companion object {
//        class HabitsListViewModelFactory(private val repository: HabitsDatabaseRepository) :
//            ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return modelClass.getConstructor(HabitsDatabaseRepository::class.java)
//                    .newInstance(repository)
//            }
//        }
//    }
}