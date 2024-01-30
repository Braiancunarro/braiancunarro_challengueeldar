package com.braian.braiancunarro_challengeeldar.data.repository

import com.braian.braiancunarro_challengeeldar.data.model.QRCodeRequest
import com.braian.braiancunarro_challengeeldar.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface QRCodeService {

    @Headers(
        "Content-Type: application/json",
        "X-RapidAPI-Host: ${Constants.QR_API_HOST}",
        "X-RapidAPI-Key:${Constants.QR_API_KEY}"
    )
    @POST(Constants.QR_API_PATH)
    suspend fun generateQRCode(@Body request: QRCodeRequest): Response<ResponseBody>
}
