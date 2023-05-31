package com.example.domain.usecases

import com.example.domain.entities.HabitInformation
import com.example.domain.entities.SortData
import com.example.domain.entities.SortField
import com.example.domain.entities.SortType

class HabitsSortAndFilterUseCase {
    fun sortAndFilterHabits(
        sort: SortData,
        filter: String,
        habits: List<HabitInformation>
    ): List<HabitInformation> {
        return (when (sort) {
            SortData(
                SortType.Ascending,
                SortField.Title
            ) -> habits.sortedBy { it.habitTitle }
            SortData(
                SortType.Descending,
                SortField.Title
            ) -> habits.sortedByDescending { it.habitTitle }
            SortData(
                SortType.Ascending,
                SortField.Priority
            ) -> habits.sortedBy { it.habitPriority }
            SortData(
                SortType.Descending,
                SortField.Priority
            ) -> habits.sortedByDescending { it.habitPriority }
            else -> habits.sortedBy { it.habitTitle }
        }).filter { elem -> elem.habitTitle.contains(filter) }
    }
}