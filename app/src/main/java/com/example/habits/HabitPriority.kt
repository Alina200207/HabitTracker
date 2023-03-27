package com.example.habits

import androidx.annotation.StringRes

enum class HabitPriority(@StringRes val text: Int) {

    Low(R.string.low_priority),
    Medium(R.string.medium_priority),
    High(R.string.high_priority);


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