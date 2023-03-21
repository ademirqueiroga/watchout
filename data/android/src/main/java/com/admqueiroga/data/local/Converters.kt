package com.admqueiroga.data.local

import androidx.room.TypeConverter

internal class Converters {
    @TypeConverter
    fun intListToString(numbers: List<Int>): String = numbers.joinToString(",") { it.toString().trim() }

    @TypeConverter
    fun stringToIntList(numbers: String): List<Int> = numbers.split(",").map { it.trim().toInt() }
}