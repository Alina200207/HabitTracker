package com.example.habits.di

import com.example.habits.fragments.BottomSheetFragment
import com.example.habits.fragments.HabitsListFragment
import dagger.Subcomponent


@Subcomponent(modules = [HabitsListViewModelModule::class])
interface HabitsListViewModelComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(module: HabitsListViewModelModule): HabitsListViewModelComponent
    }
    fun inject(habitsListFragment: HabitsListFragment)
    fun inject(bottomSheetFragment: BottomSheetFragment)
}

interface HabitsListViewModelComponentProvider{
    fun provideHabitsListViewModelComponent(): HabitsListViewModelComponent.Factory
}