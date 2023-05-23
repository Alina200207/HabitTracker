package com.example.data.database.network

import com.example.domain.entities.HabitDone
import com.example.domain.entities.HabitInformation
import com.example.domain.entities.Uid
import retrofit2.Response
import retrofit2.http.*


interface HabitsApiService{
    @GET("habit")
    suspend fun getHabits(): Response<Array<HabitInformation>>

    @HTTP(method = "PUT", path = "habit", hasBody = true)
    suspend fun putHabit(@Body habit: HabitInformation): Response<Uid>

    @POST("habit_done")
    suspend fun postHabit(@Body habitDone: HabitDone): Response<Uid>

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Body uid: Uid): Response<Unit>
}