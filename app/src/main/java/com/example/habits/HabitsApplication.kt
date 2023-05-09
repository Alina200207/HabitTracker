package com.example.habits

import android.app.Application
import com.example.habits.constants.Constants
import com.example.habits.database.HabitsDatabase
import com.example.habits.database.HabitsDatabaseRepository
import com.example.habits.entities.HabitInformation
import com.example.habits.network.*
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HabitsApplication : Application() {
    private val database by lazy {
        HabitsDatabase.getDatabase(this)
    }
    val repository by lazy {
        HabitsDatabaseRepository(database.habitsDao())
    }
    val serverRepository by lazy {
        HabitsServerRepository(createRetrofit(), repository)
    }
    val appScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        appScope.launch {
            serverRepository.updateHabitsList()
        }
    }

    private fun createRetrofit(): HabitsApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(RetryInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(HabitsApiService::class.java)
    }

    private fun gsonConverterFactory(): GsonConverterFactory{
        val gsonBuilder = GsonBuilder()
        gsonBuilder
            .registerTypeAdapter(HabitInformation::class.java, HabitsJsonSerializer())
            .registerTypeAdapter(HabitInformation::class.java, HabitsJsonDeserializer())
        return GsonConverterFactory.create(gsonBuilder.create())
    }
}