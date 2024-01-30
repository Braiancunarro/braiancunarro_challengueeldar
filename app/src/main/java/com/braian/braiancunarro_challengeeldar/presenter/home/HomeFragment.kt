package com.braian.braiancunarro_challengeeldar.presenter.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.braian.braiancunarro_challengeeldar.R
import com.braian.braiancunarro_challengeeldar.adapter.CardAdapter
import com.braian.braiancunarro_challengeeldar.adapter.MarginItemDecoration
import com.braian.braiancunarro_challengeeldar.adapter.OnHeaderClickListener
import com.braian.braiancunarro_challengeeldar.adapter.OnItemClickListener
import com.braian.braiancunarro_challengeeldar.data.local.AppDatabase
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardEntity
import com.braian.braiancunarro_challengeeldar.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClickListener, OnHeaderClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var cardAdapter: CardAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.btnPayWithQR.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_qrGeneratorFragment)
        }

        binding.btnaddMoney.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_dashboard)
        }
        binding.btnpagoNFC.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_paywithnfc)
        }
        binding.btntransf.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_dashboard)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        observeCardList()
    }

    private fun setupRecyclerView() {

        val creditCardList = listOf(
            CreditCardEntity(
                cardHolderName = "TITULAR DE LA TARJETA",
                cardNumber = "**** **** **** 1234",
                expirationMonth = "12",
                expirationYear = "23",
                securityCode = "123",
                brand = "Visa"
            ),
            CreditCardEntity(
                cardHolderName = "TITULAR DE LA TARJETA",
                cardNumber = "**** **** **** 5678",
                expirationMonth = "08",
                expirationYear = "24",
                securityCode = "456",
                brand = "Mastercard"
            ),
            CreditCardEntity(
                cardHolderName = "TITULAR DE LA TARJETA",
                cardNumber = "**** **** **** 9012",
                expirationMonth = "05",
                expirationYear = "22",
                securityCode = "789",
                brand = "American Express"
            )
        )
        binding.rvCards.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        cardAdapter = CardAdapter(creditCardList, this, this)
        binding.rvCards.adapter = cardAdapter
        binding.rvCards.addItemDecoration(MarginItemDecoration(8))
    }

    private fun observeCardList() {
        val appDatabase = AppDatabase.getDatabase(requireContext())
        val creditCardDao = appDatabase.creditCardDao()
        val viewModelFactory = HomeViewModelFactory(creditCardDao)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        homeViewModel.cardListLiveData.observe(viewLifecycleOwner, Observer { cardList ->
            if (cardList.isNotEmpty()) {
                val firstCard = cardList[0]
                cardAdapter.submitList(cardList)
                Log.d("HomeFragment", "First card: $firstCard")
            } else {
                Log.d("HomeFragment", "La lista de tarjetas está vacía.")
            }
        })
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(), "Clic en la tarjeta $position", Toast.LENGTH_SHORT).show()
    }

    override fun onHeaderClick(position: Int) {
        if (position == -1) {
            findNavController().navigate(R.id.action_navigation_home_to_addCardFragment)
        }
    }
}
