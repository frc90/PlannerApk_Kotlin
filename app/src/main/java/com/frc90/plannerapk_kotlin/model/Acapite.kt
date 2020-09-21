package com.frc90.plannerapk_kotlin.model


import com.google.gson.annotations.SerializedName

data class Acapite(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int
)