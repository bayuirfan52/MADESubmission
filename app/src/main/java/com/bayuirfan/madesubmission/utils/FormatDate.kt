package com.bayuirfan.madesubmission.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object FormatDate {
    @Suppress("SameParameterValue")
    private fun formatDate(date: String, format: String): String{
        var result = ""
        val oldDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        oldDate.timeZone = TimeZone.getTimeZone("UTC")
        try {
            val old = oldDate.parse(date)
            val newDate = SimpleDateFormat(format, Locale.getDefault())
            result = newDate.format(old)
        }catch (e: ParseException){
            Log.e(FormatDate.javaClass.simpleName, e.localizedMessage)
        }
        return result
    }

    fun reformatDate(date: String): String{
        return formatDate(date, "dd MMM yyyy")
    }
}