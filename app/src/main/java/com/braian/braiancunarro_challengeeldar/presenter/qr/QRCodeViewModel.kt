package com.braian.braiancunarro_challengeeldar.presenter.qr

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braian.braiancunarro_challengeeldar.data.model.QRCodeRequest
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
                    val inputStream: InputStream = response.body()!!.byteStream()

                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    withContext(Dispatchers.Main) {
                        _qrCodeBitmapLiveData.value = bitmap
                    }
                } else {
                    // response.code(), response.message()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

