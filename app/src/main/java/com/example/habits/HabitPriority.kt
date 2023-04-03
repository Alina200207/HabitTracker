package com.example.habits

import androidx.annotation.StringRes

enum class HabitPriority(@StringRes val text: Int) {

    High(R.string.high_priority),
    Medium(R.string.medium_priority),
    Low(R.string.low_priority);


    companion object {
        fun toEnum(priority: String): HabitPriority {
            return when (priority) {
                Constants.lowPriorityText -> Low
                Constants.mediumPriorityText -> Medium
                Constants.highPriorityText -> High
                else -> Low
            }
        }
    }
}