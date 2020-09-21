package com.frc90.plannerapk_kotlin.model


import com.google.gson.annotations.SerializedName

data class Partaker(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)