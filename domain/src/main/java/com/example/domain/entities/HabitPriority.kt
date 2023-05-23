package com.example.domain.entities

import androidx.annotation.StringRes
import com.example.domain.R

enum class HabitPriority(@StringRes val text: Int) {
    High(R.string.high_priority),
    Medium(R.string.medium_priority),
    Low(R.string.low_priority);
}