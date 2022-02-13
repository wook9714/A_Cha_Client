package com.example.a_cha_client.calendarDecorator

import android.graphics.Color
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

class SundayDecorator   : DayViewDecorator {
    private val calendar: Calendar = Calendar.getInstance()
    override fun shouldDecorate(day: CalendarDay): Boolean {
        day.copyTo(calendar)
        val weekDay: Int = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(Color.GRAY))
        view.setDaysDisabled(true)
        view.addSpan(RelativeSizeSpan(0.9f))
        view.addSpan(StyleSpan(Typeface.ITALIC))
    }
}