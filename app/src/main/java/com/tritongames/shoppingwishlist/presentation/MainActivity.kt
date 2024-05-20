package com.tritongames.shoppingwishlist.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.tritongames.shoppingwishlist.BuildConfig
import com.tritongames.shoppingwishlist.data.models.firebase.FirebaseAuthImpl
import com.tritongames.shoppingwishlist.data.viewmodels.FireBaseSignInViewModel
import com.tritongames.shoppingwishlist.presentation.ui.theme.ShoppingWishListTheme
import com.tritongames.shoppingwishlist.util.FireBaseSignInViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fbOptionsBuilder = FirebaseOptions.Builder()
        fbOptionsBuilder
            .setApiKey(BuildConfig.FIREBASE_API_KEY)
            .setApplicationId("com.tritongames.loginregister")
            .setProjectId("loginregister-f8641")
            .setStorageBucket("loginregister-f8641.appspot.com")

        FirebaseApp.initializeApp(this, fbOptionsBuilder.build())


        setContent {
            ShoppingWishListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val fbAuthImpl = FirebaseAuthImpl()
                    val fbSignInVM = ViewModelProvider(this, FireBaseSignInViewModelFactory(fbAuthImpl))[FireBaseSignInViewModel::class.java]
                    LoginShop(fbSignInVM)
                }
            }
        }
    }
}


@Composable
fun LoginShop(fbSignInVM: FireBaseSignInViewModel) {
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    val loginError by remember { mutableStateOf(false)}
    var registerClicked by remember { mutableStateOf(false)}
    FirebaseAuthImpl()

    val context = LocalContext.current
    Column(modifier =
    Modifier
        .wrapContentWidth(Alignment.CenterHorizontally)
        .wrapContentHeight(Alignment.CenterVertically)
    ) {
        Text(
            text = "Welcome to Shopping Wish List - Login.  This is just a login test",
        )
        Row(modifier =
        Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)

        ) {

        }

        Row(modifier =
        Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
        ) {
            Column(modifier =
            Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.End
            ) {
                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        fbSignInVM.updateEmail(it)

                    },
                    label = { Text("Email") },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = loginError
                )


                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        fbSignInVM.updatePassword(it)
                    },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation('*'),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = loginError
                )


            }

        }

        Row(modifier =
        Modifier
            .wrapContentWidth(Alignment.End)
            .wrapContentHeight(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.End
        ) {
            ElevatedButton(onClick = { registerClicked = true }) {
                Text("Register")
            }

            if (registerClicked) {
                val goToRegisterPage = Intent(context, CreateNewUserRegistration::class.java)
                startActivity(context, goToRegisterPage, null)
            }

            ElevatedButton(onClick = { checkLogin(fbSignInVM, context) }) {
                Text("Login")
            }
        }


    }
}



fun  checkLogin(
    fbSignInVM: FireBaseSignInViewModel,
    context: Context,
){
    if (fbSignInVM.onSignInClick()) {
        Toast.makeText(context, "Successful Login. Time to shop", Toast.LENGTH_SHORT).show()
        val goToShopIntent = Intent(context, Shop::class.java)
        startActivity(context, goToShopIntent, null)

    }
    else {
        Toast.makeText(context, "Unsuccessful Login. Please try again or register a new account", Toast.LENGTH_SHORT).show()

    }


}

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true)
@Composable
fun LoginShopPreview() {
    ShoppingWishListTheme {
        val fbAuthImpl = FirebaseAuthImpl()
        val fbSignInVM = FireBaseSignInViewModel(fbAuthImpl)
        LoginShop(fbSignInVM)


    }
}
