package com.tritongames.shoppingwishlist.data.viewmodels

import com.tritongames.shoppingwishlist.data.repository.FakeContactsRepository
import com.tritongames.shoppingwishlist.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ContactsViewModelTest.HiltAndroidTest
    class ContactsViewModelTest {
        annotation class HiltAndroidTest

        @get: Rule
       // var hiltRule = HiltAndroidRule(this)


    @Inject
        private lateinit var contactsVM: ContactsViewModel
        @Inject
    private val dispatcherIO = Dispatchers.IO
        private val dispatcherMAIN = Dispatchers.Main
        private val dispatcherDEFAULT = Dispatchers.Default
        private val dispatcherUNCONFINED = Dispatchers.Unconfined

    private val dispatcherProvider: DispatcherProvider = CreateInstance(dispatcherMAIN, dispatcherIO, dispatcherDEFAULT, dispatcherUNCONFINED)

    @Before
    fun setUp() {
        contactsVM = ContactsViewModel(FakeContactsRepository(), dispatcherProvider)
      // hiltRule.inject()
    }

    @Test
    fun getClientEmailFromFakeRepository() {
        val value = contactsVM.getContactEmails()
        assertThat("The email exists", value.contains("xman@gmail.com"))

    }

    @Test
    fun getPhoneNumber() {
    }

    @Test
    fun getAddress() {
    }

    @Test
    fun getID() {
    }

    @Test
    fun getUserName() {
    }

    @Test
    fun getCity() {
    }

    @Test
    fun getState() {
    }

    @Test
    fun getContactEmails() {
    }

    @Test
    fun getContactPasswords() {
    }

    @Test
    fun getFirstNames() {
    }

    @Test
    fun getLastNames() {
    }
}

class CreateInstance(
    override val main: CoroutineDispatcher,
    override val io: CoroutineDispatcher,
    override val default: CoroutineDispatcher,
    override val unconfined: CoroutineDispatcher
) : DispatcherProvider {

}
