package com.braian.braiancunarro_challengeeldar.presenter.paywithnfc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardDao
import javax.inject.Inject

class PaywithnfcViewModelFactory @Inject constructor(private val creditCardDao: CreditCardDao) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaywithnfcViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaywithnfcViewModel(creditCardDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}