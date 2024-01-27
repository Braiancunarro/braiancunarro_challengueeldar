package com.braian.braiancunarro_challengeeldar.ui.addcard

import AddCardViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.braian.braiancunarro_challengeeldar.databinding.FragmentAddCardBinding

class AddCardFragment : Fragment() {

    private val viewModel: AddCardViewModel by viewModels()
    private lateinit var binding: FragmentAddCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observar los cambios en los datos y actualizar la interfaz de usuario
        viewModel.cardHolderName.observe(viewLifecycleOwner, Observer { name ->
            binding.etCardHolderName.setText(name)
        })

        viewModel.cardNumber.observe(viewLifecycleOwner, Observer { number ->
            binding.etCardNumber.setText(number)
        })

        viewModel.expirationMonth.observe(viewLifecycleOwner, Observer { month ->
            binding.actvExpirationMonth.setText(month)
        })

        viewModel.expirationYear.observe(viewLifecycleOwner, Observer { year ->
            binding.actvExpirationYear.setText(year)
        })

        viewModel.securityCode.observe(viewLifecycleOwner, Observer { code ->
            binding.etSecurityCode.setText(code)
        })

        viewModel.cardBrandImage.observe(viewLifecycleOwner, Observer { imageResource ->
            binding.ivExampleCardBrand.setImageResource(imageResource)
        })

        // Configurar listeners para los campos de texto
        binding.etCardHolderName.addTextChangedListener {
            viewModel.setCardHolderName(it.toString())
        }

        binding.etCardNumber.addTextChangedListener {
            viewModel.setCardNumber(it.toString())
        }

        binding.actvExpirationMonth.addTextChangedListener {
            viewModel.setExpirationMonth(it.toString())
        }

        binding.actvExpirationYear.addTextChangedListener {
            viewModel.setExpirationYear(it.toString())
        }

        binding.etSecurityCode.addTextChangedListener {
            viewModel.setSecurityCode(it.toString())
        }
    }
}
