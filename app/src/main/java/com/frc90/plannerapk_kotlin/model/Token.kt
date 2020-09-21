package com.frc90.plannerapk_kotlin.model


import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("access")
    val access: String,
    @SerializedName("refresh")
    val refresh: String
)