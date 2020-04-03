package com.zomato.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


object Converters {

    var gson = Gson()

    @TypeConverter
    @JvmStatic
    fun stringToList(data: String): List<String> {
        val listType: Type =
            object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(someObjects: List<String>): String {
        return gson.toJson(someObjects)
    }
}