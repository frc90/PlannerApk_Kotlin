package com.frc90.plannerapk_kotlin.model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("acapite")
    val acapite: Acapite?,
    @SerializedName("accomplished")
    val accomplished: Boolean?,
    @SerializedName("all_day")
    val allDay: Boolean,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("goal")
    val goal: Goal?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("leading")
    val leading: List<Leading>?,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("observation")
    val observation: String?,
    @SerializedName("owner")
    val owner: Owner?,
    @SerializedName("parent_activity")
    val parentActivity: Any?,
    @SerializedName("partaker")
    val partaker: List<Partaker>?,
    @SerializedName("performer")
    val performer: List<Any>?,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("status")
    val status: String?,
    @SerializedName("strategy")
    val strategy: List<Strategy>?
)