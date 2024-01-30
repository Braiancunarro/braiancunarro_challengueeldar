package com.braian.braiancunarro_challengeeldar.ui.addcard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
                viewModel.cardNumber.value = s?.toString() ?: ""
                viewModel.exampleCardNumber.value = formatCardNumber(s?.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


        binding.etSecurityCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.securityCode.value = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.actvExpirationMonth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.expirationMonth.value = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.actvExpirationYear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.expirationYear.value = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.btnAddCard.setOnClickListener {
            viewModel.onAddCardButtonClick()
        }

        viewModel.cardListLiveData.observe(viewLifecycleOwner) { cardList ->
        }
    }

    fun formatCardNumber(cardNumber: String?): String {
        cardNumber?.let {
            val cleanCardNumber = it.replace("[^\\d]".toRegex(), "")

            return cleanCardNumber.chunked(4).joinToString(" ")
        }
        return ""
    }
}
