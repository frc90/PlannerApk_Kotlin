package com.frc90.plannerapk_kotlin.model


import com.google.gson.annotations.SerializedName

data class Goal(
    @SerializedName("id")
    val id: Int,
    @SerializedName("short_name")
    val shortName: String
)