package com.example.data.network

import com.google.gson.annotations.SerializedName


data class HabitDone(
    val date: Int,

    @SerializedName("habit_uid")
    val habitUid: String
)
