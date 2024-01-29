package com.braian.braiancunarro_challengeeldar.data.creditCardDto

data class CardModel(
    val cardHolderName: String,
    val cardNumber: String,
    val expirationMonth: String,
    val expirationYear: String,
    val securityCode: String,
    val brand : String
)
