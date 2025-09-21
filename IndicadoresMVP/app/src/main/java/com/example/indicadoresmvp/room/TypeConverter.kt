package com.example.indicadoresmvp.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SerieListConverter {
    @TypeConverter
    fun fromString(value: String?): List<Indicador.Serie>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<Indicador.Serie>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Indicador.Serie>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(list)
    }
}