package com.braian.braiancunarro_challengeeldar.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardDao
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(private val creditCardDao: CreditCardDao) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(creditCardDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}