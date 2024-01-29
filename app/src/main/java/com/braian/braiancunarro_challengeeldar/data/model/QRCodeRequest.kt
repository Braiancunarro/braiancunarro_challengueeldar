package com.braian.braiancunarro_challengeeldar.data.model

data class QRCodeRequest(
    val content: String,
    val width: Int = 300,
    val height: Int = 300
)
