package com.tritongames.shoppingwishlist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tritongames.shoppingwishlist.R

class CreateNewUserLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_new_login)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.createNewLoginFragment, CreateNewUserLoginFragment.newInstance())
                .commit()

        }
    }
}