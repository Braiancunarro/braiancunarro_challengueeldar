package com.braian.braiancunarro_challengeeldar.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.braian.braiancunarro_challengeeldar.R

// BindingAdapters.kt

@BindingAdapter("cardBrandImage")
fun setCardBrandImage(imageView: ImageView, cardNumber: String?) {
    cardNumber?.let {
        val firstDigit = it.firstOrNull()?.toString() ?: ""
        val drawableId = when (firstDigit) {
            "3" -> R.drawable.americanexpress
            "4" -> R.drawable.visa
            "5" -> R.drawable.mastercard
            else -> R.drawable.card_credit
        }
        imageView.setImageResource(drawableId)
    }
}
