package com.example.data.database.database

import com.example.domain.entities.HabitInformation
import com.example.domain.entities.HabitType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HabitsDatabaseRepository @Inject constructor(private val habitsDao: HabitsDao) {

    val goodHabits = habitsDao.getHabitsByType(HabitType.Good).map { habits ->
        habits.map { habit -> ConvertEntities.fromHabitInformationEntityToHabitInformation(habit) }
    }
    val badHabits = habitsDao.getHabitsByType(HabitType.Bad).map { habits ->
        habits.map { habit -> ConvertEntities.fromHabitInformationEntityToHabitInformation(habit) }
    }

    suspend fun insertAll(vararg habits: HabitInformation) {
        return withContext(Dispatchers.IO) {
            for (habit in habits) {
                habitsDao.insert(ConvertEntities.fromHabitInformationToHabitInformationEntity(habit))
            }
        }
    }

    suspend fun insert(habit: HabitInformation): Long {
        return withContext(Dispatchers.IO) {
            habitsDao.insert(ConvertEntities.fromHabitInformationToHabitInformationEntity(habit))
        }
    }

    suspend fun update(habit: HabitInformation) {
        withContext(Dispatchers.IO) {
            habitsDao.updateHabits(
                ConvertEntities.fromHabitInformationToHabitInformationEntity(
                    habit
                )
            )
        }
    }

    suspend fun getAllHabits(): List<HabitInformation> {
        return withContext(Dispatchers.IO) {
            habitsDao.getHabits()
                .map { habit -> ConvertEntities.fromHabitInformationEntityToHabitInformation(habit) }
        }
    }

    suspend fun deleteAll() {
        return withContext(Dispatchers.IO) {
            habitsDao.deleteAll()
        }
    }

    suspend fun delete(habit: HabitInformation) {
        withContext(Dispatchers.IO) {
            habitsDao.delete(ConvertEntities.fromHabitInformationToHabitInformationEntity(habit))
        }
    }

    suspend fun getHabitById(id: Long): Flow<HabitInformation> {
        return withContext(Dispatchers.IO) {
            flowOf(
                ConvertEntities.fromHabitInformationEntityToHabitInformation(
                    habitsDao.getHabit(id)
                )
            )
        }
    }

    fun getHabit(id: Long): HabitInformation {
        return ConvertEntities.fromHabitInformationEntityToHabitInformation(habitsDao.getHabit(id))
    }
}