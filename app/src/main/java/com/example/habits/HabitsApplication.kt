package com.example.habits

import android.app.Application
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
            .create(DataModule(), ContextModule(this), CoroutineScopeModule(appScope))
        appComponent.inject(this)
        appScope.launch {
            serverRepository.synchronizeDatabaseAndServer()
        }
    }

    override fun provideHabitsListViewModelComponent(): HabitsListViewModelComponent.Factory {
        return appComponent.habitsListViewModelComponentFactory()
    }
}