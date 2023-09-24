package com.tritongames.shoppingwishlist.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.viewmodels.ContactsViewModel
import com.tritongames.shoppingwishlist.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var eMailTextBox: EditText
    private lateinit var passWordTextBox: EditText
    private lateinit var loginButton: Button
    private lateinit var createNewButton: Button
    private lateinit var progressBar: ProgressBar
    private val contactsVM: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eMailTextBox = binding.editTextTextEmailAddress
        passWordTextBox = binding.editTextPassword

        progressBar = binding.progressBar
        progressBar.visibility = View.INVISIBLE

        loginButton = binding.loginButton

        loginButton.setOnClickListener(this)


        createNewButton = binding.createNew

        createNewButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.createNew ->{
                val createNewUser = Intent(this, CreateNewUserLogin::class.java)
                startActivity(createNewUser)

            }
            R.id.loginButton ->{

                if (checkUserEmail(contactsVM, eMailTextBox.text) && !checkUserPassword(
                        contactsVM,
                        passWordTextBox.text
                    )
                ){
                    Toast.makeText (applicationContext, "Login invalid, please check your password", Toast.LENGTH_SHORT).show()
                    passWordTextBox.text.clear()
                }
                else if (!checkUserEmail(contactsVM, eMailTextBox.text) && checkUserPassword(
                        contactsVM,
                        passWordTextBox.text
                    )){
                    Toast.makeText(applicationContext, "Login invalid, please check your email", Toast.LENGTH_SHORT).show()
                    eMailTextBox.text.clear()

                }
                else{
                    Toast.makeText(applicationContext, "Login valid, welcome in", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.VISIBLE
                    val goToShop = Intent(this, Shop::class.java)
                    startActivity(goToShop)
                }
            }
        }
    }
}

    private fun checkUserPassword(contactsVM: ContactsViewModel, text_password: Editable?): Boolean {
//        val passWordList = mutableListOf<String>()
//        passWordList.addAll(contactsVM.getContactPasswords())
        var passWordFound = false
        for(passWord in contactsVM.getContactPasswords()){
            passWordFound = passWord == text_password.toString()
        }

        return passWordFound
    }

    private fun checkUserEmail(contactsVM: ContactsViewModel, text_email: Editable?): Boolean {
//        val emailList = mutableListOf<String>()
//        emailList.addAll(contactsVM.getContactEmails())
        var emailFound = false

        val email: MutableList<String>? = contactsVM.getContactEmails()
        if (email != null) {
            emailFound =  email.contains(text_email.toString())
        }
            Log.d("Main Activity", emailFound.toString())


        return emailFound

    }
