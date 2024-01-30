package com.braian.braiancunarro_challengeeldar.ui.addcard

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braian.braiancunarro_challengeeldar.R
import com.braian.braiancunarro_challengeeldar.data.creditCardDto.CardModel
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardDao
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardEntity
import com.braian.braiancunarro_challengeeldar.utils.EncryptionUtils
import kotlinx.coroutines.launch

class AddCardViewModel(private val creditCardDao: CreditCardDao) : ViewModel() {

    private val cardList = mutableListOf<CardModel>()

    val cardHolderName = MediatorLiveData<String>()
    val cardNumber = MutableLiveData<String>()
    val expirationMonth = MutableLiveData<String>()
    val expirationYear = MutableLiveData<String>()
    val securityCode = MutableLiveData<String>()
    val brand = MutableLiveData<String>()
    val exampleHolderName = MutableLiveData<String>().apply {
        value = "TITULAR DE LA TARJETA"
    }
    val exampleCardNumber = MutableLiveData<String>().apply {
        value = "**** **** **** 1234"
    }
    val exampleExpirationDate = MutableLiveData<String>().apply {
        value = "MM/YY"
    }
    val exampleImageCard = MutableLiveData<Int>().apply {
        value = R.drawable.card_credit
    }

    private val _cardListLiveData = MutableLiveData<List<CardModel>>()
    val cardListLiveData: MutableLiveData<List<CardModel>> = _cardListLiveData

    init {
        cardHolderName.addSource(cardHolderName) { newValue ->
            exampleHolderName.value = newValue
        }

        cardNumber.observeForever { newValue ->
            newValue?.let {
                val firstDigit = it.firstOrNull()?.toString() ?: ""

                updateCardBrandImage(firstDigit)
            }
        }

        val expirationWatcher: (String, String) -> Unit = { month, year ->
            exampleExpirationDate.value = "$month/$year"
        }

        expirationMonth.observeForever { month ->
            expirationWatcher(month, expirationYear.value.orEmpty())
        }

        expirationYear.observeForever { year ->
            expirationWatcher(expirationMonth.value.orEmpty(), year)
        }
    }

    fun onAddCardButtonClick() {
        val newCard = CardModel(
            EncryptionUtils().encrypt(cardHolderName.value.orEmpty()),
            EncryptionUtils().encrypt(cardNumber.value.orEmpty()),
            EncryptionUtils().encrypt(expirationMonth.value.orEmpty()),
            EncryptionUtils().encrypt(expirationYear.value.orEmpty()),
            EncryptionUtils().encrypt(securityCode.value.orEmpty()),
            EncryptionUtils().encrypt(brand.value.orEmpty())
        )

        cardList.add(newCard)

        saveCreditCardToDatabase(newCard)

        _cardListLiveData.value = cardList.toList()

        resetFields()

    }

    private fun updateCardBrandImage(firstDigit: String) {
        val drawableId = when (firstDigit) {
            "3" -> R.drawable.americanexpress
            "4" -> R.drawable.visa
            "5" -> R.drawable.mastercard
            else -> R.drawable.card_credit
        }

        exampleImageCard.value = drawableId
    }

    private fun resetFields() {
        cardHolderName.value = ""
        cardNumber.value = ""
        expirationMonth.value = ""
        expirationYear.value = ""
        securityCode.value = ""
    }

    private fun saveCreditCardToDatabase(card: CardModel) {
        viewModelScope.launch {
            creditCardDao.insertCreditCard(
                CreditCardEntity(
                    cardHolderName = card.cardHolderName,
                    cardNumber = card.cardNumber,
                    expirationMonth = card.expirationMonth,
                    expirationYear = card.expirationYear,
                    securityCode = card.securityCode,
                    brand = card.brand
                )
            )
        }
    }
}
