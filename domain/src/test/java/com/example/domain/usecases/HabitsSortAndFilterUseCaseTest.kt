package com.example.domain.usecases

import com.example.domain.entities.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class HabitsSortAndFilterUseCaseTest {
    private val habitsList: List<HabitInformation> = listOf(
        HabitInformation("Медитация", "", HabitPriority.High, HabitType.Good, 8, 7, 0, "red"),
        HabitInformation("Отжимания", "", HabitPriority.Low, HabitType.Good, 8, 7, 0, "red"),
        HabitInformation("Курение", "", HabitPriority.High, HabitType.Bad, 8, 7, 0, "red"),
        HabitInformation("Чтение", "", HabitPriority.Medium, HabitType.Good, 8, 7, 0, "red"),
        HabitInformation("Хм", "", HabitPriority.Low, HabitType.Bad, 8, 7, 0, "red")
    )
    private val habitsSortAndFilterUseCase = HabitsSortAndFilterUseCase()
    private var sortData: SortData = SortData(SortType.Ascending, SortField.Title)
    private var filter = ""

    @Before
    fun setup() {
        filter = ""
        sortData = SortData(SortType.Ascending, SortField.Title)
    }

    @Test
    fun filter_emptyFilter() {
        filter = ""
        val expected = listOf(
            HabitInformation("Курение", "", HabitPriority.High, HabitType.Bad, 8, 7, 0, "red"),
            HabitInformation("Медитация", "", HabitPriority.High, HabitType.Good, 8, 7, 0, "red"),
            HabitInformation("Отжимания", "", HabitPriority.Low, HabitType.Good, 8, 7, 0, "red"),
            HabitInformation("Хм", "", HabitPriority.Low, HabitType.Bad, 8, 7, 0, "red"),
                    HabitInformation("Чтение", "", HabitPriority.Medium, HabitType.Good, 8, 7, 0, "red")
        )
        val resultHabitsList =
            habitsSortAndFilterUseCase.sortAndFilterHabits(sortData, filter, habitsList)
        Assertions.assertIterableEquals(expected, resultHabitsList)
    }

    @Test
    fun filter_notEmptyFilter() {
        filter = "ен"
        val expected = listOf(
            HabitInformation("Курение", "", HabitPriority.High, HabitType.Bad, 8, 7, 0, "red"),
            HabitInformation("Чтение", "", HabitPriority.Medium, HabitType.Good, 8, 7, 0, "red")
        )
        val result = habitsSortAndFilterUseCase.sortAndFilterHabits(sortData, filter, habitsList)
        Assertions.assertIterableEquals(expected, result)
    }

    @Test
    fun sort_title() {
        sortData = SortData(SortType.Descending, SortField.Title)
        val expected = listOf(
            HabitInformation("Чтение", "", HabitPriority.Medium, HabitType.Good, 8, 7, 0, "red"),
            HabitInformation("Хм", "", HabitPriority.Low, HabitType.Bad, 8, 7, 0, "red"),
            HabitInformation("Отжимания", "", HabitPriority.Low, HabitType.Good, 8, 7, 0, "red"),
            HabitInformation("Медитация", "", HabitPriority.High, HabitType.Good, 8, 7, 0, "red"),
            HabitInformation("Курение", "", HabitPriority.High, HabitType.Bad, 8, 7, 0, "red")

        )
        val result = habitsSortAndFilterUseCase.sortAndFilterHabits(sortData, filter, habitsList)
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun sort_priority() {
        sortData = SortData(SortType.Ascending, SortField.Priority)
        val expected = listOf(
            HabitInformation("Медитация", "", HabitPriority.High, HabitType.Good, 8, 7, 0, "red"),
            HabitInformation("Курение", "", HabitPriority.High, HabitType.Bad, 8, 7, 0, "red"),
            HabitInformation("Чтение", "", HabitPriority.Medium, HabitType.Good, 8, 7, 0, "red"),
            HabitInformation("Отжимания", "", HabitPriority.Low, HabitType.Good, 8, 7, 0, "red"),
            HabitInformation("Хм", "", HabitPriority.Low, HabitType.Bad, 8, 7, 0, "red")
        )
        val result = habitsSortAndFilterUseCase.sortAndFilterHabits(sortData, filter, habitsList)
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun sortAndFilter_priority() {
        filter = "е"
        sortData = SortData(SortType.Descending, SortField.Priority)
        val expected = listOf(
            HabitInformation("Чтение", "", HabitPriority.Medium, HabitType.Good, 8, 7, 0, "red"),
            HabitInformation("Медитация", "", HabitPriority.High, HabitType.Good, 8, 7, 0, "red"),
            HabitInformation("Курение", "", HabitPriority.High, HabitType.Bad, 8, 7, 0, "red"),
        )
        val result = habitsSortAndFilterUseCase.sortAndFilterHabits(sortData, filter, habitsList)
        Assertions.assertEquals(expected, result)
    }
}