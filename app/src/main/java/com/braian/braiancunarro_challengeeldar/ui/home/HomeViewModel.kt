package com.braian.braiancunarro_challengeeldar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardDao
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val creditCardDao: CreditCardDao) : ViewModel() {

    // LiveData para la lista de tarjetas
    val cardListLiveData: LiveData<List<CreditCardEntity>> = creditCardDao.getAllCreditCards()

    // Método para obtener la lista de tarjetas sincrónicamente
    fun getCardList() : LiveData<List<CreditCardEntity>>  {
        // Utilizar un bloque de runBlocking para esperar la respuesta de la base de datos
        return runBlocking {
            creditCardDao.getAllCreditCards()
        }
    }
}