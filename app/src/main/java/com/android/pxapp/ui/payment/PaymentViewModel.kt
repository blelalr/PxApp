package com.android.pxapp.ui.payment

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.android.pxapp.data.model.BannerData
import com.android.pxapp.data.model.MessageData
import com.android.pxapp.data.model.PaymentQrData
import com.android.pxapp.data.network.ApiState
import com.android.pxapp.data.repository.AppRepo
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class PaymentViewModel(private val appRepo : AppRepo = AppRepo()) : ViewModel() {

    private val _paymentQrData = MutableLiveData<PaymentQrData>()


    fun getPaymentQrCodeFromRepo(): LiveData<ApiState> {
        return flow {
            emit(ApiState.Loading)
            emit(appRepo.loadPaymentQrCodeData())
        }.asLiveData(Dispatchers.IO)
    }

    fun getQrCodeBitmap(qrCodeContent: String): Bitmap {
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val size = 100
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE,  size, size, hints)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    fun setPaymentQrData(paymentQrData: PaymentQrData) {
        _paymentQrData.postValue(paymentQrData)
    }

    fun getPaymentQrData(): LiveData<PaymentQrData> {
        return _paymentQrData
    }
}