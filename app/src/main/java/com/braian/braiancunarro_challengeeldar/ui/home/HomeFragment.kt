package com.braian.braiancunarro_challengeeldar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.braian.braiancunarro_challengeeldar.R
import com.braian.braiancunarro_challengeeldar.adapter.CardAdapter
import com.braian.braiancunarro_challengeeldar.adapter.MarginItemDecoration
import com.braian.braiancunarro_challengeeldar.adapter.OnHeaderClickListener
import com.braian.braiancunarro_challengeeldar.adapter.OnItemClickListener
import com.braian.braiancunarro_challengeeldar.data.creditCardDto.CreditCard
import com.braian.braiancunarro_challengeeldar.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), OnItemClickListener , OnHeaderClickListener {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var cardAdapter: CardAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val creditCardList = listOf(
            CreditCard("Visa", "**** **** **** 1234", "12/23", "TITULAR DE LA TARJETA"),
            CreditCard("Mastercard", "**** **** **** 5678", "08/24", "TITULAR DE LA TARJETA"),
            CreditCard("American Express", "**** **** **** 9012", "05/22", "TITULAR DE LA TARJETA")
        )

       binding.rvCards.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Configurar el adaptador
        cardAdapter = CardAdapter(creditCardList, this,this)
        binding.rvCards.adapter = cardAdapter
        binding.rvCards.addItemDecoration(MarginItemDecoration(8))

        return root
    }

    override fun onItemClick(position: Int) {
        if (position == -1){
            findNavController().navigate(R.id.action_navigation_home_to_addCardFragment)
        }
        Toast.makeText(requireContext(), "Clic en la tarjeta $position", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}