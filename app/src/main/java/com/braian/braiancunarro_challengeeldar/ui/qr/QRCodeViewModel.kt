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
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.InputStream

class QRCodeViewModel(private val qrCodeService: QRCodeService) : ViewModel() {
    private val _qrCodeBitmapLiveData = MutableLiveData<Bitmap>()
    val qrCodeBitmapLiveData: LiveData<Bitmap> get() = _qrCodeBitmapLiveData


    fun generateQRCode(content: String, width: Int, height: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = QRCodeRequest(content, width, height)
                val response: Response<ResponseBody> = qrCodeService.generateQRCode(request)

                if (response.isSuccessful) {
                    // Obtener el InputStream de la respuesta
                    val inputStream: InputStream = response.body()!!.byteStream()

                    // Decodificar el InputStream a Bitmap
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    // Publicar en el hilo principal
                    withContext(Dispatchers.Main) {
                        _qrCodeBitmapLiveData.value = bitmap
                    }
                } else {
                    // Manejar errores según el código de respuesta HTTP
                    // response.code(), response.message()
                }
            } catch (e: Exception) {
                // Manejar errores, por ejemplo, mostrar un mensaje de error
                e.printStackTrace()
            }
        }
    }

}

