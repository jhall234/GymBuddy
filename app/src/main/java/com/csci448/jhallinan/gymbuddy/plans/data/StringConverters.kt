package com.csci448.jhallinan.gymbuddy.plans.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringConverters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun jsonFromString(value: ArrayList<String>): String {
            return Gson().toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun stringFromJson(value: String): ArrayList<String> {
            val type = object : TypeToken<ArrayList<String>>() {}.type
            return Gson().fromJson<ArrayList<String>>(value, type)
        }
    }
}