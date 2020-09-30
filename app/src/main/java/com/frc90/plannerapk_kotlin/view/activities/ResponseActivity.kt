package com.frc90.plannerapk_kotlin.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.frc90.plannerapk_kotlin.R
import com.frc90.plannerapk_kotlin.adapter.ResultsAdapter
import kotlinx.android.synthetic.main.activity_response.*

class ResponseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response)

        rv_response_activity.layoutManager = LinearLayoutManager(this)
        rv_response_activity.adapter = ResultsAdapter()

//        getCurrentMonth()
    }


    private fun getAccessToken(): String {
        val access = intent.extras!!.getString("access")
        return "Bearer " + access.toString()
    }

//    private fun getCurrentMonth() {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(Routes.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val service = retrofit.create(ApiService::class.java)
//        val resultsActivities = service.getCurrentMonth(getAccessToken())
//        resultsActivities.enqueue(object : Callback<ArrayList<Result>> {
//            override fun onResponse(call: Call<ArrayList<Result>>, response: Response<ArrayList<Result>>) {
//                var code = response.code()
//
//                if (response.isSuccessful) {
//                    var activitiesMonth = response.body()!!
////                    val results = activitiesMonth.results //staff todo
//
////                    (rv_response_activity.adapter as ResultsAdapter).setListOfResult(results)
//                } else {
//                    Log.i("TAG_LOG", "ALgo fallo!!! \n" + "CODE: $code")
//                }
//            }
//
//            override fun onFailure(call: Call<ArrayList<Result>>, t: Throwable) {
//                Toast.makeText(this@ResponseActivity, "Algo fallo!!!", Toast.LENGTH_LONG).show()
//                t?.printStackTrace()
//                Log.i("TAG_LOG", "ALgo fallo!!!")
//                call.cancel()
//            }
//
//        })
//    }
}