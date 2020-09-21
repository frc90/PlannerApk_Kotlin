package com.frc90.plannerapk_kotlin.model

import com.google.gson.annotations.SerializedName

data class UserData (
    @SerializedName("username")
    var userName:String,

    @SerializedName("password")
    var password:String
)