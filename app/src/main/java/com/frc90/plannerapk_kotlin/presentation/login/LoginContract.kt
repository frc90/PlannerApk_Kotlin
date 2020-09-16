package com.frc90.plannerapk_kotlin.presentation.login

interface LoginContract{

    interface View{
        fun showError(msgError: String)
        fun showProgressBar()
        fun hideProgressBar()
        fun signIn()
    }
}