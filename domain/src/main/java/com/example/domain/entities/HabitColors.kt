package com.example.domain.entities

import androidx.annotation.StringRes
import com.example.domain.R

enum class HabitColors(
    @StringRes
    val text: Int
) {
    Red(R.string.red_color_text), Green(R.string.green_color_text);
}