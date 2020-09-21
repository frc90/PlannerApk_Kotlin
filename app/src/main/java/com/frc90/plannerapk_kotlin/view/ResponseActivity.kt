package com.frc90.plannerapk_kotlin.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frc90.plannerapk_kotlin.R
import kotlinx.android.synthetic.main.activity_response.*

class ResponseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response)

        getAccessToken()
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


    private fun getAccessToken(){
        val string = intent.extras!!.getString("access")
        tv_test.text = string
    }
}