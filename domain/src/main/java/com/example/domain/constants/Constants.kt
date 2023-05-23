package com.example.domain.constants

import android.graphics.Color

object Constants {
    const val toastAllFieldsText = "Заполните все поля"
    const val habitTypesCount = 2
    const val database = "habitsEntitiesDatabase"
    const val responseTimeRetry = 15000L
    const val error = "okError"
    const val headerAuthorization = "Authorization"
    const val baseUrl = "https://droid-test-server.doubletapp.ru/api/"
    const val imageUrl = "https://fikiwiki.com/uploads/posts/2022-02/thumbs/1645054738_13-fikiwiki-com-p-kartinki-ikonki-13.png"
    const val authorization = ""
    private val red = Color.rgb(255, 0, 0)
    val green = Color.rgb(0, 255, 0)
    private const val strRed = "Красный"
    const val strGreen = "Зеленый"
    val colors = mapOf(
        green to strGreen,
        red to strRed
    )
}