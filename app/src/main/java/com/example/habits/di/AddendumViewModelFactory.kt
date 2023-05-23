package com.example.habits.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habits.viewmodels.HabitAddendumViewModel
import dagger.assisted.AssistedFactory


@AssistedFactory
interface AddendumViewModelFactory {
    fun create(id: Long): HabitAddendumViewModel
}

fun provideFactory(assistedFactory: AddendumViewModelFactory, id: Long): ViewModelProvider.Factory{
    return object: ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(id) as T
        }
    }
}

