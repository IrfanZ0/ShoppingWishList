package com.tritongames.shopwishlist.util

import android.content.Context
import android.widget.Toast
import com.tritongames.shopwishlist.data.ShoppingData
import com.tritongames.shopwishlist.data.ShoppingData.Companion.context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(private val authToken: String) : Interceptor {

    val context: Context? = ShoppingData.context
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val builder: Request.Builder = original.newBuilder()
            .header("Authorization", authToken)
            .header("Accept", "application/json")
        val request: Request = builder.build()
        val response = chain.proceed(request)
        when (response.code) {
            400 -> {
                Toast.makeText(context, "Bad Request", Toast.LENGTH_SHORT).show()
            }
            401 -> {
                Toast.makeText(context, "Unauthorized Error", Toast.LENGTH_SHORT).show()
            }
            403 ->{
                Toast.makeText(context, "Forbidden", Toast.LENGTH_SHORT).show()

            }
            404 ->{
                Toast.makeText(context, "Not Found Error", Toast.LENGTH_SHORT).show()
            }
        }
            return response
    }
}
