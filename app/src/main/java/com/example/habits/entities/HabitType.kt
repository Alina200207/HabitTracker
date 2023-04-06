package com.example.habits.entities

import androidx.annotation.StringRes
import com.example.habits.R

enum class HabitType(@StringRes val text: Int, @StringRes val enum_text: Int) {
    Good(R.string.good_habit, R.string.good_enum), Bad(R.string.bad_habit, R.string.bad_enum);
}