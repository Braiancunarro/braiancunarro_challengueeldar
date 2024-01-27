package com.braian.braiancunarro_challengeeldar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.braian.braiancunarro_challengeeldar.R // Asegúrate de ajustar el nombre del paquete según tu aplicación
import com.braian.braiancunarro_challengeeldar.data.creditCardDto.CreditCard

class CardAdapter(
    private val cardList: List<CreditCard>,
    private val itemClickListener: OnItemClickListener,
    private val headerClickListener: OnHeaderClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_CARD = 1

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandImage: ImageView = itemView.findViewById(R.id.ivCardBrand)
        val cardNumber: TextView = itemView.findViewById(R.id.tvCardNumber)
        val expirationDate: TextView = itemView.findViewById(R.id.tvExpirationDate)
        val cardHolderName: TextView = itemView.findViewById(R.id.tvCardHolderName)

        init {
            itemView.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition - 1) // Restar 1 para ajustar la posición
            }
        }
    }
    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                headerClickListener.onItemClick(adapterPosition - 1) // Restar 1 para ajustar la posición
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_card_header, parent, false)
                HeaderViewHolder(view)
            }
            VIEW_TYPE_CARD -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
                CardViewHolder(view)
            }
            else -> throw IllegalArgumentException("View type not supported")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CardViewHolder -> {
                val card = cardList[position - 1] // Restar 1 para ajustar la posición
                holder.brandImage.setImageResource(getBrandImageResource(card.brand))
                holder.cardNumber.text = card.cardNumber
                holder.expirationDate.text = card.expirationDate
                holder.cardHolderName.text = card.cardHolderName
            }
        }
    }

    override fun getItemCount(): Int {
        // Agregar 1 al recuento total para incluir el encabezado
        return cardList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_CARD
        }
    }

    private fun getBrandImageResource(brand: String): Int {
        // Mapea la marca de la tarjeta a la correspondiente imagen de recursos
        return when (brand) {
            "Visa" -> R.drawable.visa
            "Mastercard" -> R.drawable.mastercard
            "American Express" -> R.drawable.americanexpress
            else -> R.drawable.visa // Imagen predeterminada o maneja otros casos
        }
    }
}


interface OnItemClickListener {
    fun onItemClick(position: Int)
}
interface OnHeaderClickListener {
    fun onItemClick(position: Int)
}
