package com.example.habits.di

import com.example.data.database.di.DataModule
import com.example.habits.HabitsApplication
import com.example.habits.fragments.HabitAddendumFragment
import com.example.habits.viewmodels.HabitsListViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DataModule::class, ContextModule::class, CoroutineScopeModule::class, ViewModulesModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dataModule: DataModule,
            contextModule: ContextModule,
            coroutineScopeModule: CoroutineScopeModule
        ):
                ApplicationComponent
    }
    fun inject(app: HabitsApplication)
    fun inject(addendumFragment: HabitAddendumFragment)
    fun habitsListViewModelComponentFactory(): HabitsListViewModelComponent.Factory
}

