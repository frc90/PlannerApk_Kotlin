package com.frc90.plannerapk_kotlin.networking.services

import com.frc90.plannerapk_kotlin.model.CurrentMonth
import com.frc90.plannerapk_kotlin.model.Token
import com.frc90.plannerapk_kotlin.model.UserData
import com.frc90.plannerapk_kotlin.networking.routes.Routes
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // create_topken
    @Headers("Accept: application/json")
    @POST(Routes.CREATE_TOKEN)
    fun getAccessToken(
        @Body userData: UserData
    ): Call<Token>

    // current_month
    @GET(Routes.CURRENT_MONTH)
    fun getCurrentMonth(@Header("Authorization") token: String): Call<CurrentMonth>
}