package com.braian.braiancunarro_challengeeldar.ui.qr

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardEntity
import com.braian.braiancunarro_challengeeldar.data.model.QRCodeRequest
import com.braian.braiancunarro_challengeeldar.data.model.QRCodeResponse
import com.braian.braiancunarro_challengeeldar.data.repository.QRCodeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class QRCodeViewModel(private val qrCodeService: QRCodeService) : ViewModel() {
    private val _qrCodeBitmapLiveData = MutableLiveData<Bitmap>()
    val qrCodeBitmapLiveData: LiveData<Bitmap> get() = _qrCodeBitmapLiveData


    fun generateQRCode(content: String, width: Int, height: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = QRCodeRequest(content, width, height)
                val response: Response<QRCodeResponse> = qrCodeService.generateQRCode(request)

                if (response.isSuccessful) {
                    val byteArray = response.body()?.image!!.toByteArray()
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray?.size ?: 0)

                    withContext(Dispatchers.Main) {
                        _qrCodeBitmapLiveData.value = bitmap
                    }
                } else {
                    // Manejar errores según el código de respuesta HTTP
                    // response.code(), response.message()
                    Log.e("QRCodeViewModel", "Error en la llamada al servicio: ${response.code()}")
                }
            } catch (e: Exception) {
                // Manejar errores, por ejemplo, mostrar un mensaje de error
                e.printStackTrace()
                Log.e("QRCodeViewModel", "Error en la llamada al servicio: ${e.message}")
            }
        }
    }
}

