package com.example.data.network

import android.util.Log
import com.example.domain.constants.Constants
import com.example.domain.entities.HabitInformation
import com.example.domain.entities.HabitPriority
import com.example.domain.entities.HabitType
import com.example.domain.entities.ServerSynchronization
import com.google.gson.*
import java.lang.reflect.Type
import java.util.UUID

class HabitsJsonSerializer : JsonSerializer<HabitInformation> {
    override fun serialize(
        src: HabitInformation?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val result = JsonObject()
        val dates = Gson().toJsonTree(src?.doneDates)
        Log.i("okdgd", src?.doneDates.toString())
        result.addProperty("color", src?.habitColor)
        result.addProperty("count", src?.habitNumberExecution)
        result.addProperty("date", (System.currentTimeMillis() / 1000).toInt())
        result.addProperty("description", src?.habitDescription)
        result.add("done_dates", dates)
        result.addProperty("frequency", src?.frequency)
        result.addProperty("priority", HabitPriority.values().indexOf(src?.habitPriority))
        result.addProperty("title", src?.habitTitle)
        result.addProperty("type", HabitType.values().indexOf(src?.habitType))
        if (src?.uid != "0")
            result.addProperty("uid", src?.uid)
        return result
    }
}

class HabitsJsonDeserializer : JsonDeserializer<HabitInformation> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): HabitInformation {
        val jsonObject = json?.asJsonObject
        val color = jsonObject?.get("color")?.asInt ?: Constants.green
        return HabitInformation(
            0,
            jsonObject?.get("title")?.asString ?: "",
            jsonObject?.get("description")?.asString ?: "",
            HabitPriority.values()[jsonObject?.get("priority")?.asInt ?: 0],
            HabitType.values()[jsonObject?.get("type")?.asInt ?: 0],
            jsonObject?.get("count")?.asInt ?: 0,
            jsonObject?.get("frequency")?.asInt ?: 0,
            color,
            Constants.colors[color] ?: Constants.strGreen,
            ServerSynchronization.SynchronizedChange,
            jsonObject?.get("uid")?.asString ?: UUID.randomUUID().toString(),
            Gson().fromJson(jsonObject?.get("done_dates"), Array<Int>::class.java).toList()
        )
    }
}