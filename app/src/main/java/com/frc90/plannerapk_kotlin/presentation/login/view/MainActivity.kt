package com.frc90.plannerapk_kotlin.presentation.login.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.frc90.plannerapk_kotlin.R
import com.frc90.plannerapk_kotlin.base.BaseActivity
import com.frc90.plannerapk_kotlin.presentation.login.LoginContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), LoginContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        // splashscreen
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_singIn.setOnClickListener { signIn() }
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    // mostrar error
    override fun showError(msgError: String) {
        Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar() {
        progressBar_singIn.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar_singIn.visibility = View.GONE
    }

    override fun signIn() {
        showTextToast(this, "This is a test")

        //ejemplo
//        Toast.makeText(this, "Hello, world!!!", Toast.LENGTH_SHORT).show()

//        toast(this,"Hello, World!!!")
    }


}