package com.frc90.plannerapk_kotlin.view.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.kaopiz.kprogresshud.KProgressHUD
import android.util.Log
import android.widget.Toast
import com.frc90.plannerapk_kotlin.R
import com.frc90.plannerapk_kotlin.base.BaseActivity
import com.frc90.plannerapk_kotlin.base.Pref
import com.frc90.plannerapk_kotlin.model.UserData
import com.frc90.plannerapk_kotlin.presentation.LoginContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), LoginContract.View {
    private lateinit var prefs: Pref
    private lateinit var pDialog: KProgressHUD


    override fun onCreate(savedInstanceState: Bundle?) {
        // splashscreen
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // inicio el prefs
        prefs = Pref(applicationContext)
        // chequear si te has logeado
        if (prefs.isLogged) {
            startActivity(
                Intent(
                    applicationContext,
                    MainDashboard::class.java // hacerle la peticion del token
                )
            )
            // end current activity
            finish()
        } else {
            // init dialog
            initDialog()
//            // init field to handle IME_ACTION_DONE
//            field = findViewById(R.id.et_password_login)
        }



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

    /**
     * Dialog OnFailure
     */
    private fun showDialogOnFailure(msg: String) {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_warning)
            .setTitle(R.string.dialog_err_title)
            .setCancelable(false)
            .setMessage(msg)
            .setPositiveButton(
                R.string.ok
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    /**
     * Initialize dialog
     */
    private fun initDialog() {
        pDialog = KProgressHUD(this)
        pDialog.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        pDialog.setCancellable(true)
        pDialog.setAnimationSpeed(2)
        pDialog.setDimAmount(0.5f)
    }

    private fun showDialog() {
        if (!pDialog.isShowing) pDialog.show()
    }

    private fun hideDialog() {
        if (pDialog.isShowing) pDialog.dismiss()
    }

}