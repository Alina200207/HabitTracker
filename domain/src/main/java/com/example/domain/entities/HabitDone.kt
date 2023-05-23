package com.example.domain.entities


data class HabitDone(
    val date: Int,

    //сериализация другое имя в модуле data
    val uid: String
)
