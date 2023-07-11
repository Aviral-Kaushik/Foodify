package com.aviral.foodify.dbUtils

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConvertor {

    @TypeConverter
    fun fromAyToString(attribute: Any?): String {
        return if (attribute == null)
            ""
        else
            attribute as String
    }


    @TypeConverter
    fun fromStringToAny(attribute: String?): Any {
        return attribute ?: ""
    }

}