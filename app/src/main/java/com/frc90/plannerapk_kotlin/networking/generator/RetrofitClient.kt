package com.frc90.plannerapk_kotlin.networking.generator

import com.frc90.plannerapk_kotlin.networking.routes.Routes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

class RetrofitClient {
    companion object{
        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit? {
            @Synchronized
            if (retrofit == null) {

                val gsonBuilder: Gson = GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .setPrettyPrinting()
                    .setVersion(1.0)
                    .create()

                val httpClient = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .proxy(Proxy.NO_PROXY)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(Routes.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                    .client(httpClient)
                    .build()

            }
            return retrofit
        }
    }
}