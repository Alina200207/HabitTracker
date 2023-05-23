package com.example.habits

import android.app.Application
import com.example.data.database.di.DataModule
import com.example.domain.constants.Constants
import com.example.data.database.network.*
import com.example.domain.entities.HabitInformation
import com.example.habits.di.*
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class HabitsApplication : Application(), HabitsListViewModelComponentProvider {
//    private val database by lazy {
//        HabitsDatabase.getDatabase(this)
//    }
//    val repository by lazy {
//        HabitsDatabaseRepository(database.habitsDao())
//    }
//    val serverRepository by lazy {
//        HabitsServerRepository(createRetrofit(), repository)
//    }
    @Inject
    lateinit var serverRepository: HabitsServerRepository
    lateinit var appComponent: ApplicationComponent
    private val appScope = CoroutineScope(SupervisorJob())


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(DataModule(this), ContextModule(this), CoroutineScopeModule(appScope))
        appComponent.inject(this)
        appScope.launch {
            serverRepository.updateHabitsList()
        }
    }

    override fun provideHabitsListViewModelComponent(): HabitsListViewModelComponent.Factory {
        return appComponent.habitsListViewModelComponentFactory()
    }

//    private fun createRetrofit(): HabitsApiService {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor(RetryInterceptor())
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(Constants.baseUrl)
//            .client(okHttpClient)
//            .addConverterFactory(gsonConverterFactory())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        return retrofit.create(HabitsApiService::class.java)
//    }
//
//    private fun gsonConverterFactory(): GsonConverterFactory{
//        val gsonBuilder = GsonBuilder()
//        gsonBuilder
//            .registerTypeAdapter(HabitInformation::class.java, HabitsJsonSerializer())
//            .registerTypeAdapter(HabitInformation::class.java, HabitsJsonDeserializer())
//        return GsonConverterFactory.create(gsonBuilder.create())
//    }
}