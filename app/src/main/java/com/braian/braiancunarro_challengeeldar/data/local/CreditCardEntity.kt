package com.braian.braiancunarro_challengeeldar.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "credit_cards")
data class CreditCardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cardHolderName: String,
    val cardNumber: String,
    val expirationMonth: String,
    val expirationYear: String,
    val securityCode: String,
    val brand:String
)
