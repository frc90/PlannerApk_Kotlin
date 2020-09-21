package com.frc90.plannerapk_kotlin.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.frc90.plannerapk_kotlin.R
import com.frc90.plannerapk_kotlin.model.CurrentMonth
import com.frc90.plannerapk_kotlin.networking.routes.Routes
import com.frc90.plannerapk_kotlin.networking.services.ApiService
import kotlinx.android.synthetic.main.activity_response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResponseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response)

        getAccessToken()

        btn_currentMonth.setOnClickListener { getCurrentMonth() }
    }

    /*
    * obtener valores del Preferences
    * */
    // mostrar los datos gurdados
    private fun getToken(){
        val sharePref = getPreferences(Context.MODE_PRIVATE)
        val accessToken = sharePref.getString("tokens","")
        tv_test.text = accessToken
//        et_password.setText(pass)
    }

    private fun saveData(){
        val sharePref = getPreferences(Context.MODE_PRIVATE)
        with(sharePref.edit()){
//            putString("userName", et_user.text.toString())
//            putString("userPass", et_password.text.toString())
            commit()
        }
    }

    // borrar los datos
    private fun deleteData(){
        val sharePref = getPreferences(Context.MODE_PRIVATE)
        with(sharePref.edit()){
            putString("userName", "")
            putString("userPass", "")
            commit()
        }
    }


    private fun getAccessToken():String{
        val access = intent.extras!!.getString("access")
        return "Bearer " + access.toString()
    }

    private fun getCurrentMonth(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Routes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val currentMonth = service.getCurrentMonth(getAccessToken())
        currentMonth.enqueue(object: Callback<CurrentMonth>{
            override fun onResponse(call: Call<CurrentMonth>, response: Response<CurrentMonth>) {
                var code = response.code()

                if (response.isSuccessful){
                    var activitiesMonth = response.body()!!
                    val results = activitiesMonth.results //staff todo

                    Log.i("TAG_LOG", "ALgo fallo!!! \n" + "CODE: $code")
                    tv_test.text = activitiesMonth.toString()
                }else{
                    Log.i("TAG_LOG", "ALgo fallo!!! \n" + "CODE: $code")
                }
            }

            override fun onFailure(call: Call<CurrentMonth>, t: Throwable) {
                Toast.makeText(this@ResponseActivity, "Algo fallo!!!", Toast.LENGTH_LONG).show()
                t?.printStackTrace()
                Log.i("TAG_LOG", "ALgo fallo!!!")
                call.cancel()
            }

        })
    }
}