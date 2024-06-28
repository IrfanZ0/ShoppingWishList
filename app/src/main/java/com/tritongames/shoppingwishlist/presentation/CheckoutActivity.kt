package com.tritongames.shoppingwishlist.presentation


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.tritongames.shoppingwishlist.BuildConfig
import com.tritongames.shoppingwishlist.data.models.checkout.StripeInterface
import com.tritongames.shoppingwishlist.data.models.checkout.StripePaymentIntent
import com.tritongames.shoppingwishlist.data.viewmodels.CheckOutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    //private lateinit var paymentSheetResult: PaymentSheetResult
    private lateinit var paymentSheet: PaymentSheet
    private val colorsLight: PaymentSheet.Colors = PaymentSheet.Colors.defaultLight
    private val colorsDark: PaymentSheet.Colors = PaymentSheet.Colors.defaultDark
    private val shapes: PaymentSheet.Shapes = PaymentSheet.Shapes.default
    private val typography: PaymentSheet.Typography = PaymentSheet.Typography(1.0f, null)
    private val primaryButton: PaymentSheet.PrimaryButton = PaymentSheet.PrimaryButton()
    private val listAddress: MutableList<PaymentSheet.Address> = mutableListOf()
    private var paymentIntentClientSecret: String = ""
    private var clientEphemeralKey : String = ""
    private var publishableKey : String = ""
    private lateinit var config: PaymentSheet.CustomerConfiguration
    private lateinit var purchaseList: RecyclerView
    private lateinit var db : Firebase
    private lateinit var dbStore: FirebaseFirestore
    private var contactTest : MutableList<String> = mutableListOf()
    private var contactMapTest : MutableMap<String, String> = mutableMapOf()
    private lateinit var composeView : ComposeView
    private  var data : MutableList<String> = mutableListOf()
    private lateinit var numColumns : GridCells
    private var count = 0
    private val TAG = "CheckoutActivity"
    val apiStripeKey = BuildConfig.STRIPE_API_KEY
    @Inject lateinit var stripeApi : StripeInterface
    private lateinit var checkoutViewModel: CheckOutViewModel

    private val billingDetailsCollectionConfiguration: PaymentSheet.BillingDetailsCollectionConfiguration =
        PaymentSheet.BillingDetailsCollectionConfiguration(

        )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkoutViewModel = ViewModelProvider(this)[CheckOutViewModel::class.java]


        data.add("Apples")
        data.add("0.50")
        data.add("Bananas")
        data.add("0.75")
        data.add("Oranges")
        data.add("0.25")
        data.add("SubTotal:")
        data.add("subTotalCalc()")

        db = Firebase
        dbStore = db.firestore

        contactTest.add("0")
        contactTest.add("John")
        contactTest.add("Doe")
        contactTest.add("abcd@gmail.com")
        contactTest.add("818-556-9108")
        contactTest.add("1313 Mockingbird Lane")
        contactTest.add("Transylvannia")
        contactTest.add("Pennsylvania")
        contactTest.add("98640")

        addContacts(contactTest)
        contactMapTest = getContacts()

        count = 0
        composeView  = ComposeView(this, null, 0)
        numColumns = GridCells.Fixed(2)

        setContent {
            ShowInvoice(data)
            ShowPaymentButtons()

        }

        //Updating Payment Intent
        lifecycleScope.launch {
            val formattedDoubleString = subTotalCalc().replace("$", "")
            checkoutViewModel.updatePaymentIntent("pi_3O7IEpF1WVVH8ZyJ0o46smo3", formattedDoubleString.toDouble())
            checkoutViewModel.checkoutLoad.collect { event ->
                when (event) {
                    is CheckOutViewModel.CheckoutLoadingEvent.Failure -> {
                        Log.d("CheckoutActivity", event.errorString)
                    }

                    is CheckOutViewModel.CheckoutLoadingEvent.Success -> {
                        Log.d("CheckoutActivity", event.resultString)


                    }

                    else -> {
                        Unit
                    }
                }
            }
        }


        // create payment intent
        lifecycleScope.launch {
          checkoutViewModel.checkoutLoad.collect {event ->
                when (event){
                    is CheckOutViewModel.CheckoutLoadingEvent.Failure -> {
                        Log.d("CheckoutActivity", event.errorString)
                    }
                    is CheckOutViewModel.CheckoutLoadingEvent.Success -> {
                       // postPaymentIntent(event.resultString)

                    }

                    else -> {Unit}
                }

            }
        }



        paymentSheet = PaymentSheet(this@CheckoutActivity, ::onPaymentSheetResult)

    }

    private fun postPaymentIntent(paymentIntent: StripePaymentIntent) {

         CoroutineScope(Dispatchers.IO).launch {
            val response = stripeApi.createPaymentIntent( paymentIntent)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().setLenient().create()
                    val prettyJson =
                        gson.toJson(JsonParser.parseString(response.body()?.toString()))
                    Log.d("CheckoutActivity", prettyJson)

                } else {
                    Log.e("Retrofit Error", response.code().toString())
                }
            }
        }

    }

    @Composable
    fun ShowInvoice(data : MutableList<String>) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            content = {
                items(data.count()) {
                    Column(modifier = Modifier
                        .padding(0.dp, 4.dp, 0.dp, 4.dp)
                        .aspectRatio(4f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Cyan)
                        .wrapContentWidth(Alignment.CenterHorizontally, false)
                        .wrapContentHeight(Alignment.CenterVertically, false)
                        .drawWithContent {
                            drawContent()
                            drawLine(
                                Brush.linearGradient(
                                    0.0f to Color.Red,
                                    1.0f to Color.Yellow,
                                    2.0f to Color.Cyan,
                                    3.0f to Color.Blue,
                                    4.0f to Color.Green,
                                    5.0f to Color.White
                                ),
                                Offset.Zero,
                                Offset.Infinite,
                                androidx.compose.ui.graphics.drawscope.Stroke.HairlineWidth,

                                )

                        }

                    ){
                        if (it % 2 == 0) {
                            Text(text = data[it], color = Color.White, textAlign = TextAlign.Center)
                        }

                        else {

                            if (data[it] == "subTotalCalc()"){
                                Text(text = subTotalCalc(), color = Color.White, textAlign = TextAlign.Center)
                            }
                            else {
                                Text(text = "$ " + data[it], color = Color.White, textAlign = TextAlign.Center)

                            }
                        }

                    }

                }

            }
        )

    }

    @Preview
    @Composable
    fun ShowInvoicePreview() {
        val i = 1

        data.add("Apples")
        data.add("0.50")
        data.add("Bananas")
        data.add("0.75")
        data.add("Oranges")
        data.add("0.25")
        data.add("SubTotal:")
        data.add("subTotalCalc()")

        LazyVerticalGrid(
            GridCells.Fixed(2),
            content = {
                items(data.count()) {
                    Column(modifier = Modifier
                        .padding(0.dp, 4.dp, 0.dp, 4.dp)
                        .aspectRatio(4f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Cyan)
                        .wrapContentWidth(Alignment.CenterHorizontally, false)
                        .wrapContentHeight(Alignment.CenterVertically, false)
                        .drawWithContent {
                            drawContent()
                            drawLine(
                                Brush.linearGradient(
                                    0.0f to Color.Red,
                                    1.0f to Color.Yellow,
                                    2.0f to Color.Cyan,
                                    3.0f to Color.Blue,
                                    4.0f to Color.Green,
                                    5.0f to Color.White
                                ),
                                Offset.Zero,
                                Offset.Infinite,
                                androidx.compose.ui.graphics.drawscope.Stroke.HairlineWidth,

                                )

                        }

                    ){
                        if (it % 2 == 0) {
                            Text(text = data[it], color = Color.White, textAlign = TextAlign.Center)
                        }

                        else {

                            if (data[it] == "subTotalCalc()"){
                                Text(text = subTotalCalc(), color = Color.White, textAlign = TextAlign.Center)
                            }
                            else {
                                Text(text = "$ " + data[it], color = Color.White, textAlign = TextAlign.Center)

                            }
                        }

                    }



                }

            }
        )

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun ShowPaymentButtons() {
        val fbUserEmail = Firebase.auth.currentUser?.email
        Row(
            modifier =
            Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        )
        {
            ElevatedButton(
                onClick = { showPaymentSheet() },
                modifier =
                Modifier.wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentHeight(Alignment.CenterVertically),
                enabled = true,
                shape = androidx.compose.material3.ButtonDefaults.elevatedShape,
                colors = androidx.compose.material3.ButtonDefaults.elevatedButtonColors(
                    Color.Cyan,
                    Color.White,
                    Color.Gray,
                    Color.DarkGray
                ),
                androidx.compose.material3.ButtonDefaults.buttonElevation(
                    2.dp,
                    1.dp,
                    3.dp,
                    4.dp,
                    0.dp
                ),
                null,
                ButtonDefaults.ContentPadding,
                interactionSource = remember { MutableInteractionSource() }
            )
            {
                Text(text = "Check Out")

            }
            ElevatedButton(
                onClick = {

                    if (fbUserEmail != null) {
                        checkoutViewModel.createNewPaymentIntent(subTotalCalc(), "usd", "Purchase", fbUserEmail)
                    }
                },
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally, false)
                    .wrapContentHeight(Alignment.CenterVertically, false),
                enabled = true,
                shape = androidx.compose.material3.ButtonDefaults.elevatedShape,
                colors = androidx.compose.material3.ButtonDefaults.elevatedButtonColors(
                    Color.Cyan,
                    Color.White,
                    Color.Gray,
                    Color.DarkGray
                ),
                androidx.compose.material3.ButtonDefaults.buttonElevation(
                    2.dp,
                    1.dp,
                    3.dp,
                    4.dp,
                    0.dp
                ),
                null,
                ButtonDefaults.ContentPadding,
                interactionSource = remember { MutableInteractionSource() }
            )
            {
                Text(text = "Save Payment")

            }
        }
        

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Preview
    @Composable
    fun ShowPaymentButtonsPreview() {

        Row(
            modifier =
            Modifier.wrapContentHeight(Alignment.CenterVertically)
                .wrapContentHeight(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom


        )
        {
            ElevatedButton(
                onClick = { showPaymentSheet()},
                modifier =
                Modifier.wrapContentWidth(Alignment.CenterHorizontally, false)
                    .wrapContentHeight(Alignment.CenterVertically, false),
                enabled = true,
                shape = androidx.compose.material3.ButtonDefaults.elevatedShape,
                colors = androidx.compose.material3.ButtonDefaults.elevatedButtonColors(
                    Color.Cyan,
                    Color.White,
                    Color.Gray,
                    Color.DarkGray
                ),
                androidx.compose.material3.ButtonDefaults.buttonElevation(
                    2.dp,
                    1.dp,
                    3.dp,
                    4.dp,
                    0.dp
                ),
                null,
                ButtonDefaults.ContentPadding,
                interactionSource = remember { MutableInteractionSource() }
            )
            {
                Text(text = "Check Out")

            }
            ElevatedButton(
                onClick = { checkoutViewModel.createNewPaymentIntent(subTotalCalc(), "usd", "Test Payment Intent", "iziaulla@gmail.com")

                },
                modifier =
                Modifier.wrapContentWidth(Alignment.CenterHorizontally, false)
                    .wrapContentHeight(Alignment.CenterVertically, false),
                enabled = true,
                shape = androidx.compose.material3.ButtonDefaults.elevatedShape,
                colors = androidx.compose.material3.ButtonDefaults.elevatedButtonColors(
                    Color.Cyan,
                    Color.White,
                    Color.Gray,
                    Color.DarkGray
                ),
                androidx.compose.material3.ButtonDefaults.buttonElevation(
                    2.dp,
                    1.dp,
                    3.dp,
                    4.dp,
                    0.dp
                ),
                null,
                ButtonDefaults.ContentPadding,
                interactionSource = remember { MutableInteractionSource() }
            )
            {
                Text(text = "Save Payment")

            }
        }
    }

   data class ProductInfo(val name: String, val price: String)



    private fun subTotalCalc(): String {
        var parsedDouble: Double = 0.0
        var runningTotal: Double = 0.0
        for (i in 1..data.count() - 2 step 2){
            parsedDouble = data[i].toDoubleOrNull() ?: throw IllegalArgumentException("${data[i]} is not a valid number")
            runningTotal += parsedDouble
        }

        val formattedRunningTotal = String.format("%.2f", runningTotal)

        return "$ $formattedRunningTotal"
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
                //showPaymentSheet ()


                Toast.makeText(this@CheckoutActivity, "Payment sheet was successful", Toast.LENGTH_SHORT).show()

            }

            else -> {}
        }

    }

    private fun showPaymentSheet(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://apis.androidparadise.site/stripe/"

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url, object:
            Response.Listener<String?> {
                override fun onResponse(response: String?) {

                    try{
                        val jsonObject = response?.let { JSONObject(it) }
                        if (jsonObject != null) {
                            config = PaymentSheet.CustomerConfiguration(
                                jsonObject.getString("customer"),
                                jsonObject.getString("ephemeralKey")
                            )
                            paymentIntentClientSecret = jsonObject.getString("paymentIntent")
                            PaymentConfiguration.init(applicationContext, jsonObject.getString("publishableKey"))

                            paymentIntentClientSecret
                                .let { paymentSheet.presentWithPaymentIntent(it, PaymentSheet.Configuration("Triton Games", config)) }
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


    private fun addContacts (contactInfo : MutableList<String>) {
       for (i in contactInfo) {
           val contact = hashMapOf<String, String>(
               "id" to contactInfo[0],
               "first name" to contactInfo[1],
               "last name" to contactInfo[2],
               "email" to contactInfo[3],
               "phone" to contactInfo[4],
               "address" to contactInfo[5],
               "city" to contactInfo[6],
               "state" to contactInfo[7],
               "zip code" to contactInfo[8],

           )

           dbStore.collection("contacts")
               .add(contact)
               .addOnSuccessListener { documentReference -> Toast.makeText(this, "Document added with id: ${documentReference.id}", Toast.LENGTH_SHORT).show() }
               .addOnFailureListener {e -> Toast.makeText(this, "Error adding document: $e", Toast.LENGTH_SHORT).show()}
       }
    }

    private fun getContacts() : MutableMap<String, String> {
        var contactsList : MutableMap<String, String> = mutableMapOf()
        dbStore.collection("contacts")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Toast.makeText(this, "${document.id} => ${document.data}", Toast.LENGTH_SHORT).show()
                    contactsList = document.data as MutableMap<String, String>
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting documents :  $exception", Toast.LENGTH_SHORT).show()
            }
        return contactsList
    }
   
   
}