package com.braian.braiancunarro_challengeeldar.presenter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardDao
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val creditCardDao: CreditCardDao) : ViewModel() {

    val cardListLiveData: LiveData<List<CreditCardEntity>> = creditCardDao.getAllCreditCards()

    fun getCardList() : LiveData<List<CreditCardEntity>>  {
        return runBlocking {
            creditCardDao.getAllCreditCards()
        }
    }
}