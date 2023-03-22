package com.example.habits

enum class HabitType {
    Good, Bad;

    companion object{
        fun toRus(habitType: HabitType): String {
            return when (habitType){
                Good -> "Хорошая"
                Bad -> "Плохая"
            }
        }
        fun toEnum(type: String): HabitType{
            return when (type){
                "Хорошая" -> Good
                "Плохая" -> Bad
                else -> Good
            }
        }
    }
}