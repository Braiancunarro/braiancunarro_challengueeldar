package com.braian.braiancunarro_challengeeldar.ui.qr

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.braian.braiancunarro_challengeeldar.data.repository.RetrofitBuilder
import com.braian.braiancunarro_challengeeldar.databinding.FragmentHomeBinding
import com.braian.braiancunarro_challengeeldar.databinding.FragmentQrGeneratorBinding
import com.braian.braiancunarro_challengeeldar.ui.home.HomeViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [QrGeneratorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QrGeneratorFragment : Fragment() {

    private lateinit var binding: FragmentQrGeneratorBinding
    private lateinit var qrCodeViewModel: QRCodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrGeneratorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val qrCodeService = RetrofitBuilder.createQRCodeService()
        qrCodeViewModel = ViewModelProvider(this, QRCodeViewModelFactory(qrCodeService)).get(QRCodeViewModel::class.java)

        qrCodeViewModel.generateQRCode("TuContenido", 300, 300)

        // Ahora puedes usar el viewModel para generar un código QR
        qrCodeViewModel.qrCodeBitmapLiveData.observe(viewLifecycleOwner) { bitmap ->
            // Actualizar la interfaz de usuario con el código QR
            Log.d(bitmap.toString(),"QR")
            binding.imgQrCode.setImageBitmap(bitmap)
        }
    }
    }


