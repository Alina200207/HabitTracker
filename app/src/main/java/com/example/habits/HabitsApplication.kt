package com.example.habits

import android.app.Application
import com.example.data.database.HabitsDatabase
import com.example.data.database.HabitsDatabaseRepository
import com.example.data.di.DataModule
import com.example.data.network.HabitsServerRepository
import com.example.habits.di.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


class HabitsApplication : Application(), HabitsListViewModelComponentProvider {
    @Inject
    lateinit var serverRepository: HabitsServerRepository

    lateinit var appComponent: ApplicationComponent
    private val appScope = CoroutineScope(SupervisorJob())


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory()
            .create(DataModule(this), ContextModule(this), CoroutineScopeModule(appScope))
        appComponent.inject(this)
        appScope.launch {
            serverRepository.updateHabitsList()
        }
    }

    override fun provideHabitsListViewModelComponent(): HabitsListViewModelComponent.Factory {
        return appComponent.habitsListViewModelComponentFactory()
    }
}