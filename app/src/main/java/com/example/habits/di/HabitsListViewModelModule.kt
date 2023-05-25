package com.example.habits.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.database.HabitsDatabaseRepository
import com.example.habits.MainActivity
import com.example.habits.viewmodels.HabitsListViewModel
import dagger.Module
import dagger.Provides


@Module
class HabitsListViewModelModule(private val activity: MainActivity) {

    @Provides
    fun provideActivity() = activity

    @Provides
    fun provideFactory(repository: HabitsDatabaseRepository): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(HabitsDatabaseRepository::class.java)
                    .newInstance(repository)
            }
        }
    }

    @Provides
    fun provideHabitsListViewModel(factory: ViewModelProvider.Factory, activity: MainActivity) =
        ViewModelProvider(activity, factory)[HabitsListViewModel::class.java]
}