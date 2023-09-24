package com.tritongames.shoppingwishlist.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.viewmodels.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject


@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    //private lateinit var paymentSheetResult: PaymentSheetResult
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var checkOutButton: ImageButton
    private val name: PaymentSheet.BillingDetailsCollectionConfiguration.CollectionMode = PaymentSheet.BillingDetailsCollectionConfiguration.CollectionMode.Automatic
    private val phone: PaymentSheet.BillingDetailsCollectionConfiguration.CollectionMode = PaymentSheet.BillingDetailsCollectionConfiguration.CollectionMode.Automatic
    private val email: PaymentSheet.BillingDetailsCollectionConfiguration.CollectionMode = PaymentSheet.BillingDetailsCollectionConfiguration.CollectionMode.Automatic
    private val addressCollection: PaymentSheet.BillingDetailsCollectionConfiguration.AddressCollectionMode = PaymentSheet.BillingDetailsCollectionConfiguration.AddressCollectionMode.Automatic
    private val attachDefaultsToPaymentMethod: Boolean = false
    private val colorsLight: PaymentSheet.Colors = PaymentSheet.Colors.defaultLight
    private val colorsDark: PaymentSheet.Colors = PaymentSheet.Colors.defaultDark
    private val shapes: PaymentSheet.Shapes = PaymentSheet.Shapes.default
    private val typography: PaymentSheet.Typography = PaymentSheet.Typography(1.0f, null)
    private val primaryButton: PaymentSheet.PrimaryButton = PaymentSheet.PrimaryButton()
    private val listAddress: MutableList<PaymentSheet.Address> = mutableListOf()
    private val allowsDelayedPaymentMethods: Boolean = false
    private val allowsPaymentMethodsRequiringShippingAddress: Boolean = false
    private val appearance: PaymentSheet.Appearance = PaymentSheet.Appearance(colorsLight, colorsDark, shapes, typography, primaryButton)
    private val primaryButtonLabel: String? = null
    private var paymentIntentClientSecret: String? = null
    private lateinit var config: PaymentSheet.CustomerConfiguration
    val contactsVM: ContactsViewModel by viewModels()


    private val billingDetailsCollectionConfiguration: PaymentSheet.BillingDetailsCollectionConfiguration =
        PaymentSheet.BillingDetailsCollectionConfiguration(

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout_layout)
        showPaymentSheet()
      /*  val merchantName: String = "Triton Games"
        val customer: PaymentSheet.CustomerConfiguration = contactsVM.getEphermalKeySecret()
            .let { PaymentSheet.CustomerConfiguration("Triton Games", it.toString()) }
        val googlePay: PaymentSheet.GooglePayConfiguration? = null
        val primaryButtonColor: ColorStateList? = null
        val address: PaymentSheet.Address? = null
        val defaultBillingDetails: PaymentSheet.BillingDetails = PaymentSheet.BillingDetails(
            address,
            email.toString(),
            (contactsVM.getContactFirstNames() + " " + contactsVM.getContactLastNames()).toString(),
            contactsVM.getPhoneNumber().toString()
        )

        val shippingDetails: AddressDetails = AddressDetails(
            (contactsVM.getContactFirstNames() + " " + contactsVM.getContactLastNames()).toString(),
            address,
            contactsVM.getPhoneNumber().toString(),
            false
        )




       val contactEmail =  contactsVM.getContactEmails()*/
       /* for(i in 0..contactsVM.getAddress().size)
        {
            listAddress.addAll(
                listOf(
                    PaymentSheet.Address(
                contactsVM.getAddress().get(i).City,
                contactsVM.getAddress().get(i).Country,
                contactsVM.getAddress().get(i).Street_Address,
                null,
                contactsVM.getAddress().get(i).Zip,
                contactsVM.getAddress().get(i).State
                    )
                )
            )
        }

        val address: PaymentSheet.Address = PaymentSheet.Address(

            listAddress.toString()
        )*/


      //  val secret = contactsVM.getClientSecret()[0]


        checkOutButton = findViewById(R.id.checkOutButton)

        checkOutButton.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                paymentIntentClientSecret?.let { paymentSheet.presentWithPaymentIntent(it, PaymentSheet.Configuration("Triton Games", config)) }

            }


        })
        paymentSheet = PaymentSheet(this@CheckoutActivity, ::onPaymentSheetResult)

    }


    private fun onPaymentSheetResult( paymentSheetResult: PaymentSheetResult) {

        when(paymentSheetResult){
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(this@CheckoutActivity, "Payment sheet was cancelled", Toast.LENGTH_SHORT).show()

            }
            is PaymentSheetResult.Failed-> {

                Toast.makeText(this@CheckoutActivity, "Payment sheet failed: ${paymentSheetResult.error}", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Completed -> {
                Toast.makeText(this@CheckoutActivity, "Payment sheet was successful", Toast.LENGTH_SHORT).show()
                showPaymentSheet ()
            }

            else -> {}
        }

    }

    private fun showPaymentSheet(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://irfanz0.github.io/ShoppingWishList/"

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, url, object:
            Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    try{
                        val jsonObject = response?.let { JSONObject(it) }
                        if (jsonObject != null) {
                            config = PaymentSheet.CustomerConfiguration(
                                jsonObject.getString("customer"),
                                jsonObject.getString("ephermalKey")
                            )
                            paymentIntentClientSecret = jsonObject.getString("paymentIntent")
                            PaymentConfiguration.init(applicationContext, jsonObject.getString("publishableKey"))
                        }
                    }

                    catch(e: JSONException){
                        e.printStackTrace()
                    }

                }

            }, object :
                Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    if (error != null) {
                        error.printStackTrace()
                    }

                }
            }) {
            override fun getParams(): Map<String, String>? {
                val paramV: MutableMap<String, String> = HashMap()
                paramV["authKey"] = "abc"
                return paramV
            }
        }
        queue.add(stringRequest)
    }



   
   
}