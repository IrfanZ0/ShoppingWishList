package com.tritongames.shoppingwishlist.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.category1
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.category2
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.category3
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.mName
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.nameAdapter
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.nextBTN
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.spinName
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.w1Text
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.w2Text
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.w3Text
import com.tritongames.shoppingwishlist.data.models.categories.ShoppingCategoriesDataClass
import com.tritongames.shoppingwishlist.data.viewmodels.ContactsViewModel
import com.tritongames.shoppingwishlist.data.viewmodels.ShoppingDataViewModel
import com.tritongames.shoppingwishlist.databinding.ActivityShopBinding
import com.tritongames.shoppingwishlist.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Shop : AppCompatActivity()  {
    private lateinit var binding: ActivityShopBinding
    val contactsVM: ContactsViewModel by viewModels()
    val shoppingVM: ShoppingDataViewModel by viewModels()
    private val newCategoryList: ArrayList<ShoppingCategoriesDataClass> = arrayListOf()
    lateinit var populateButton: Button
    val dDrop: DragDrop
        get() {
            return DragDrop()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mName = ArrayList<String>()
        spinName = binding.spinName

        lifecycleScope.launch() {
            contactsVM.recipientLoad.collect{
                    event->
                when(event){
                    is ContactsViewModel.RecipientLoadingEvent.Success ->{

                        nameAdapter = ArrayAdapter(this@Shop, android.R.layout.simple_spinner_item,
                            contactsVM.getContactEmails()
                        )
                        spinName!!.adapter = nameAdapter

                    }
                    is ContactsViewModel.RecipientLoadingEvent.Failure ->{
                        ContactsViewModel.RecipientLoadingEvent.Failure("Problem loading contact email")
                    }
                    else -> Unit
                }

            }
        }

        spinName?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @Override
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {

                val selected_name: String = spinName!!.selectedItem.toString()

            }

            @Override
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }

        w1Text = binding.wish1Text
        w2Text = binding.wish2Text
        w3Text = binding.wish3Text
        category1 = binding.wish1Cat
        category2 = binding.wish2Cat
        category3 = binding.wish3Cat

        nextBTN = binding.buttonNext
        nextBTN?.setOnClickListener { // click to jump to next class com.tritongames.shoppingwishlist.presentation.WishList
            val wlIntent = Intent(this@Shop, WishList::class.java)
            startActivity(wlIntent)
        }

        populateButton = binding.populateRecViewButton
        populateButton.setOnClickListener{
            lifecycleScope.launch {
                shoppingVM.shopDataLoad.collect{event->
                    when(event){
                        is ShoppingDataViewModel.ShopDataLoadEvent.Success ->{

                            val recView: RecyclerView = findViewById(R.id.recViewGrid)
                            val catList = shoppingVM.getCategoryList()
                            val recViewAdapter = CategoryAdapter( getUserData(catList))
                            val gLayoutManager = GridLayoutManager(this@Shop, 3)
                            recView.layoutManager = gLayoutManager
                            recView.adapter = recViewAdapter

                        }
                        is ShoppingDataViewModel.ShopDataLoadEvent.Error ->{
                            Resource.Error<ShoppingCategoriesDataClass>("Error Loading Categories")

                        }
                        else -> Unit


                    }

                }


            }
        }

        val cDragListener = dDrop

        category1?.setOnDragListener(cDragListener)
        category2?.setOnDragListener(cDragListener)
        category3?.setOnDragListener(cDragListener)

        /*cLongClickListener = CategoryOnItemLongClickListener()
        gv?.onItemLongClickListener = cLongClickListener*/
    }

    private fun getUserData(catList: List<ShoppingCategoriesDataClass>): ArrayList<ShoppingCategoriesDataClass> {
        for (i in catList.indices){
            val category =
                ShoppingCategoriesDataClass(catList[i].category, catList[i].image, listOf())
            newCategoryList.add(category)

        }
        return newCategoryList

    }



    companion object {
        // This is Irfan's fabulous project 2
        var categoriesPic: List<Int>? = null
        var categoriesText: List<String>? = null
        @SuppressLint("StaticFieldLeak")
        var NAME: EditText? = null
        var dImage1: Int? = null
        var dImage2: Int? = null
        var dImage3: Int? = null
        @SuppressLint("StaticFieldLeak")
        var gv: GridView? = null
        var dCatArray = intArrayOf(
            R.drawable.ic_baseline_shopping,
            R.drawable.ic_baseline_computers,
            R.drawable.ic_baseline_dining,
            R.drawable.ic_baseline_ebook,
            R.drawable.ic_baseline_movies,
            R.drawable.ic_baseline_pets,
            R.drawable.ic_baseline_travel,
            R.drawable.ic_baseline_games
        )
        var TAGS = arrayOfNulls<String>(3)
        var position = 0
        @SuppressLint("StaticFieldLeak")
        var imgCat: View? = null
        var t1: CharSequence? = null
        var t2: CharSequence? = null
        var t3: CharSequence? = null
        var rID1 = 0
        var rID2 = 0
        var rID3 = 0
    }
}