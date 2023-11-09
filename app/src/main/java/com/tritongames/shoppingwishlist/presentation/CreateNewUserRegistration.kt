package com.tritongames.shoppingwishlist.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tritongames.shoppingwishlist.data.models.contacts.ContactResponseItem
import com.tritongames.shoppingwishlist.data.models.contacts.Data
import com.tritongames.shoppingwishlist.data.viewmodels.ContactsViewModel
import com.tritongames.shoppingwishlist.databinding.ActivityCreateNewUserRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class CreateNewUserRegistration : AppCompatActivity() {
    private lateinit var createNewUserBind: ActivityCreateNewUserRegistrationBinding
    private lateinit var firstNameText: EditText
    private lateinit var lastNameText: EditText
    private lateinit var addressText: EditText
    private lateinit var cityText: EditText
    private lateinit var stateText: EditText
    private lateinit var zipText: EditText
    private lateinit var phoneText: EditText
    private lateinit var userNameText: EditText
    private lateinit var passWordText: EditText
    private lateinit var emailText: EditText
    private lateinit var submitButton: Button
    private var data: Data? = null
    private var dataList: List<Data?> = listOf()
    private var contact: ContactResponseItem? = null
    private val contactsVM: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNewUserBind = ActivityCreateNewUserRegistrationBinding.inflate(layoutInflater)
        setContentView(createNewUserBind.root)
        firstNameText = createNewUserBind.firstNameLabelText
        lastNameText = createNewUserBind.lastNameLabelText
        addressText = createNewUserBind.addressLabelText
        cityText = createNewUserBind.cityLabelText
        stateText = createNewUserBind.stateLabelText
        zipText = createNewUserBind.zipLabelText
        phoneText = createNewUserBind.phoneLabelText
        userNameText = createNewUserBind.userNameLabelText
        passWordText = createNewUserBind.passwordLabelText
        emailText = createNewUserBind.emailLabelText
        submitButton = createNewUserBind.submitButton

        lifecycleScope.launch {

            data = Data(
                addressText.text.toString(),
                cityText.text.toString(),
                firstNameText.text.toString(),
                "1",
                lastNameText.text.toString(),
                passWordText.text.toString(),
                stateText.text.toString(),
                userNameText.text.toString(),
                zipText.text.toString(),
                emailText.text.toString(),
                phoneText.text.toString()
            )

            dataList = listOf(data)


            contact = ContactResponseItem(
                null,
                dataList,
                null,
                null,
                null,
                null
            )

            contactsVM.recipientLoad.collect { event ->
                when (event) {
                    is ContactsViewModel.RecipientLoadingEvent.Success -> {
                       Toast.makeText(this@CreateNewUserRegistration, contactsVM.getContactEmails()[0], Toast.LENGTH_SHORT).show()

                    }

                    is ContactsViewModel.RecipientLoadingEvent.Failure -> {
                        Toast.makeText(
                            this@CreateNewUserRegistration,
                             "Failed to retrieve email",
                            Toast.LENGTH_LONG
                        ).show()

                        Toast.makeText(
                            this@CreateNewUserRegistration,
                            "Failed to send data to server",
                            Toast.LENGTH_SHORT
                        ).show()

                    }


                    else -> {}
                }
            }

        }



        submitButton.setOnClickListener (
            object : View.OnClickListener {
                override fun onClick(v: View?) {

                    contact?.let { contactsVM.addContact(it) }

                }

            })
    }





}











