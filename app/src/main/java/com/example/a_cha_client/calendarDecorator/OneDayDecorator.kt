package com.example.a_cha_client.calendarDecorator

import com.prolificinteractive.materialcalendarview.CalendarDay

import com.prolificinteractive.materialcalendarview.MaterialCalendarView

import android.graphics.Color

import android.text.style.ForegroundColorSpan

import android.text.style.RelativeSizeSpan

import android.graphics.Typeface

import android.text.style.StyleSpan

import com.prolificinteractive.materialcalendarview.DayViewFacade

import com.prolificinteractive.materialcalendarview.DayViewDecorator
import java.util.*


class OneDayDecorator : DayViewDecorator {
        private var date: CalendarDay?
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return date != null && day == date
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(StyleSpan(Typeface.BOLD))
            view.addSpan(RelativeSizeSpan(1.4f))
            view.addSpan(ForegroundColorSpan(Color.MAGENTA))
        }

        /**
         * We're changing the internals, so make sure to call [MaterialCalendarView.invalidateDecorators]
         */
        fun setDate(date: Date?) {
            this.date = CalendarDay.from(date)
        }

        init {
            date = CalendarDay.today()
        }
    }