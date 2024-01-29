package com.braian.braiancunarro_challengeeldar.ui.addcard

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braian.braiancunarro_challengeeldar.R
import com.braian.braiancunarro_challengeeldar.data.creditCardDto.CardModel
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardDao
import com.braian.braiancunarro_challengeeldar.data.local.CreditCardEntity
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

    // LiveData para la lista de tarjetas
    private val _cardListLiveData = MutableLiveData<List<CardModel>>()
    val cardListLiveData: MutableLiveData<List<CardModel>> = _cardListLiveData

    init {
        // Observar cambios en cardHolderName y actualizar exampleHolderName
        cardHolderName.addSource(cardHolderName) { newValue ->
            exampleHolderName.value = newValue
        }

        // Observar cambios en cardNumber y actualizar exampleCardNumber
        cardNumber.observeForever { newValue ->
            newValue?.let {
                // Obtener el primer dígito del número de tarjeta
                val firstDigit = it.firstOrNull()?.toString() ?: ""

                // Actualizar la imagen según el primer dígito
                updateCardBrandImage(firstDigit)
            }
        }

        // Observar cambios en expirationMonth y expirationYear y actualizar exampleExpirationDate
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
        // Crear un nuevo objeto CardModel con los datos ingresados
        val newCard = CardModel(
            cardHolderName.value.orEmpty(),
            cardNumber.value.orEmpty(),
            expirationMonth.value.orEmpty(),
            expirationYear.value.orEmpty(),
            securityCode.value.orEmpty(),
            brand.value.orEmpty()
        )

        // Agregar la nueva tarjeta a la lista
        cardList.add(newCard)

        // Guardar la nueva tarjeta en la base de datos
        saveCreditCardToDatabase(newCard)

        // Notificar a la interfaz de usuario sobre el cambio en la lista de tarjetas
        _cardListLiveData.value = cardList.toList()

        // Restablecer los campos después de guardar la tarjeta (si es necesario)
        resetFields()

        // Notificar a la interfaz de usuario u otros componentes según sea necesario
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
        // Restablecer los campos a sus valores iniciales o vacíos según sea necesario
        cardHolderName.value = ""
        cardNumber.value = ""
        expirationMonth.value = ""
        expirationYear.value = ""
        securityCode.value = ""
    }

    // Método para guardar la tarjeta de crédito en la base de datos
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
