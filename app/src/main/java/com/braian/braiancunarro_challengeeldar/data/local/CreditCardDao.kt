package com.braian.braiancunarro_challengeeldar.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CreditCardDao {
    @Insert
    suspend fun insertCreditCard(creditCard: CreditCardEntity)

    @Query("SELECT * FROM credit_cards")
    fun getAllCreditCards(): LiveData<List<CreditCardEntity>>
}
