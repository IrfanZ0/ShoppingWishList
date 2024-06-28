package com.tritongames.shoppingwishlist.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore
import com.tritongames.loginmap.ui.theme.ShoppingWishListTheme
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.models.firebase.FirebaseAuthImpl
import com.tritongames.shoppingwishlist.data.viewmodels.FireBaseSignInViewModel
import com.tritongames.shoppingwishlist.data.viewmodels.FireBaseStorageViewModel
import com.tritongames.shoppingwishlist.data.viewmodels.PurchaserViewModel


class CreateNewUserRegistration: ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this@CreateNewUserRegistration)

        setContent {
            val purchaserVM: PurchaserViewModel by viewModels()
            ShowRegistrationPage(purchaserVM)

        }
    }

    @Composable
    fun ShowRegistrationPage(purchaserVM: PurchaserViewModel) {
        // Bottom Bar
        var darkModeOn by remember { mutableStateOf(false) }
        var colorPickerOpen by remember { mutableStateOf(false) }


        Scaffold(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically),


            bottomBar = {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = { purchaserVM.changeDayNightMode(!darkModeOn) }) {
                            Icon(
                                imageVector = getImageVector(darkModeOn),
                                contentDescription = null
                            )
                            if (purchaserVM.dayNightState.value) {
                                darkModeOn = purchaserVM.dayNightState.value
                                ShoppingWishListTheme(darkModeOn, true) {
                                    ShowRegistrationPage(purchaserVM)
                                }
                            }
                            else {
                                darkModeOn = purchaserVM.dayNightState.value
                                ShoppingWishListTheme(darkModeOn, true) {
                                    ShowRegistrationPage(purchaserVM)
                                }

                            }



                        }
                        IconButton(onClick = { colorPickerOpen = true }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.color_palette),
                                contentDescription = "Pick colors for theme"
                            )
                            if (colorPickerOpen) {
                                OpenColorPickerWindow(colorPickerOpen, purchaserVM)
                            }

                        }
                    }
                )


            }
        ) {

            val fbStorageVM = FireBaseStorageViewModel()
            val context = LocalContext.current
            val purchaserMap = hashMapOf<String, String>()
            val image by remember { mutableStateOf("")}
            var firstName by remember { mutableStateOf("") }
            var lastName by remember { mutableStateOf("") }
            var address by remember { mutableStateOf("") }
            var city by remember { mutableStateOf("") }
            var purchaserState by remember { mutableStateOf("") }
            var zipCode by remember {mutableStateOf("")}
            var email by remember { mutableStateOf("") }
            var userName by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var retypePassword by remember { mutableStateOf("") }


            Column( modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                Text("Welcome to registration page")

                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                purchaserMap["First Name"] = firstName

                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    singleLine = true,
                    label = { Text("Last Name") },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                purchaserMap["Last Name"] = lastName

                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                purchaserMap["Address"] = address

                TextField(
                    value = city,
                    onValueChange = { city = it },
                    singleLine = true,
                    label = { Text("City") },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                purchaserMap["City"] = city

                TextField(
                    value = purchaserState,
                    onValueChange = { purchaserState = it },
                    singleLine = true,
                    label = { Text("State") },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                purchaserMap["State"] = purchaserState

                TextField(
                    value = zipCode,
                    onValueChange = { zipCode = it },
                    singleLine = true,
                    label = { Text("Zip Code") },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    label = { Text("Email") },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                purchaserMap["Email"] = email

                TextField(
                    value = userName,
                    onValueChange = { userName = it },
                    singleLine = true,
                    label = { Text("User Name") },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                purchaserMap["User Name"] = userName

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation('*'),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                purchaserMap["Password"] = password

                TextField(
                    value = retypePassword,
                    onValueChange = { retypePassword = it },
                    singleLine = true,
                    label = { Text("Retype Password") },
                    visualTransformation = PasswordVisualTransformation('*'),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                purchaserMap["Retype Password"] = retypePassword

                Row(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                        .wrapContentHeight(Alignment.Bottom)
                        .padding(it),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start

                ) {


                    ElevatedButton(onClick = { register(email, password, purchaserMap, context) }) {
                        Text("Register")
                    }

                }
            }
        }

    }



    @Composable
    fun OpenColorPickerWindow(colorPickerOpen: Boolean, purchaserVM: PurchaserViewModel) {
        val popupWidth = 500.dp
        val popupHeight = 500.dp
        val cyanColor = Color.Cyan
        var colorWindowOpen = colorPickerOpen

        Popup(
            // on below line we are adding
            // alignment and properties.
            alignment = Alignment.TopCenter,
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = colorWindowOpen,
                dismissOnClickOutside = colorWindowOpen,
                clippingEnabled = false
            )

        ) {

            Box(
                // adding modifier to it.
                Modifier
                    .size(popupWidth, popupHeight)
                    .padding(top = 5.dp)
                    // on below line we are adding background color
                    .background(cyanColor, RoundedCornerShape(10.dp))
                    // on below line we are adding border.
                    .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(horizontal = 20.dp),
                    // on below line we are adding horizontal and vertical
                    // arrangement to it.
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text("Customize the colors for light mode and dark mode")
                    Text("Light Mode")
                    Row(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .wrapContentHeight(Alignment.CenterVertically)
                    ) {
                        var lightModeTextColorSelected by remember { mutableStateOf(false) }
                        var lightModeTextColor by remember { mutableStateOf(Color.White) }

                        Text("Text Color:")

                        FilledTonalButton(
                            onClick = {
                                lightModeTextColorSelected = !lightModeTextColorSelected
                            },
                            colors = ButtonColors(
                                lightModeTextColor,
                                Color.Unspecified,
                                Color.Unspecified,
                                Color.Unspecified
                            )
                        ) {
                            if (lightModeTextColorSelected) {
                                lightModeTextColor =
                                    getLightModeTextColor(lightModeTextColorSelected)

                            }
                        }

                    }

                    Row(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .wrapContentHeight(Alignment.CenterVertically)
                    ) {
                        var lightModeBackgroundColorSelected by remember { mutableStateOf(false) }
                        var lightModeBackgroundColor by remember { mutableStateOf(Color.White) }


                        Text("Background Color:")

                        FilledTonalButton(
                            onClick = {
                                lightModeBackgroundColorSelected =
                                    !lightModeBackgroundColorSelected
                            },
                            colors = ButtonColors(
                                lightModeBackgroundColor,
                                Color.Unspecified,
                                Color.Unspecified,
                                Color.Unspecified
                            )
                        ) {
                            if (lightModeBackgroundColorSelected) {
                                lightModeBackgroundColor =
                                    getLightModeBackgroundColor(lightModeBackgroundColorSelected)

                            }
                        }

                    }

                    Text("Dark Mode")

                    Row(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .wrapContentHeight(Alignment.CenterVertically)
                    ) {
                        var darkModeTextColorSelected by remember { mutableStateOf(false) }
                        var darkModeTextColor by remember { mutableStateOf(Color.White) }


                        Text("Text Color:")

                        FilledTonalButton(
                            onClick = {
                                darkModeTextColorSelected = !darkModeTextColorSelected
                            },
                            colors = ButtonColors(
                                darkModeTextColor,
                                Color.Unspecified,
                                Color.Unspecified,
                                Color.Unspecified
                            )
                        ) {
                            if (darkModeTextColorSelected) {
                                darkModeTextColor =
                                    getDarkModeTextColor(darkModeTextColorSelected)

                            }
                        }

                    }

                    Row(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .wrapContentHeight(Alignment.CenterVertically)
                    ) {
                        var darkModeBackgroundColorSelected by remember { mutableStateOf(false) }
                        var darkModeBackgroundColor by remember { mutableStateOf(Color.White) }


                        Text("Background Color:")

                        FilledTonalButton(
                            onClick = {
                                darkModeBackgroundColorSelected =
                                    !darkModeBackgroundColorSelected
                            },
                            colors = ButtonColors(
                                darkModeBackgroundColor,
                                Color.Unspecified,
                                Color.Unspecified,
                                Color.Unspecified
                            )
                        ) {
                            if (darkModeBackgroundColorSelected) {
                                darkModeBackgroundColor =
                                    getDMBackgroundColor(darkModeBackgroundColorSelected)

                            }
                        }

                    }

                }


            }


        }


    }

    @Composable
    fun getDMBackgroundColor(darkModeBackgroundColorSelected: Boolean): Color {
        var dmBackgroundColor = Color.Unspecified

        if (darkModeBackgroundColorSelected) {
            dmBackgroundColor = pickColor(darkModeBackgroundColorSelected)

        }
        return dmBackgroundColor
    }

    @Composable
    fun getDarkModeTextColor(darkModeTextColorSelected: Boolean): Color {
        var dmTextColor = Color.Unspecified


        if (darkModeTextColorSelected) {
            dmTextColor = pickColor(darkModeTextColorSelected)

        }

        return dmTextColor
    }

    @Composable
    fun getLightModeBackgroundColor(lightModeBackgroundColorSelected: Boolean): Color {
        var lmBackgroundColor = Color.Unspecified


        if (lightModeBackgroundColorSelected) {
            lmBackgroundColor = pickColor(lightModeBackgroundColorSelected)

        }

        return lmBackgroundColor

    }


    @Composable
    fun pickColor(colorSelected: Boolean): Color {
        var colorPicked: Color by remember { mutableStateOf(Color.White) }
        var selectedColor = colorSelected
        val popUpWidth = 500.dp
        val popUpHeight = 500.dp
        val cyanColor = Color.Cyan

        val colors = mutableListOf(
            Color.Black,
            Color.Cyan,
            Color.Blue,
            Color.Gray,
            Color.Green,
            Color.DarkGray,
            Color.LightGray,
            Color.White,
            Color.Yellow
        )

        Box(
            modifier =
            Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
        ) {
            Popup(
                // on below line we are adding
                // alignment and properties.
                alignment = Alignment.TopCenter,
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = colorSelected,
                    dismissOnClickOutside = colorSelected,
                    clippingEnabled = false
                )

            ) {
//                Box(
//                    modifier =
//                    Modifier
//                        .wrapContentWidth(Alignment.CenterHorizontally)
//                        .wrapContentHeight(Alignment.CenterVertically)
//                        .size(popUpWidth, popUpHeight)
//                        .background(cyanColor, RoundedCornerShape(10.dp))
//                        // on below line we are adding border.
//                        .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
//
//                ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(colors.size),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically),
                    state = rememberLazyGridState(),
                    verticalArrangement = Arrangement.Center
                ) {

                    items(colors.size) {
                        Button(
                            onClick = {
                                colorPicked = colors[it]
                            },
                            modifier = Modifier
                                .wrapContentWidth(Alignment.CenterHorizontally)
                                .wrapContentHeight(Alignment.CenterVertically),
                            colors = ButtonColors(
                                containerColor = colors[it],
                                Color.Unspecified,
                                Color.Unspecified,
                                Color.Unspecified
                            ),
                        ) {

                        }

                    }
                }


            }

        }



        return colorPicked

    }

    @Composable
    fun getLightModeTextColor(lightModeTextColorSelected: Boolean): Color {
        var lmTextColor = Color.Unspecified

        if (lightModeTextColorSelected) {
            lmTextColor = pickColor(lightModeTextColorSelected)

        }

        return lmTextColor
    }


    fun register(
        email: String,
        password: String,
        purchaserMap: HashMap<String, String>,
        context: Context
    ) {
        val fbAuthImpl = FirebaseAuthImpl()
        val fbSignInVM = FireBaseSignInViewModel(fbAuthImpl)
        fbSignInVM.updateEmail(email)
        fbSignInVM.updatePassword(password)
        if (fbSignInVM.onSignUpClick()) {

            val fireStore = Firebase.firestore
            fireStore.collection("purchasers").document(email)
                .set(purchaserMap)
            Toast.makeText(
                context,
                "Sign up was successful. Welcome ${
                    purchaserMap["First Name"].plus(" ").plus(purchaserMap["Last Name"])
                }",
                Toast.LENGTH_SHORT
            ).show()
            val goBackToLoginPageIntent = Intent(context, MainActivity::class.java)
            startActivity(goBackToLoginPageIntent)
        } else {
            Toast.makeText(
                context,
                "There was an error registering please try again",
                Toast.LENGTH_SHORT
            ).show()
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



    @Preview
    @Composable
    fun CreateNewUserPreview() {

        // Bottom Bar
        val purchaserVM: PurchaserViewModel by viewModels()
        var darkModeOn by remember { mutableStateOf(false) }
        var colorPickerOpen by remember { mutableStateOf(false) }
        val fbStorageVM = FireBaseStorageViewModel()
        val context = LocalContext.current
        val purchaserMap = hashMapOf<String, String>()
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var city by remember { mutableStateOf("") }
        var purchaserState by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var userName by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var retypePassword by remember { mutableStateOf("") }
        val scrollState = rememberScrollState(0)
        val purchaserInfo = mutableListOf(
            firstName,
            lastName,
            address,
            city,
            purchaserState,
            email,
            userName,
            password,
            retypePassword
        )

        Scaffold(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically),
           bottomBar = {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = { darkModeOn = true }) {
                            Icon(
                                imageVector = getImageVector(darkModeOn),
                                contentDescription = null
                            )

//                            ShoppingWishListTheme(darkModeOn, true) {
//                                ShowRegistrationPage()
//                            }

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
        )
        { innerPadding ->

            Box( modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Here is where you can create and/or erase a profile.")
                    Text("You can also select a temporary icon for your profile.")
                    Text("Turning on/off between light and dark modes can be saved for setting the mood and readability.")
                    Text("You can customize the color scheme for light and dark modes")
                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        modifier = Modifier.size(10.dp),
                        label = { Text("First Name") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    TextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        modifier = Modifier.size(10.dp),
                        label = { Text("Last Name") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    TextField(
                        value = address,
                        onValueChange = { address = it },
                        modifier = Modifier.size(10.dp),
                        label = { Text("Address") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        modifier = Modifier.size(10.dp),
                        label = { Text("City") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    TextField(
                        value = purchaserState,
                        onValueChange = { purchaserState = it },
                        modifier = Modifier.size(10.dp),
                        label = { Text("State") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.size(10.dp),
                        label = { Text("Email") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    TextField(
                        value = userName,
                        onValueChange = { userName = it },
                        modifier = Modifier.size(10.dp),
                        label = { Text("User Name") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.size(10.dp),
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation('*'),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    TextField(
                        value = retypePassword,
                        onValueChange = { retypePassword = it },
                        modifier = Modifier.size(10.dp),
                        label = { Text("Retype Password") },
                        visualTransformation = PasswordVisualTransformation('*'),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Text("Choose your temporary icon, you may change it after logging in.")
                    fbStorageVM.GetPublicImages()
                    var clear = false
                    ElevatedButton(onClick = { register(
                        email,
                        password,
                        purchaserMap,
                        context
                    ) }) {
                        Text("Register")
                    }

                }

            }


            purchaserMap["First Name"] = firstName
            purchaserMap["Last Name"] = lastName
            purchaserMap["Address"] = address
            purchaserMap["City"] = city
            purchaserMap["State"] = purchaserState
            purchaserMap["Email"] = email
            purchaserMap["User Name"] = userName
            purchaserMap["Password"] = password
            purchaserMap["Retype Password"] = retypePassword


        }
        if (colorPickerOpen) {
            OpenColorPickerWindow(colorPickerOpen, purchaserVM)
        }


    }
}














