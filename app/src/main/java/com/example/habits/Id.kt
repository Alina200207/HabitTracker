package com.example.habits

object Id {
    private var id: Int = 0
    private fun increaseId() {
        id += 1
    }

    fun getId(): Int {
        increaseId()
        return id
    }
}