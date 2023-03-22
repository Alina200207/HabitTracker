package com.example.habits

object Additions {
    fun defineWordEnding(count: Int): String{
        return if (listOf(2, 3, 4).contains(count % 10) && count / 10 != 1){
            "а"
        } else ""
    }
}