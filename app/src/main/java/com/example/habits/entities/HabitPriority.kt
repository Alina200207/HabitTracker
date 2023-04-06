package com.example.habits.entities

import androidx.annotation.StringRes
import com.example.habits.R

enum class HabitPriority(@StringRes val text: Int) {
    High(R.string.high_priority),
    Medium(R.string.medium_priority),
    Low(R.string.low_priority);
}