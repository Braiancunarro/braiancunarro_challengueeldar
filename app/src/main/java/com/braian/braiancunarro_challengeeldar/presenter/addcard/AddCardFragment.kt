package com.braian.braiancunarro_challengeeldar.presenter.addcard

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.braian.braiancunarro_challengeeldar.R
import com.braian.braiancunarro_challengeeldar.data.local.AppDatabase
import com.braian.braiancunarro_challengeeldar.databinding.FragmentAddCardBinding

class AddCardFragment : Fragment() {

    private lateinit var binding: FragmentAddCardBinding
    private lateinit var viewModel: AddCardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val appDatabase = AppDatabase.getDatabase(requireContext())
        val creditCardDao = appDatabase.creditCardDao()
        val viewModelFactory = AddCardViewModelFactory(creditCardDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddCardViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.etCardHolderName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.cardHolderName.value = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.etCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cleanCardNumber = s?.toString()?.replace("[^\\d]".toRegex(), "") ?: ""
                if (cleanCardNumber.length > 16) {
                    viewModel.cardNumber.value = cleanCardNumber.substring(0, 16)
                    binding.etCardNumber.setText(viewModel.cardNumber.value)
                    binding.etCardNumber.setSelection(16)
                } else {
                    viewModel.cardNumber.value = cleanCardNumber
                }
                viewModel.exampleCardNumber.value = formatCardNumber(viewModel.cardNumber.value)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })



        binding.etSecurityCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ((s?.length ?: 0) > 3) {
                    viewModel.securityCode.value = s?.subSequence(0, 3).toString()
                    binding.etSecurityCode.setText(viewModel.securityCode.value)
                    binding.etSecurityCode.setSelection(3)
                } else {
                    viewModel.securityCode.value = s?.toString() ?: ""
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.actvExpirationMonth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ((s?.length ?: 0) > 2) {
                    viewModel.expirationMonth.value = s?.subSequence(0, 2).toString()
                    binding.actvExpirationMonth.setText(viewModel.expirationMonth.value)
                    binding.actvExpirationMonth.setSelection(2)
                } else {
                    viewModel.expirationMonth.value = s?.toString() ?: ""
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.actvExpirationYear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ((s?.length ?: 0) > 2) {
                    viewModel.expirationYear.value = s?.subSequence(0, 2).toString()
                    binding.actvExpirationYear.setText(viewModel.expirationYear.value)
                    binding.actvExpirationYear.setSelection(2)
                } else {
                    viewModel.expirationYear.value = s?.toString() ?: ""
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.btnAddCard.setOnClickListener {
            viewModel.onAddCardButtonClick()
            showSuccessDialog()
        }

        viewModel.cardListLiveData.observe(viewLifecycleOwner) { cardList ->
        }
    }

    fun formatCardNumber(cardNumber: String?): String {
        cardNumber?.let {
            val cleanCardNumber = it.replace("[^\\d]".toRegex(), "")
            val truncatedCardNumber = cleanCardNumber.take(16)
            return cleanCardNumber.chunked(4).joinToString(" ")
        }
        return ""
    }
    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Tarjeta Agregada")
            .setMessage("Tarjeta agregada con éxito.")
            .setPositiveButton("Cerrar") { dialog, _ ->
                dialog.dismiss()
                navigateToHomeFragment()
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.navigation_home)
    }

}
