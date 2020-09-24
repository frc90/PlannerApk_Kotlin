package com.frc90.plannerapk_kotlin.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.frc90.plannerapk_kotlin.model.Token
import com.frc90.plannerapk_kotlin.model.UserData
import com.frc90.plannerapk_kotlin.networking.routes.Routes
import com.frc90.plannerapk_kotlin.networking.services.ApiService
import com.frc90.plannerapk_kotlin.view.activities.ResponseActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        setContentView(getLayout())
    }

    @LayoutRes
    abstract fun getLayout(): Int

    // toast
    fun showTextToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    // login con las credenciales
    fun login(name: String, password: String):Boolean{
        if (!isValiName(name)){
            Toast.makeText(
                this,
                "Tu nombre de usuario no es correcto",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }else if (!isValiPassword(password)) {
            Toast.makeText(
                this,
                "La contraseña no es correcta, tiene que ser mayor a 8 caracteres, por favor intentelo de nuevo",
                Toast.LENGTH_SHORT
            ).show();
            return false;
        } else {
            return true;
        }
    }

    // validaciones
    fun isValiName(name: String):Boolean {
        return !TextUtils.isEmpty(name)
    }

    fun isValiPassword(passwrod: String):Boolean {
        return passwrod.length > 8;
    }


    /*
    * Preferences
    * */


    // mostrar los datos guardados
    fun showData(){
        val sharePref = getPreferences(Context.MODE_PRIVATE)
        val user = sharePref.getString("userName","")
        val pass = sharePref.getString("userPass","")
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)){
            input_full_name.setText(user)
            input_pass.setText(pass)
            cb_remember_me.isChecked = true
        }
    }

    // salvar los datos en modo privado
    fun saveData(){
        val sharePref = getPreferences(Context.MODE_PRIVATE)
        with(sharePref.edit()){
            putString("userName", input_full_name.text.toString())
            putString("userPass", input_pass.text.toString())
            commit()
        }
    }

    // borrar los datos
    fun deleteData(){
        val sharePref = getPreferences(Context.MODE_PRIVATE)
        with(sharePref.edit()){
            putString("userName", "")
            putString("userPass", "")
            commit()
        }
    }



    // guardar los token de acceso
    private fun saveTokenPref(tokens: String) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("tokens", tokens)
            commit()
        }
    }
    /*end Preferences*/


    // para crear el token y pasar para la otra activity
    fun createToken(userData: UserData) {

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
                    var intent = Intent(this@BaseActivity, ResponseActivity::class.java)
                    // para no virar hacia el login
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("access", tokens.access)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@BaseActivity, response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                Toast.makeText(
                    this@BaseActivity,
                    "Asegurece de estar conectado a internet",
                    Toast.LENGTH_LONG
                ).show()
                t?.printStackTrace()
                call.cancel()
            }
        })

    }
}