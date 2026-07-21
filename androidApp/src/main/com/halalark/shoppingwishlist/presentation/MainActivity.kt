package com.halalark.shoppingwishlist.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halalark.shoppingwishlist.shoppingwishlist.CreateNewUserRegistration
import com.halalark.shoppingwishlist.shoppingwishlist.MapsActivity
import com.tritongames.shoppingwishlist.data.models.firebase.purchaserdata.PurchaserData
import com.tritongames.shoppingwishlist.data.repository.firebase.Purchaser
import com.tritongames.shoppingwishlist.data.repository.firebase.Recipient
import com.tritongames.shoppingwishlist.data.viewmodels.UserDataViewModel
import com.tritongames.shoppingwishlist.ui.theme.ShoppingWishListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userDataVM: UserDataViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var checkLogin by remember { mutableStateOf(false) }
            ShoppingWishListTheme {
                Column(modifier = Modifier.padding(16.dp)) {
                    SayHello()
                    Registration()
                    LoginUI(userDataVM)
                }

            }
        }
    }
}

@Composable
fun SayHello() {
    Text(
        text = "Hello there!  Please select your role below and press done to continue to registration."
    )
}

@Composable
fun Registration(){
    Row(
        modifier = Modifier.padding(10.dp).fillMaxWidth(1f),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        val context = LocalContext.current

        Button(onClick = {
            val registerIntent = Intent(context, CreateNewUserRegistration::class.java)
            registerIntent.putExtra("Role", "Purchaser")
            context.startActivity(registerIntent)
        }
        ) {
            Text(text = "Purchaser")
        }
        Button(onClick = {
            val registerIntent = Intent(context, CreateNewUserRegistration::class.java)
            registerIntent.putExtra("Role", "Recipient")
            context.startActivity(registerIntent)
        }
        ) {
            Text(text = "Recipient")
        }
    }
}

@Composable
fun ShowRegistrationPage(role: String) {
    val context = LocalContext.current
    val registerIntent = Intent(context, CreateNewUserRegistration::class.java)
    registerIntent.putExtra("Role", role)
    context.startActivity(registerIntent)
}


//@Composable
//fun CheckLogin(userDataVM: UserDataViewModel) {
//    val signInStatus by userDataVM.loginState.collectAsState()
//    val purchaserData by userDataVM.purchaserDataState.collectAsState()
//
//    CheckLogin(
//        signInStatus = signInStatus,
//        purchaserData = purchaserData,
//        onLoginClick = { email, password ->
//            userDataVM.updateEmail(email)
//            userDataVM.updatePassword(password)
//            userDataVM.getPurchaserData()
//        }
//    )
//}

//@Composable
//fun CheckLogin(
//    signInStatus: SignInDetails,
//    purchaserData: List<com.tritongames.com.halalark.shoppingwishlist.data.models.firebase.purchaserdata.PurchaserData>,
//    onLoginClick: (String, String) -> Unit
//) {
//    LoginUI(
//        onLoginClick = onLoginClick,
//        isLoading = signInStatus is SignInDetails.Loading
//    )
//
//    // The Login composable handles navigation via LaunchedEffect
//    Login(signInStatus, purchaserData.toMutableList())
//}

@Composable
fun LoginUI(userDataVM: UserDataViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    var pDataList by remember { mutableStateOf<MutableList<PurchaserData>>(mutableListOf()) }
    var loginIsClicked by remember {mutableStateOf(false)}


    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }

        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation('*'),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

        )

        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    userDataVM.updateEmail(email)
                    userDataVM.updatePassword(password)
                    userDataVM.getPurchaserData()
                    loginIsClicked = true


                } else {
                    Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                }
            },

        ) {
            Text(text = "Login")
        }

        if (loginIsClicked){
            pDataList = userDataVM.purchaserDataState.collectAsState().value
            Login(pDataList)
        }
    }
}



@Composable
fun Login(
       purchaserData: MutableList<PurchaserData>
) {

    val context = LocalContext.current

    LaunchedEffect(purchaserData) {
            // Wait for data to be populated if it's currently the default empty object
            val userData = purchaserData.firstOrNull()
            if (userData != null && userData.email.isNotEmpty()) {
                val goToMapIntent = Intent(context, MapsActivity::class.java).apply {
                    putExtra("Email", userData.email)
                    putExtra("First Name", userData.firstName)
                    putExtra("Last Name", userData.lastName)
                    putExtra("Address", userData.address)
                    putExtra("City", userData.city)
                    putExtra("State", userData.purchaserState)
                    putExtra("Zip", userData.zipCode)
                    putExtra("Phone", userData.phone)
                    putExtra("Image", userData.purchaserImage)
                    putExtra("Location", userData.location)
                }
                context.startActivity(goToMapIntent)
            }
    }

}



@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    val pDataRepo = Purchaser()
    val rDataRepo = Recipient()
    val userDataVM = UserDataViewModel(pDataRepo, rDataRepo)

    ShoppingWishListTheme(
        darkTheme = true,
        dynamicColor = false
            ){
        Column {
            SayHello()
            Registration()
            LoginUI(userDataVM)
        }

    }
}
