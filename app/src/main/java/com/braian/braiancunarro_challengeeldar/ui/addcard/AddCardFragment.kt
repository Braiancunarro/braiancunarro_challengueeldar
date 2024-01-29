// AddCardFragment.kt
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
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardDao
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

        // Inicializar el ViewModel
        val appDatabase = AppDatabase.getDatabase(requireContext())
        val creditCardDao = appDatabase.creditCardDao()
        val viewModelFactory = AddCardViewModelFactory(creditCardDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddCardViewModel::class.java)
        // Establecer el ciclo de vida para el DataBinding
        binding.lifecycleOwner = viewLifecycleOwner

        // Establecer el ViewModel en el DataBinding
        binding.viewModel = viewModel

        // Configurar el TextWatcher para cardHolderName
        binding.etCardHolderName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.cardHolderName.value = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
                // No es necesario implementar
            }
        })

        // Configurar el TextWatcher para cardNumber
        binding.etCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.cardNumber.value = s?.toString() ?: ""
                viewModel.exampleCardNumber.value = formatCardNumber(s?.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // No es necesario implementar
            }
        })


        // Configurar el TextWatcher para securityCode
        binding.etSecurityCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.securityCode.value = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
                // No es necesario implementar
            }
        })

        // Configurar el TextWatcher para expirationMonth y expirationYear
        binding.actvExpirationMonth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.expirationMonth.value = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
                // No es necesario implementar
            }
        })

        binding.actvExpirationYear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No es necesario implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.expirationYear.value = s?.toString() ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
                // No es necesario implementar
            }
        })

        // Configurar el ClickListener para el botón "Agregar Tarjeta"
        binding.btnAddCard.setOnClickListener {
            viewModel.onAddCardButtonClick()
            // Aquí puedes manejar la lógica adicional después de agregar la tarjeta, si es necesario
        }

        // Observar cambios en la lista de tarjetas
        viewModel.cardListLiveData.observe(viewLifecycleOwner) { cardList ->
            // Actualizar la interfaz de usuario con la nueva lista de tarjetas
            // Puedes agregar lógica para mostrar las tarjetas en un RecyclerView aquí
            // cardList contiene la lista actualizada de tarjetas
        }
    }

    fun formatCardNumber(cardNumber: String?): String {
        cardNumber?.let {
            // Eliminar cualquier espacio en blanco y no números
            val cleanCardNumber = it.replace("[^\\d]".toRegex(), "")

            // Dividir en secuencias de 4 caracteres
            return cleanCardNumber.chunked(4).joinToString(" ")
        }
        return ""
    }
}
