package com.braian.braiancunarro_challengeeldar.presenter.addcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardDao

class AddCardViewModelFactory(private val creditCardDao: CreditCardDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddCardViewModel(creditCardDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}