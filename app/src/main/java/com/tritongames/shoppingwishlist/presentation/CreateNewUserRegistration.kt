package com.tritongames.shoppingwishlist.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
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


class CreateNewUserRegistration: ComponentActivity() {
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

            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        actions = {
                            IconButton(onClick = { darkModeOn = true }) {
                                Icon(
                                    imageVector = getImageVector(darkModeOn),
                                    contentDescription = null
                                )

                                ShoppingWishListTheme(darkModeOn, true) {

                                }

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
            ) { it ->
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

                Column(
                    modifier = Modifier
                        .padding(it),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text("Welcome to registration page")
                    Text("Here is where you can create and/or erase a profile.")
                    Text("You can also select a temporary icon for your profile.")
                    Text("Turning on/off between light and dark modes can be saved for setting the mood and readability.")
                    Text("You can customize the color scheme for light and dark modes")

                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )

                    TextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )


                    TextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Address") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )


                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("City") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )


                    TextField(
                        value = purchaserState,
                        onValueChange = { purchaserState = it },
                        label = { Text("State") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )


                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )

                    TextField(
                        value = userName,
                        onValueChange = { userName = it },
                        label = { Text("User Name") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )


                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation('*'),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

                    )

                    TextField(
                        value = retypePassword,
                        onValueChange = { retypePassword = it },
                        label = { Text("Retype Password") },
                        visualTransformation = PasswordVisualTransformation('*'),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

                    )

                    Text("Choose your temporary icon, you may change it after logging in.")
                    fbStorageVM.GetPublicImages()

                }

                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                }

                Row(
                    modifier = Modifier
                        .padding(it),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    ElevatedButton(onClick = { register(purchaserMap, context) }) {
                        Text("Register")
                    }
                    ElevatedButton(onClick = { clearAll(purchaserMap, context) }) {
                        Text("Clear")

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

                if (colorPickerOpen) {
                    OpenColorPickerWindow(colorPickerOpen)
                }
            }

        }
        @Composable
        fun OpenColorPickerWindow(colorPickerOpen: Boolean) {
            val popupWidth = 500.dp
            val popupHeight = 500.dp
            val cyanColor = Color.Cyan
            var colorWindowOpen = colorPickerOpen

            Popup(
                // on below line we are adding
                // alignment and properties.
                alignment = Alignment.TopCenter,
                properties = PopupProperties(),
                onDismissRequest = { colorWindowOpen = false }
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
                            .fillMaxSize()
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
                                    !lightModeTextColorSelected
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
                                    !lightModeBackgroundColorSelected
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
                                    !darkModeTextColorSelected
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
                                    !darkModeBackgroundColorSelected
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
                    properties = PopupProperties(),
                    onDismissRequest = { selectedColor = false }
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
                        modifier = Modifier.heightIn(300.dp),
                        state = rememberLazyGridState()
                    ) {

                        items(colors.size) {
                            Button(
                                onClick = { colorPicked = colors[it] },
                                modifier = Modifier.height(70.dp),
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





        fun clearAll(purchaserMap: HashMap<String, String>, context: Context) {
            for (dataItem in purchaserMap) {
                dataItem.setValue("")
            }
            Toast.makeText(context, "All data has been erased", Toast.LENGTH_SHORT).show()

        }

        fun register(
            purchaserMap: HashMap<String, String>,
            context: Context
        ) {
            val fbAuthImpl = FirebaseAuthImpl()
            val fbSignInVM = FireBaseSignInViewModel(fbAuthImpl)
            if (fbSignInVM.onSignUpClick()) {
                fbSignInVM.updateEmail(purchaserMap["Email"].toString())
                fbSignInVM.updatePassword(purchaserMap["Password"].toString())
                val fireStore = Firebase.firestore
                fireStore.collection("purchasers").document(purchaserMap["Email"].toString())
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


        @RequiresApi(Build.VERSION_CODES.S)
        @Preview
        @Composable
        fun CreateNewUserPreview() {

            // Bottom Bar
            var darkModeOn by remember { mutableStateOf(false) }
            var colorPickerOpen by remember { mutableStateOf(false) }

            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        actions = {
                            IconButton(onClick = { darkModeOn = true }) {
                                Icon(
                                    imageVector = getImageVector(darkModeOn),
                                    contentDescription = null
                                )

                                ShoppingWishListTheme(darkModeOn, true) {

                                }

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


            ) {
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

                Column(
                    modifier = Modifier
                        .padding(it),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Here is where you can create and/or erase a profile.")
                    Text("You can also select a temporary icon for your profile.")
                    Text("Turning on/off between light and dark modes can be saved for setting the mood and readability.")
                    Text("You can customize the color scheme for light and dark modes")

                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )

                    TextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )


                    TextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Address") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )


                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("City") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )


                    TextField(
                        value = purchaserState,
                        onValueChange = { purchaserState = it },
                        label = { Text("State") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )


                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )

                    TextField(
                        value = userName,
                        onValueChange = { userName = it },
                        label = { Text("User Name") },
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

                    )


                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation('*'),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

                    )

                    TextField(
                        value = retypePassword,
                        onValueChange = { retypePassword = it },
                        label = { Text("Retype Password") },
                        visualTransformation = PasswordVisualTransformation('*'),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

                    )
                }

                Row(
                    modifier =
                    Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .horizontalScroll(scrollState)
                ) {

                    Text("Choose your temporary icon, you may change it after logging in.")
                    fbStorageVM.GetPublicImages()
                }

                Row(
                    modifier =
                    Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically)
                ) {

                    ElevatedButton(onClick = { register(purchaserMap, context) }) {
                        Text("Register")
                    }
                    ElevatedButton(onClick = { clearAll(purchaserMap, context) }) {
                        Text("Clear")

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

                if (colorPickerOpen) {
                    OpenColorPickerWindow(colorPickerOpen)
                }
            }

        }

}

