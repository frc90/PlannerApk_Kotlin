package com.frc90.plannerapk_kotlin.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.frc90.plannerapk_kotlin.R
import com.github.tibolte.agendacalendarview.AgendaCalendarView
import com.github.tibolte.agendacalendarview.CalendarPickerController
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent
import com.github.tibolte.agendacalendarview.models.CalendarEvent
import com.github.tibolte.agendacalendarview.models.DayItem
import java.util.*
import kotlin.collections.ArrayList


class MainDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        val minDate: Calendar = Calendar.getInstance()
        val maxDate: Calendar = Calendar.getInstance()

        minDate.add(Calendar.MONTH, -2)
        minDate.set(Calendar.DAY_OF_MONTH, 1)
        maxDate.add(Calendar.YEAR, 1)

        val eventList: ArrayList<CalendarEvent> = ArrayList()
        mockList(eventList);

        val agenda = findViewById<AgendaCalendarView>(R.id.agenda_calendar_view)

        agenda.init(
            eventList,
            minDate,
            maxDate,
            Locale.getDefault(),
            object : CalendarPickerController {
                override fun onDaySelected(dayItem: DayItem?) {
                }

                override fun onEventSelected(event: CalendarEvent?) {
                }

                override fun onScrollToDate(calendar: Calendar?) {
                }
            });
        return
    }

    // Add events to Calendar
    private fun mockList(eventList: ArrayList<CalendarEvent>) {
        val startTime1 = Calendar.getInstance()
        val endTime1 = Calendar.getInstance()
        endTime1.add(Calendar.MONTH, 1)
        val event1 = BaseCalendarEvent(
            "Thibault travels in Iceland", "A wonderful journey!", "Iceland",
            ContextCompat.getColor(this, R.color.colorAccent), startTime1, endTime1, true
        )
        eventList.add(event1)
        val startTime2 = Calendar.getInstance()
        startTime2.add(Calendar.DAY_OF_YEAR, 1)
        val endTime2 = Calendar.getInstance()
        endTime2.add(Calendar.DAY_OF_YEAR, 3)
        val event2 = BaseCalendarEvent(
            "Visit to Dalvík",
            "A beautiful small town",
            "Dalvík",
            ContextCompat.getColor(this, R.color.blue_selected),
            startTime2,
            endTime2,
            true
        )
        eventList.add(event2)

        // Example on how to provide your own layout
        val startTime3 = Calendar.getInstance()
        val endTime3 = Calendar.getInstance()
        startTime3[Calendar.HOUR_OF_DAY] = 14
        startTime3[Calendar.MINUTE] = 0
        endTime3[Calendar.HOUR_OF_DAY] = 15
        endTime3[Calendar.MINUTE] = 0
    }
}