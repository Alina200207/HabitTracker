package com.example.habits

enum class HabitPriority {
    Low,
    Medium,
    High;


    companion object{
        fun toRus(habitPriority: HabitPriority): String {
            return when (habitPriority){
                Low -> "Низкий"
                Medium -> "Средний"
                High -> "Высокий"
            }
        }
        fun toEnum(priority: String): HabitPriority{
            return when (priority){
                "Низкий" -> Low
                "Средний" -> Medium
                "Высокий" -> High
                else -> Low
            }
        }
    }
}