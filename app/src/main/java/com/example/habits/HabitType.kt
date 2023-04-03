package com.example.habits

import androidx.annotation.StringRes

enum class HabitType(@StringRes val text: Int, @StringRes val enum_text: Int) {
    Good(R.string.good_habit, R.string.good_enum), Bad(R.string.bad_habit, R.string.bad_enum);

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