package com.frc90.plannerapk_kotlin.base

import android.content.Context
import android.os.Bundle
import android.os.Message
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

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
            Toast.makeText(this,
                "Tu nombre de usuario no es correcto",
                Toast.LENGTH_SHORT).show()
            return false
        }else if (!isValiPassword(password)) {
            Toast.makeText(this,
                "La contraseña no es correcta, tiene que ser mayor a 8 caracteres, por favor intentelo de nuevo",
                Toast.LENGTH_SHORT).show();
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
}