package com.example.habits.network

import com.example.habits.constants.Constants
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.HabitPriority
import com.example.habits.entities.HabitType
import com.example.habits.entities.ServerSynchronization
import com.google.gson.*
import org.json.JSONArray
import java.lang.reflect.Type
import java.sql.Timestamp
import java.util.Date
import java.util.UUID

class HabitsJsonSerializer: JsonSerializer<HabitInformation> {
    override fun serialize(
        src: HabitInformation?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val result = JsonObject()
        val dates = Gson().toJsonTree(listOf(0))
        result.addProperty("color", src?.habitColor)
        result.addProperty("count", src?.habitNumberExecution)
        result.addProperty("date", (System.currentTimeMillis()/1000).toInt())
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

class HabitsJsonDeserializer: JsonDeserializer<HabitInformation> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): HabitInformation {
        val jsonObject = json?.asJsonObject
        val color = jsonObject?.get("color")?.asInt ?: Constants.green
        return HabitInformation(0,
            jsonObject?.get("title")?.asString ?: "",
            jsonObject?.get("description")?.asString ?: "",
            HabitPriority.values()[jsonObject?.get("priority")?.asInt ?: 0],
            HabitType.values()[jsonObject?.get("type")?.asInt ?: 0],
            jsonObject?.get("count")?.asInt ?: 0,
            jsonObject?.get("frequency")?.asInt ?: 0,
            color,
            Constants.colors[color] ?: Constants.strGreen,
            ServerSynchronization.SynchronizedChange,
            jsonObject?.get("uid")?.asString ?: UUID.randomUUID().toString()
        )
    }
}