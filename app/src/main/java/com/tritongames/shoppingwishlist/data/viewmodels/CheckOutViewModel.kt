package com.tritongames.shoppingwishlist.data.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.stripe.Stripe
import com.tritongames.shoppingwishlist.BuildConfig
import com.tritongames.shoppingwishlist.data.models.checkout.StripePaymentIntent
import com.tritongames.shoppingwishlist.data.repository.checkout.CheckoutRepository
import com.tritongames.shoppingwishlist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(private val checkoutRepo: CheckoutRepository, private val dispatchers: CoroutineDispatcher):
    ViewModel(){


    sealed class CheckoutLoadingEvent{
        class Success(val resultString: String): CheckoutLoadingEvent()
        class Failure(val errorString: String): CheckoutLoadingEvent()
        data object Loading: CheckoutLoadingEvent()
        data object Empty: CheckoutLoadingEvent()
    }

    private val _checkoutLoad = MutableStateFlow<CheckoutLoadingEvent>(CheckoutLoadingEvent.Empty)
    val checkoutLoad: StateFlow<CheckoutLoadingEvent> = _checkoutLoad

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun createNewPaymentIntent(amount: String, currency: String, description: String, email: String) {

        dispatchers.let {
            viewModelScope.launch(it) {
                //_checkoutLoad.value = CheckoutLoadingEvent.Loading
                val stripePayment = StripePaymentIntent(amount, currency, description, email)

                when (val checkoutResource = checkoutRepo.createPaymentIntent(stripePayment)) {

                    is Resource.Error -> _checkoutLoad.value = CheckoutLoadingEvent.Failure(checkoutResource.errorMsg!!)

                    is Resource.Success -> {

                        val gson = GsonBuilder().setLenient().setPrettyPrinting().create()

                        val jsonString: String
                        val paymentMap = mutableMapOf(
                            "amount" to amount,
                            "currency" to currency,
                            "description" to description,
                            "email" to email
                        )


                        val jsonObjectString = createJsonString(paymentMap)

                        jsonString = gson.toJson(jsonObjectString)

                        _checkoutLoad.value = CheckoutLoadingEvent.Success(jsonString)
                        Log.d("CheckOutViewModel", jsonString)


                    }


                }

            }
        }
    }

    private fun createJsonString(payMap: MutableMap<String, String>): String {
        val jObject = JSONObject()
        var payString = ""

        payMap.forEach{
            jObject.put(it.key, it.value)
            payString = jObject.toString()
        }

        return payString
    }

    fun getPaymentAmount(id: String) {
        dispatchers.let { it ->
            viewModelScope.launch(it) {
                _checkoutLoad.value = CheckoutLoadingEvent.Loading
                when (val checkoutResponse = checkoutRepo.getPaymentIntent(id)) {
                    is Resource.Error -> checkoutResponse.errorMsg?.let {
                        CheckoutLoadingEvent.Failure(
                            it
                        )
                    }

                    is Resource.Success -> {
                        val amount = checkoutResponse.data?.amount
                        _checkoutLoad.value = CheckoutLoadingEvent.Success("$amount")
                    }

                }

            }
        }

    }

    fun updatePaymentIntent(id: String, updatedSubTotal: Double) {
        dispatchers.let { it ->
            viewModelScope.launch(it) {
                _checkoutLoad.value = CheckoutLoadingEvent.Loading
                when (val checkoutResponse = checkoutRepo.updatePaymentIntent(id)) {
                    is Resource.Error -> checkoutResponse.errorMsg?.let {
                        CheckoutLoadingEvent.Failure(
                            it
                        )
                    }

                    is Resource.Success -> {
                        checkoutResponse.data?.amount ?: updatedSubTotal.toInt()
                        _checkoutLoad.value = CheckoutLoadingEvent.Success("${ checkoutResponse.data}")

                        checkoutResponse.data?.let { it1 -> Log.d("CheckOutViewModel", it1.amount) }
                    }

                }

            }
        }

    }

    fun setPaymentIntent(
        updatedAmount: String,
        purchaseDescription: String,
        customerReceiptEmail: String
    ): StripePaymentIntent {
        Stripe.apiKey = BuildConfig.STRIPE_API_KEY

        val fixedString = updatedAmount.replace("$", "")
        val parsedDouble = fixedString.toDouble()
        val doubleMutliplied = parsedDouble * 100.0
        val parsedInt = doubleMutliplied.toInt()

        return StripePaymentIntent(
            parsedInt.toString(),
            "usd",
            purchaseDescription,
            customerReceiptEmail
        )

    }




}