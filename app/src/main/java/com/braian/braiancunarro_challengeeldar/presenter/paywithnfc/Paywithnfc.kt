package com.braian.braiancunarro_challengeeldar.presenter.paywithnfc

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.braian.braiancunarro_challengeeldar.data.local.AppDatabase
import com.braian.braiancunarro_challengeeldar.databinding.FragmentPaywithnfcBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Paywithnfc : Fragment() {

    private lateinit var binding: FragmentPaywithnfcBinding
    private lateinit var viewModel: PaywithnfcViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaywithnfcBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDatabase = AppDatabase.getDatabase(requireContext())
        val creditCardDao = appDatabase.creditCardDao()
        val viewModelFactory = PaywithnfcViewModelFactory(creditCardDao)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(PaywithnfcViewModel::class.java)


        // Observa los cambios en la lista de tarjetas y actualiza el Spinner
        viewModel.cardListLiveData.observe(viewLifecycleOwner, Observer { cards ->
            // Actualizar el Spinner con las tarjetas obtenidas de la base de datos
            val cardNames =
                cards.map { it.cardHolderName } // Adaptar según la estructura de tu entidad
            setupCardSpinner(cardNames)
        })

        // Otras lógicas aquí, como manejar la selección del método de pago y el botón de pago
    }

    private fun setupCardSpinner(cardNames: List<String>) {
        // Configurar el Adapter para el Spinner
        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, cardNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el Adapter al Spinner
        binding.spinnerCards.adapter = adapter

        // Mostrar el Spinner y ocultar otras vistas según sea necesario
        binding.spinnerCards.visibility = View.VISIBLE
        binding.textSelectedCard.visibility = View.VISIBLE
    }
}
