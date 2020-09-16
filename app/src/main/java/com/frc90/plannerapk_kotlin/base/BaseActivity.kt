package com.frc90.plannerapk_kotlin.base

import android.content.Context
import android.os.Bundle
import android.os.Message
import android.os.PersistableBundle
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

    // no funciona
    fun Context.toast(
        context: Context = applicationContext,
        message: String,
        duration: Int = Toast.LENGTH_SHORT
    ) {
    }
}