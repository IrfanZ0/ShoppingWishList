package com.tritongames.shoppingwishlist.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.stripe.android.core.injection.WeakMapInjectorRegistry.register
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.repository.firebase.Purchaser
import com.tritongames.shoppingwishlist.data.viewmodels.TAG
import com.tritongames.shoppingwishlist.data.viewmodels.UserDataViewModel
import com.tritongames.shoppingwishlist.ui.theme.ShoppingWishListTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CreateNewUserRegistration: ComponentActivity() {
    private val userDataVM: UserDataViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent {
            ShowRegistrationPage()

        }
    }

    @Composable
    fun ShowRegistrationPage() {
        // Bottom Bar
        var darkModeOn by remember { mutableStateOf(false) }
        var colorPickerOpen by remember { mutableStateOf(false) }
        val chosenRole = remember { intent.getStringExtra("Role") ?: "Recipient" }


        ShoppingWishListTheme(darkModeOn, false) {
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        actions = {
                            IconButton(onClick = { darkModeOn = !darkModeOn }) {
                                Icon(
                                    imageVector = getImageVector(darkModeOn),
                                    contentDescription = null
                                )



                            }
                            IconButton(onClick = { colorPickerOpen = true }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.color_palette),
                                    contentDescription = "Pick colors for theme"
                                )

                            }
                        }

                    )


                }
            ) { padding ->
                val context = LocalContext.current
                var firstName by remember { mutableStateOf("") }
                var lastName by remember { mutableStateOf("") }
                var address by remember { mutableStateOf("") }
                var city by remember { mutableStateOf("") }
                var purchaserState by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var retypePassword by remember { mutableStateOf("") }
                var passwordsNoMatch by remember {mutableStateOf(false)}
                var checkedState by remember { mutableStateOf(false)}
                val firebaseAuth = Firebase.auth

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text("Welcome to registration page")
                    }

                    item {
                        TextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = { Text("First Name") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = { Text("Last Name") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Address") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = city,
                            onValueChange = { city = it },
                            label = { Text("City") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = purchaserState,
                            onValueChange = { purchaserState = it },
                            label = { Text("State") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = password,
                            onValueChange = {
                                password = it
                                 if (!password.contentEquals(retypePassword)){
                                    passwordsNoMatch = true
                                 }
                            },
                            isError = passwordsNoMatch,
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation('*'),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                    }

                    item {
                        TextField(
                            value = retypePassword,
                            onValueChange = {
                                retypePassword = it
                                if (!password.contentEquals(retypePassword)){
                                    passwordsNoMatch = true
                                }
                                            },
                            isError = passwordsNoMatch,
                            label = { Text("Retype Password") },
                            visualTransformation = PasswordVisualTransformation('*'),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                    }



                    item {
                        Row(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            ElevatedButton(onClick = {
                                val registrationMap = hashMapOf(
                                    "First Name" to firstName,
                                    "Last Name" to lastName,
                                    "Address" to address,
                                    "City" to city,
                                    "State" to purchaserState,
                                    "Email" to email,
                                    "Password" to password,
                                    "retypePassword" to retypePassword
                                )
                                if (chosenRole == "Purchaser") {
                                    if (firebaseAuth.currentUser != null
                                        && firebaseAuth.currentUser?.email == email) {
                                        register(registrationMap, hashMapOf(), context, chosenRole)

                                    }
                                    else{
                                        lifecycleScope.launch {
                                            val purchaser = Purchaser()
                                            try{
                                                purchaser.addNewPurchaser(email, password)
                                            }catch(e: Exception){
                                                Log.d(TAG, "There was a problem adding a new purchaser: ${e.message}")
                                            }
                                        }
                                    }
                                } else {
                                    if (firebaseAuth.currentUser != null
                                        && firebaseAuth.currentUser?.email == email) {
                                        register(
                                            registrationMap,
                                            registrationMap,
                                            context,
                                            chosenRole
                                        )
                                    }
                                }
                            }) {
                                Text("Register")
                            }
                            ElevatedButton(onClick = {
                                firstName = ""
                                lastName = ""
                                address = ""
                                city = ""
                                purchaserState = ""
                                email = ""
                                password = ""
                                retypePassword = ""
                                Toast.makeText(context, "All data has been erased", Toast.LENGTH_SHORT).show()
                            }) {
                                Text("Clear")
                            }
                        }
                    }
                }

            }
        }

    }



    fun clearAll(purchaserMap: HashMap<String, String>, context: Context) {
        for (dataItem in purchaserMap) {
            dataItem.setValue("")
        }
        Toast.makeText(context, "All data has been erased", Toast.LENGTH_SHORT).show()

    }

    fun register(
        purchaserMap: HashMap<String, String>,
        recipientMap: HashMap<String, String>,
        context: Context,
        role: String?
    ) {
        when (role){
            "Purchaser" -> {
                userDataVM.setPurchaserData(purchaserMap, context)
            }
            "Recipient" -> {
                userDataVM.setRecipientData(purchaserMap, recipientMap, context)
            }
        }


    }

    @Composable
    fun getImageVector(darkModeOn: Boolean): ImageVector {
        val darkModeImage: ImageVector = if (darkModeOn) {
            ImageVector.vectorResource(id = R.drawable.darkmode)
        } else {
            ImageVector.vectorResource(id = R.drawable.lightmode)

        }
        return darkModeImage
    }


    @RequiresApi(Build.VERSION_CODES.S)
    @Preview
    @Composable
    fun CreateNewUserPreview() {

        // Bottom Bar
        var darkModeOn by remember { mutableStateOf(false) }
        var colorPickerOpen by remember { mutableStateOf(false) }

        com.tritongames.shoppingwishlist.presentation.ui.theme.ShoppingWishListTheme(
            darkModeOn,
            false
        ) {

            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        actions = {
                            IconButton(onClick = { darkModeOn = true }) {
                                Icon(
                                    imageVector = getImageVector(darkModeOn),
                                    contentDescription = null
                                )



                            }
//                            IconButton(onClick = { colorPickerOpen = true }) {
//                                Icon(
//                                    imageVector = ImageVector.vectorResource(R.drawable.color_palette),
//                                    contentDescription = "Pick colors for theme"
//                                )
//
//                            }
                        }

                    )


                }


            ) { padding ->

                val context = LocalContext.current
                val userMap = remember { hashMapOf<String, String>() }
                val userMap2 = remember { hashMapOf<String, String>() }
                var firstName by remember { mutableStateOf("") }
                var lastName by remember { mutableStateOf("") }
                var address by remember { mutableStateOf("") }
                var city by remember { mutableStateOf("") }
                var purchaserState by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var retypePassword by remember { mutableStateOf("") }
                var role by remember { mutableStateOf("Recipient") }
                var checkedState by remember {mutableStateOf(false)}

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text("Welcome to your registration page!")
                    }


                    item {
                        TextField(
                            value = firstName,
                            onValueChange = {
                                firstName = it
                                userMap["firstName"] = firstName
                                            },
                            label = { Text("First Name") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = lastName,
                            onValueChange = {
                                lastName = it
                                userMap["lastName"] = lastName
                                            },
                            label = { Text("Last Name") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = address,
                            onValueChange = {
                                address = it
                                userMap["address"] = address
                                            },
                            label = { Text("Address") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = city,
                            onValueChange = {
                                city = it
                                userMap["city"] = city
                                            },
                            label = { Text("City") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = purchaserState,
                            onValueChange = {
                                purchaserState = it
                                userMap["purchaserState"] = purchaserState
                                            },
                            label = { Text("State") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = email,
                            onValueChange = {
                                email = it
                                userMap["email"] = email
                                            },
                            label = { Text("Email") },
                            visualTransformation = VisualTransformation.None,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }

                    item {
                        TextField(
                            value = password,
                            onValueChange = {
                                password = it
                                userMap["password"] = password
                                            },
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation('*'),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                    }

                    item {
                        TextField(
                            value = retypePassword,
                            onValueChange = {
                                retypePassword = it
                                userMap["retypePassword"] = retypePassword
                                            },
                            label = { Text("Retype Password") },
                            visualTransformation = PasswordVisualTransformation('*'),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                    }

                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Turn on switch if you're a Purchaser.  Leave off if not")
                            Switch(
                                checked = checkedState,
                                onCheckedChange = {
                                    checkedState = it
                                    role = if (it) "Purchaser" else "Recipient"
                                }
                            )
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            ElevatedButton(onClick = { register(userMap, userMap2, context, role) }) {
                                Text("Register")
                            }
                            ElevatedButton(onClick = { clearAll(userMap, context) }) {
                                Text("Clear")
                            }
                        }
                    }
                }


            }

        }

    }
}

