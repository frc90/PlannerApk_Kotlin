package com.frc90.plannerapk_kotlin.view.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.frc90.plannerapk_kotlin.R
import com.frc90.plannerapk_kotlin.model.Result
import com.frc90.plannerapk_kotlin.networking.routes.Routes
import com.frc90.plannerapk_kotlin.networking.services.ApiService
import com.github.tibolte.agendacalendarview.AgendaCalendarView
import com.github.tibolte.agendacalendarview.CalendarPickerController
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent
import com.github.tibolte.agendacalendarview.models.CalendarEvent
import com.github.tibolte.agendacalendarview.models.DayItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


// lista de los eventos del calendario
val eventList: ArrayList<CalendarEvent> = ArrayList()

class MainDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        getCurrentMonth()

    }
    // pasar los valores a la lista de eventos para mostrar en el layout
    fun showEvents(eventList: ArrayList<CalendarEvent>) {
        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        val minDate: Calendar = Calendar.getInstance()
        val maxDate: Calendar = Calendar.getInstance()

        minDate.add(Calendar.MONTH, -1)
        minDate.set(Calendar.DAY_OF_MONTH, 1)
        maxDate.add(Calendar.YEAR, 1)

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


    // obtener el token de acceso
    private fun getAccessToken(): String {
        val access = intent.extras!!.getString("access")
        return "Bearer " + access.toString()
    }

    // obtener las actividades del mes
    private fun getCurrentMonth() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Routes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val resultsActivities = service.getCurrentMonth(getAccessToken())
        resultsActivities.enqueue(object : Callback<ArrayList<Result>> {
            override fun onResponse(call: Call<ArrayList<Result>>, response: Response<ArrayList<Result>>) {
                var code = response.code()
                if (response.isSuccessful) {
                    var activitiesMonth = response.body()!!
                    val results = activitiesMonth //staff todo

                    // val results -> con las actividades del mes
                    results.forEach {

                        val startTime2 = Calendar.getInstance()
                        startTime2.add(Calendar.DAY_OF_YEAR, Math.random().toInt())
                        val endTime2 = Calendar.getInstance()
                        endTime2.add(Calendar.DAY_OF_YEAR, Math.random().toInt())

                        val starDate = getMonth(it.startDate)

                        val endDate = getMonth(it.endDate)

                        val name = it.name
                        val location = if (it.location != null) it.location.name else ""
                        val allDay = it.allDay
                        val observation = it.observation

                        val event = BaseCalendarEvent(
                            name,
                            observation,
                            location,
                            ContextCompat.getColor(this@MainDashboard, R.color.blue_selected),
                            starDate,
                            endDate,
                            allDay
                        )
                        eventList.add(event)
                    }
                    showEvents(eventList)
                } else {
                    Log.i("TAG_LOG", "ALgo fallo!!! \n" + "CODE: $code")
                }
            }

            override fun onFailure(call: Call<ArrayList<Result>>, t: Throwable) {
                Toast.makeText(this@MainDashboard, "Algo fallo!!!", Toast.LENGTH_LONG).show()
                t?.printStackTrace()
                Log.i("TAG_LOG", "ALgo fallo!!!")
                call.cancel()
            }
        })
    }


    private fun getMonth(date: String): Calendar {
        val calendar = Calendar.getInstance()
        // convert String to date
        val day = "${date[8]}${date[9]}".toInt()
        val month = "${date[5]}${date[6]}".toInt()
        val year = "${date[0]}${date[1]}${date[2]}${date[3]}".toInt()
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.YEAR, year)
        return calendar
    }
}