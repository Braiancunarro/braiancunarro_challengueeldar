package com.braian.braiancunarro_challengeeldar.data.repository

import com.braian.braiancunarro_challengeeldar.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = Constants.QR_BASE_URL

    fun createQRCodeService(): QRCodeService {
        val client = OkHttpClient.Builder().build()
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(QRCodeService::class.java)
    }
}
