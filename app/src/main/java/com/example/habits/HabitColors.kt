package com.example.habits

import androidx.annotation.StringRes

enum class HabitColors(
    @StringRes
    val text: Int
) {
    Red(R.string.red_color_text), Green(R.string.green_color_text);
}