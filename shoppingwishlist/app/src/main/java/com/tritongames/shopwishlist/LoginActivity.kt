package com.tritongames.shopwishlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.tritongames.shopwishlist.databinding.ActivityLoginBinding
import com.tritongames.shopwishlist.di.AppModule
import com.tritongames.shopwishlist.presentation.DataStoreViewModel
import com.tritongames.shopwishlist.util.AccessToken
import com.tritongames.shopwishlist.util.LoginService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class LoginActivity (): AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var loginButton: Button
    lateinit var returnButton: Button
    private val clientID = "f52e2ae4fbee858efe1e"
    private val clientSecret = "a506867a8307a618472998a9b1f2362afd510c6c"
    private val redirectUri = "com.tritongames.shopwishlist://callback"
    private val TOKEN_VALUE = "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"
   // private val BASEURL =  "https://github.com/login/oauth/authorize?client_id=${clientID}&scope=repo&redirect_uri=$redirectUri"
    private val dataStoreVM: DataStoreViewModel by viewModels()
    private val key = "Token"




    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginButton = binding.loginButton

        val builder: Uri.Builder = Uri.Builder()
        builder.scheme("https")
            .authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", "f52e2ae4fbee858efe1e")
            .appendQueryParameter("scope", "repo")
            .appendQueryParameter("redirect_uri", "com.tritongames.shopwishlist://callback")

        val baseUrl = builder.build().toString()

        loginButton.setOnClickListener {
            val login = Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl))
            startActivity(login)
        };

        returnButton = binding.returnButton!!

        returnButton.setOnClickListener {
            val returnToShop = Intent(this, Shop::class.java)
            startActivity(returnToShop)
        }


    }

    override fun onResume() {
        super.onResume()

        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        val uri = intent.data
        if (uri != null && uri.toString().startsWith(redirectUri.toString(),0, true)) {
            // use the parameter your API exposes for the code (mostly it's "code")
            val code = uri.getQueryParameter("code")
            if (code != null) {
                val builder = Retrofit.Builder()
                    .baseUrl("https://api.github.com/IrfanZ0/")
                    .addConverterFactory(GsonConverterFactory.create())

                val retrofit = builder.build()
                val loginService = retrofit.create(LoginService::class.java)

                val accessTokenCall: Call<AccessToken?> = loginService.getAccessToken(code, "com.tritongames.shopwishlist://callback","f52e2ae4fbee858efe1e", "a506867a8307a618472998a9b1f2362afd510c6c" )
                accessTokenCall.enqueue(object : Callback<AccessToken?>{
                    override fun onResponse(
                        call: Call<AccessToken?>,
                        response: Response<AccessToken?>
                    ) {
                        if (response.isSuccessful){
                            val accessToken = response.body().toString()
                            lifecycleScope.launch {
                                dataStoreVM.write(key, accessToken)
                            }

                        }
                    }

                    override fun onFailure(call: Call<AccessToken?>, t: Throwable) {
                        Toast.makeText(applicationContext.applicationContext, t.stackTrace.toString(), Toast.LENGTH_SHORT).show()

                    }

                })


            } else if (uri.getQueryParameter("error") != null) {
             Toast.makeText(applicationContext.applicationContext, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }






}

