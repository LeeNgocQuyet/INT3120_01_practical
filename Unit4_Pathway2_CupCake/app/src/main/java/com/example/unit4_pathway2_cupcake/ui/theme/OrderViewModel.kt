package com.example.unit4_pathway2_cupcake.ui.theme

import androidx.lifecycle.ViewModel
import com.example.unit4_pathway2_cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Price for a single cupcake */
private const val PRICE_PER_CUPCAKE = 2.00

/** Additional cost for same day pickup of an order */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

private val FLAVOR_EXTRA_PRICE = mapOf(
    "Vanilla" to 0.0,
    "Chocolate" to 1.0,
    "Red Velvet" to 1.5,
    "Salted Caramel" to 2.0,
    "Coffee" to 2.5
)

/**
 * [OrderViewModel] holds information about a cupcake order in terms of quantity, flavor, and
 * pickup date. It also knows how to calculate the total price based on these order details.
 */
class OrderViewModel : ViewModel() {

    /**
     * Cupcake state for this order
     */
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    /**
     * Set the quantity [numberCupcakes] of cupcakes for this order's state and update the price
     */
    fun setQuantity(numberCupcakes: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberCupcakes,
                price = calculatePrice(quantity = numberCupcakes)
            )
        }
    }

    /**
     * Set the [desiredFlavor] of cupcakes for this order's state.
     * Only 1 flavor can be selected for the whole order.
     */
    fun setFlavor(desiredFlavor: String) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(flavor = desiredFlavor)
            updatedState.copy(
                price = calculatePrice(
                    quantity = updatedState.quantity,
                    flavor = updatedState.flavor,
                    pickupDate = updatedState.date
                )
            )
        }
    }

    /**
     * Set the [pickupDate] for this order's state and update the price
     */
    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            val updatedState = currentState.copy(date = pickupDate)
            updatedState.copy(
                price = calculatePrice(
                    quantity = updatedState.quantity,
                    flavor = updatedState.flavor,
                    pickupDate = updatedState.date
                )
            )
        }
    }

    /**
     * Reset the order state
     */
    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }

    /**
     * Returns the calculated price based on the order details.
     */
    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.date,
        flavor: String = _uiState.value.flavor
    ): String {
        var calculatedPrice = quantity * PRICE_PER_CUPCAKE

        val flavorExtra = FLAVOR_EXTRA_PRICE[flavor] ?: 0.0
        calculatedPrice += quantity * flavorExtra
        /*
        if (pickupOptions().firstOrNull() == pickupDate) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        */
        val index = pickupOptions().indexOf(pickupDate)
        val dayExtra = if (index >= 0) index.toDouble() else 0.0
        calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP * dayExtra

        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
        return formattedPrice
    }

    /**
     * Returns a list of date options starting with the current date and the following 3 dates.
     */
    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // add current date and the following 3 dates.
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}
