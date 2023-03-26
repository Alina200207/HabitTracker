package com.example.habits

import androidx.annotation.StringRes

enum class HabitType(@StringRes val text: Int) {
    Good(R.string.good_habit), Bad(R.string.bad_habit);

    companion object {

        fun toEnum(type: String): HabitType {
            return when (type) {
                Constants.goodTypeText -> Good
                Constants.badTypeText -> Bad
                else -> Good
            }
        }
    }
}