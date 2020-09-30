package com.frc90.plannerapk_kotlin.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.frc90.plannerapk_kotlin.R
import com.frc90.plannerapk_kotlin.base.BaseActivity
import com.frc90.plannerapk_kotlin.model.UserData
import com.frc90.plannerapk_kotlin.presentation.LoginContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), LoginContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        // splashscreen
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)


        // hacer el

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // mostrar si existen las credenciales
        showData()

        btn_login.setOnClickListener {
            val username: String = input_full_name.text.toString()
            val password: String = input_pass.text.toString()
            if (login(username, password)){
                // guardar las credenciales
                if (cb_remember_me.isChecked){
                    saveData()
                } else{
                    deleteData()
                }
                val userData = UserData(username, password)
                createToken(userData)
            }else{
                showTextToast(this, "Por favor verifique su usuario y contraseña")
            }
        }

        // esto es necesario
        btn_dash_boar.setOnClickListener {
            val username: String = input_full_name.text.toString()
            val password: String = input_pass.text.toString()
            if (login(username, password)){
                // guardar las credenciales
                if (cb_remember_me.isChecked){
                    saveData()
                } else{
                    deleteData()
                }
                val userData = UserData(username, password)
                createToken(userData)
            }else{
                showTextToast(this, "Por favor verifique su usuario y contraseña")
            }
//            var intent = Intent(this, MainDashboard::class.java)
//            startActivity(intent)
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
}