package com.frc90.plannerapk_kotlin.model


import com.google.gson.annotations.SerializedName

data class CurrentMonth(
    @SerializedName("filteredCount")
    val filteredCount: Int,
    @SerializedName("next")
    val next: Any?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("totalCount")
    val totalCount: Int
)