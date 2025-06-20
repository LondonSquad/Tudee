package com.london.tudee.data.local.room_db.converter

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.TaskStatus

class Converters {
    @TypeConverter
    fun fromPriority(value: Priority): String = value.name

    @TypeConverter
    fun toPriority(value: String): Priority = Priority.valueOf(value)

    @TypeConverter
    fun fromStatus(value: TaskStatus): String = value.name

    @TypeConverter
    fun toStatus(value: String): TaskStatus = TaskStatus.valueOf(value)

    @TypeConverter
    fun fromColor(color: Color): Int {
        return color.value.toLong().toInt()
    }

    @TypeConverter
    fun toColor(colorInt: Int): Color {
        return Color(colorInt)
    }
}