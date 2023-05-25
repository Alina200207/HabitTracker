package com.example.data.di

import android.content.Context
import com.example.data.database.HabitsDatabase
import com.example.data.database.HabitsDatabaseRepository
import com.example.data.network.*
import com.example.domain.constants.Constants
import com.example.domain.entities.HabitInformation
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class DataModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideServerRepository(
        habitsApiService: HabitsApiService,
        habitsDatabaseRepository: HabitsDatabaseRepository
    ): HabitsServerRepository {
        return HabitsServerRepository(habitsApiService, habitsDatabaseRepository)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): HabitsApiService =
        retrofit.create(HabitsApiService::class.java)

    @Singleton
    @Provides
    fun provideDatabaseRepository(habitsDatabase: HabitsDatabase): HabitsDatabaseRepository {
        return HabitsDatabaseRepository(habitsDatabase.habitsDao())
    }

    @Singleton
    @Provides
    fun provideDatabase(): HabitsDatabase {
        return HabitsDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(RetryInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun gsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
        gsonBuilder
            .registerTypeAdapter(HabitInformation::class.java, HabitsJsonSerializer())
            .registerTypeAdapter(HabitInformation::class.java, HabitsJsonDeserializer())
        return GsonConverterFactory.create(gsonBuilder.create())
    }

}