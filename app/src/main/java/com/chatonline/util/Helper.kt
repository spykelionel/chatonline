package com.chatonline.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Helper {
    fun toTitleCase(input: String): String {
        val titleCase = StringBuilder(input.length)
        var toUpper: String = input[0].toString().uppercase(Locale.ROOT)
        titleCase.append(toUpper)
        for (i in 1 until input.length) {
            titleCase.append(input[i])
        }
        return titleCase.toString()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun toLocalDate(date: String, context: Int = 2): String {
        val longDate = date.split('+')[0]
        val date1 = longDate.split('T')[0]
        val time1 = longDate.split('T')[1].split('.')[0]
        val time = "${time1.split(':')[0]}:${time1.split(':')[1]}"
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date = LocalDate.parse("$date1 $time1", dateFormatter)
        val day = date.dayOfWeek.name.lowercase(Locale.ROOT)
        return when(context) {
            0 -> time
            1 -> day
            2 -> "${toTitleCase(day)} $date $time"
            else -> ""
        }


    }
}