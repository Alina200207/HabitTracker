package com.example.habits.entities

import com.google.gson.annotations.SerializedName

data class HabitDone(
    val date: Int,

    @SerializedName("habit_uid")
    val uid: String
)
