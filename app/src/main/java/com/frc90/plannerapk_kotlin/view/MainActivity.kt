package com.frc90.plannerapk_kotlin.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.frc90.plannerapk_kotlin.R
import com.frc90.plannerapk_kotlin.base.BaseActivity
import com.frc90.plannerapk_kotlin.model.Token
import com.frc90.plannerapk_kotlin.model.UserData
import com.frc90.plannerapk_kotlin.networking.generator.RetrofitClient
import com.frc90.plannerapk_kotlin.networking.routes.Routes
import com.frc90.plannerapk_kotlin.networking.services.ApiService
import com.frc90.plannerapk_kotlin.presentation.LoginContract
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : BaseActivity(), LoginContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        // splashscreen
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener { signIn() }

        val username: String = input_full_name.text.toString()
        val password: String = input_pass.text.toString()

        btn_login.setOnClickListener {
            if (login(username, password)){
                var userData = UserData(username, password)
                createToken(userData)
            }else{
                showTextToast(this, "Por favor verifique su usuario y contraseña")
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    // mostrar error
    override fun showError(msgError: String) {
        Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar() {
//        progressBar_singIn.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
//        progressBar_singIn.visibility = View.GONE
    }

    override fun signIn() {
        showTextToast(this, "This is a test")
    }

    /*
    * Preferences
    * */

    // guardar los token de acceso
    private fun saveTokenPref(tokens: String) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("tokens", tokens)
            commit()
        }
    }
    /*end Preferences*/

    fun createToken(userData: UserData) {
        showProgressBar()

        val retrofit = Retrofit.Builder()
            .baseUrl(Routes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val newToken = service.getAccessToken(userData)

        newToken.enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                val code = response?.code()
                if (response.isSuccessful) {
                    val tokens = response.body()!!

                    saveTokenPref(tokens.access)
                    var intent = Intent(this@MainActivity, ResponseActivity::class.java)
                    intent.putExtra("access", tokens.access)
                    startActivity(intent)

//                    Log.i("TAG_LOG", Gson().toJson(tokens))
//                    Log.i("TAG_LOG", "CODE: " + code)
                } else {
//                    Log.i("TAG_LOG", "CODE: " + code)
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Asegurece de estar conectado a internet", Toast.LENGTH_LONG).show()
                t?.printStackTrace()
                call.cancel()
            }
        })

    }
}