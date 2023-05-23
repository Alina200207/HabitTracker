package com.example.habits.di

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.database.database.HabitsDatabaseRepository
import com.example.habits.MainActivity
import com.example.habits.fragments.HabitsListFragment
import com.example.habits.viewmodels.HabitsListViewModel
import dagger.Module
import dagger.Provides


@Module
class HabitsListViewModelModule(private val activity: MainActivity) {

    @Provides
    fun provideHabitsListFragment() = activity

    @Provides
    fun provideFactory(repository: HabitsDatabaseRepository): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(HabitsDatabaseRepository::class.java).newInstance(repository)
            }
        }
    }

    @Provides
    fun provideHabitsListViewModel(factory: ViewModelProvider.Factory, activity: MainActivity)
    = ViewModelProvider(activity, factory)[HabitsListViewModel::class.java]
}