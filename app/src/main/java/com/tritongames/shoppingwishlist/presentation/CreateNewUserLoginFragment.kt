package com.tritongames.shoppingwishlist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tritongames.shoppingwishlist.R

class CreateNewUserLoginFragment : Fragment() {

    companion object {
        fun newInstance() = CreateNewUserLoginFragment()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.create_new_login, container, false)
    }

}