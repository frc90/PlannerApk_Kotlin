package com.frc90.plannerapk_kotlin.view.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.frc90.plannerapk_kotlin.R
import com.frc90.plannerapk_kotlin.adapter.ResultsAdapter
import com.frc90.plannerapk_kotlin.model.CurrentMonth
import com.frc90.plannerapk_kotlin.networking.routes.Routes
import com.frc90.plannerapk_kotlin.networking.services.ApiService
import com.github.tibolte.agendacalendarview.AgendaCalendarView
import com.github.tibolte.agendacalendarview.CalendarPickerController
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent
import com.github.tibolte.agendacalendarview.models.CalendarEvent
import com.github.tibolte.agendacalendarview.models.DayItem
import kotlinx.android.synthetic.main.activity_response.*
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
    fun showEvents(eventList: ArrayList<CalendarEvent>){
        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        val minDate: Calendar = Calendar.getInstance()
        val maxDate: Calendar = Calendar.getInstance()

        minDate.add(Calendar.MONTH, -2)
        minDate.set(Calendar.DAY_OF_MONTH, 1)
        maxDate.add(Calendar.YEAR, 1)

        // lista de los eventos

//        mockList(eventList);

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

    // ejemplo para adicionar evento alcalendario
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

    /*-------------------------------------------------------------------
    * arreglar si o si
    * -------------------------------------------------------------------*/

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
        resultsActivities.enqueue(object : Callback<CurrentMonth> {
            override fun onResponse(call: Call<CurrentMonth>, response: Response<CurrentMonth>) {
                var code = response.code()
                if (response.isSuccessful) {
                    var activitiesMonth = response.body()!!
                    val results = activitiesMonth.results //staff todo
                    // val results -> con las actividades del mes
                    results.forEach {

                        val startTime2 = Calendar.getInstance()
                        startTime2.add(Calendar.DAY_OF_YEAR, Math.random().toInt())
                        val endTime2 = Calendar.getInstance()
                        endTime2.add(Calendar.DAY_OF_YEAR, Math.random().toInt())
                        val event = BaseCalendarEvent(
                            it.name,
                            "A beautiful small town",
                            it.location.name,
                            ContextCompat.getColor(this@MainDashboard, R.color.blue_selected),
                            startTime2,
                            endTime2,
                            it.allDay
                        )
                        eventList.add(event)
//                        it.startDate
//                        it.endDate
                    }

                    showEvents(eventList)


                } else {
                    Log.i("TAG_LOG", "ALgo fallo!!! \n" + "CODE: $code")
                }
            }

            override fun onFailure(call: Call<CurrentMonth>, t: Throwable) {
                Toast.makeText(this@MainDashboard, "Algo fallo!!!", Toast.LENGTH_LONG).show()
                t?.printStackTrace()
                Log.i("TAG_LOG", "ALgo fallo!!!")
                call.cancel()
            }

        })
    }
}