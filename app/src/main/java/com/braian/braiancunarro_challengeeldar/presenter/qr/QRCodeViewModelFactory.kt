package com.braian.braiancunarro_challengeeldar.presenter.qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.braian.braiancunarro_challengeeldar.data.repository.QRCodeService

open class ViewModelFactory<T>(private val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QRCodeViewModel::class.java)) {
            return creator() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

class QRCodeViewModelFactory(private val qrCodeService: QRCodeService) :
    ViewModelFactory<QRCodeViewModel>({ QRCodeViewModel(qrCodeService)})