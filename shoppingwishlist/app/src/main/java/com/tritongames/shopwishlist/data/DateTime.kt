package com.tritongames.shopwishlist.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*

open class DateTime {

    @RequiresApi(Build.VERSION_CODES.O)
    public fun getYear(): Int
    {
        return LocalDate.now().year
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun getMonth(): Int
    {
        return LocalDate.now().monthValue
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun getDayOfMonth(): Int
    {
        return LocalDate.now().dayOfMonth
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun expired(): Boolean
    {
        val calendarToday: Calendar =
            Calendar.Builder().setDate(getYear(), getMonth(), getDayOfMonth()).build()

        val calendarSixMonths: Calendar =
            Calendar.Builder().setDate(getYear(), getMonth() + 6, getDayOfMonth()).build()

        return calendarToday.after(calendarSixMonths)

    }



}