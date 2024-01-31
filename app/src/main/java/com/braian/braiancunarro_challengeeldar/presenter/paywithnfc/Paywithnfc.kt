package com.braian.braiancunarro_challengeeldar.presenter.paywithnfc

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(PaywithnfcViewModel::class.java)


        viewModel.cardListLiveData.observe(viewLifecycleOwner, Observer { cards ->
            val cardNames =
                cards.map { it.cardHolderName }
            setupCardSpinner(cardNames)
        })


    }

    private fun setupCardSpinner(cardNames: List<String>) {

        val adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, cardNames)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)


        binding.spinnerCards.adapter = adapter


        binding.spinnerCards.visibility = View.VISIBLE
        binding.textSelectedCard.visibility = View.VISIBLE

        binding.buttonPay.setOnClickListener {
            showSuccessDialog()
        }
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
        findNavController().navigate(com.braian.braiancunarro_challengeeldar.R.id.navigation_home)
    }
}
