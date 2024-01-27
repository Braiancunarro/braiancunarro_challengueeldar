import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.braian.braiancunarro_challengeeldar.R

class AddCardViewModel : ViewModel() {

    private val _cardHolderName = MutableLiveData<String>()
    val cardHolderName: LiveData<String> get() = _cardHolderName

    private val _cardNumber = MutableLiveData<String>()
    val cardNumber: LiveData<String> get() = _cardNumber

    private val _expirationMonth = MutableLiveData<String>()
    val expirationMonth: LiveData<String> get() = _expirationMonth

    private val _expirationYear = MutableLiveData<String>()
    val expirationYear: LiveData<String> get() = _expirationYear

    private val _securityCode = MutableLiveData<String>()
    val securityCode: LiveData<String> get() = _securityCode

    private val _cardBrandImage = MutableLiveData<Int>()
    val cardBrandImage: LiveData<Int> get() = _cardBrandImage

    // Métodos para actualizar los datos
    fun setCardHolderName(name: String) {
        _cardHolderName.value = name
    }

    fun setCardNumber(number: String) {
        _cardNumber.value = number

        // Actualizar la imagen de la marca de la tarjeta según el primer dígito del número
        updateCardBrandImage(number)
    }

    fun setExpirationMonth(month: String) {
        _expirationMonth.value = month
    }

    fun setExpirationYear(year: String) {
        _expirationYear.value = year
    }

    fun setSecurityCode(code: String) {
        _securityCode.value = code
    }

    private fun updateCardBrandImage(cardNumber: String) {
        val firstDigit = cardNumber.firstOrNull()?.toString()?.toIntOrNull()

        // Asignar la imagen de la marca de la tarjeta según el primer dígito
        _cardBrandImage.value = when (firstDigit) {
            3 -> R.drawable.americanexpress
            4 -> R.drawable.visa
            5 -> R.drawable.mastercard
            else -> R.drawable.default_card_image // Imagen predeterminada o manejar otros casos
        }
    }
}
