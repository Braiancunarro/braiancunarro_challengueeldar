package com.braian.braiancunarro_challengeeldar.data.creditCardDto

data class CreditCard(
    val brand: String, // "Visa", "Mastercard", "American Express", etc.
    val cardNumber: String,
    val expirationDate: String,
    val cardHolderName: String
)
