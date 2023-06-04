package com.tritongames.shopwishlist

import android.annotation.SuppressLint
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tritongames.shopwishlist.data.ShoppingData.Companion.cLongClickListener
import com.tritongames.shopwishlist.data.ShoppingData.Companion.categoriesTag
import com.tritongames.shopwishlist.data.ShoppingData.Companion.category1
import com.tritongames.shopwishlist.data.ShoppingData.Companion.category2
import com.tritongames.shopwishlist.data.ShoppingData.Companion.category3
import com.tritongames.shopwishlist.data.ShoppingData.Companion.data
import com.tritongames.shopwishlist.data.ShoppingData.Companion.mName
import com.tritongames.shopwishlist.data.ShoppingData.Companion.nameAdapter
import com.tritongames.shopwishlist.data.ShoppingData.Companion.nextBTN
import com.tritongames.shopwishlist.data.ShoppingData.Companion.spinName
import com.tritongames.shopwishlist.data.ShoppingData.Companion.w1Text
import com.tritongames.shopwishlist.data.ShoppingData.Companion.w2Text
import com.tritongames.shopwishlist.data.ShoppingData.Companion.w3Text
import com.tritongames.shopwishlist.data.models.ShoppingCategoriesDataClass
import com.tritongames.shopwishlist.domain.RecyclerViewAdapter
import com.tritongames.shopwishlist.presentation.RecipientViewModel
import com.tritongames.shopwishlist.presentation.ShoppingDataViewModel
import com.tritongames.shopwishlist.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Shop : AppCompatActivity()  {

    val recipientVM: RecipientViewModel by viewModels()
    val shoppingVM: ShoppingDataViewModel by viewModels()
    private val newCategoryList: ArrayList<ShoppingCategoriesDataClass> = arrayListOf()
    lateinit var populateButton: Button

    @RequiresApi(Build.VERSION_CODES.N)
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        mName = ArrayList<String>()
        spinName = findViewById(R.id.spinName)

        lifecycleScope.launch() {
            recipientVM.recipientLoad.collect{
                event->
                    when(event){
                        is RecipientViewModel.RecipientLoadingEvent.Success ->{

                            nameAdapter = ArrayAdapter(this@Shop, android.R.layout.simple_spinner_item,
                                recipientVM.loadRecipientNames()
                            )
                        }
                        is RecipientViewModel.RecipientLoadingEvent.Failure ->{

                        }
                        else -> Unit
                    }

            }
        }

        spinName?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @Override
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                // TODO Auto-generated method stub
                val selected_name: String = spinName!!.selectedItem.toString()

            }

            @Override
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }

        w1Text = findViewById(R.id.wish1Text)
        w2Text = findViewById(R.id.wish2Text)
        w3Text = findViewById(R.id.wish3Text)
        category1 = findViewById(R.id.wish1Cat)
        category2 = findViewById(R.id.wish2Cat)
        category3 = findViewById(R.id.wish3Cat)

        nextBTN = findViewById<Button>(R.id.buttonNext)
        nextBTN?.setOnClickListener { // click to jump to next class WishList
            val wlIntent = Intent(this@Shop, WishList::class.java)
            startActivity(wlIntent)
        }

        populateButton = findViewById(R.id.populateRecViewButton)
        populateButton.setOnClickListener{
            lifecycleScope.launch {
                shoppingVM.shopDataLoad.collect{event->
                    when(event){
                        is ShoppingDataViewModel.ShopDataLoadEvent.Success ->{

                            val recView: RecyclerView = findViewById(R.id.recViewGrid)
                            val catList = shoppingVM.getCategoryList()
                            val recViewAdapter = RecyclerViewAdapter( getUserData(catList))
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





        val cDragListener1 = Category1DragEventListener()
        val cDragListener2 = Category2DragEventListener()
        val cDragListener3 = Category3DragEventListener()
        category1?.setOnDragListener(cDragListener1)
        category2?.setOnDragListener(cDragListener2)
        category3?.setOnDragListener(cDragListener3)
        cLongClickListener = CategoryOnItemLongClickListener()
        gv?.onItemLongClickListener = cLongClickListener
    }

    private fun getUserData(catList: List<ShoppingCategoriesDataClass>): ArrayList<ShoppingCategoriesDataClass> {
        for (i in catList.indices){
            val category = ShoppingCategoriesDataClass(catList[i].category, catList[i].image, listOf())
            newCategoryList.add(category)

        }
        return newCategoryList

    }

    inner class Category1DragEventListener : View.OnDragListener {
        @Override
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        if (v.isHovered) {
                            v.setBackgroundColor(Color.CYAN)
                            v.invalidate()
                        }

                    }
                    val o1: Any = gv?.adapter?.getItem(position) as Any
                    if (o1 is Int) dImage1 = o1 as Int
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    val o1: Any = gv?.adapter?.getItem(position) as Any
                    if (o1 is Int) dImage1 = o1 as Int
                }
                DragEvent.ACTION_DROP -> {
                    // Depending of the three ImageViews hovered over by shadow, that ImageView 
                    // shall obtain the drawable.
                    dImage1?.let { category1?.setImageResource(it) }
                    category1?.visibility = View.VISIBLE
                    t1 = category1?.let { shoppingVM.getClipData(dImage1.toString(), it).toString() }
                    return true
                }
                DragEvent.ACTION_DRAG_EXITED -> v.visibility = View.VISIBLE
                DragEvent.ACTION_DRAG_LOCATION -> {}
            }
            return true
        }
    }

    inner class Category2DragEventListener : View.OnDragListener {
        @Override
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        if (v.isHovered) {
                            v.setBackgroundColor(Color.CYAN)
                            v.invalidate()
                        }

                    }
                    val o2: Any = gv?.getAdapter()?.getItem(position) as Any
                    if (o2 is Int) dImage2 = o2 as Int
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    val o2: Any = gv?.getAdapter()?.getItem(position) as Any
                    if (o2 is Int) dImage2 = o2 as Int
                }
                DragEvent.ACTION_DROP -> {
                    // Depending of the three ImageViews hovered over by shadow, that ImageView 
                    // shall obtain the drawable.
                    dImage2?.let { category2?.setImageResource(it) }
                    category2?.setVisibility(View.VISIBLE)
                    t2 = category2?.let { shoppingVM.getClipData(dImage2.toString(), it).toString() }
                    return true
                }
                DragEvent.ACTION_DRAG_EXITED -> v.visibility = View.VISIBLE
                DragEvent.ACTION_DRAG_LOCATION -> {}
            }
            return true
        }
    }

    inner class Category3DragEventListener : View.OnDragListener {
        @Override
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        if (v.isHovered) {
                            v.setBackgroundColor(Color.CYAN)
                            v.invalidate()
                        }

                    }
                    val o3: Any = gv?.getAdapter()?.getItem(position) as Any
                    if (o3 is Int) dImage3 = o3 as Int
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    val o3: Any = gv?.getAdapter()?.getItem(position) as Any
                    if (o3 is Int) dImage3 = o3 as Int
                }
                DragEvent.ACTION_DROP -> {
                    // Depending of the three ImageViews hovered over by shadow, that ImageView 
                    // shall obtain the drawable.
                    dImage3?.let { category3?.setImageResource(it) }
                    category3?.setVisibility(View.VISIBLE)
                    t3 = category3?.let { shoppingVM.getClipData(
                            dImage3.toString(), it).toString() }
                    return true
                }
                DragEvent.ACTION_DRAG_EXITED -> v.visibility = View.VISIBLE
                DragEvent.ACTION_DRAG_LOCATION -> {}
            }
            return true
        }
    }

    inner class CategoryOnItemLongClickListener : AdapterView.OnItemLongClickListener {
        @RequiresApi(Build.VERSION_CODES.N)
        @Override
        override fun onItemLongClick(av: AdapterView<*>, v: View,
                                     pos: Int, posID: Long): Boolean {

            //ImageView convertView = (ImageView)v.findViewById(R.id.electronicsPic);
            // find the item in GridView that was long clicked, create a drag shadow of the view, and start the drag
            position = pos

            val categoriesShadow: View.DragShadowBuilder
            when (av.getItemAtPosition(pos) as Int) {
                R.drawable.ic_baseline_shopping -> {
                    v.tag = categoriesTag?.get(0)
                    updateDragData(v.tag as String, v)
                }

                R.drawable.ic_baseline_computers -> {
                    v.tag = categoriesTag?.get(1)
                    updateDragData(v.tag as String, v)
                }
                R.drawable.ic_baseline_dining -> {
                    v.tag = categoriesTag?.get(2)
                    updateDragData(v.tag as String, v)
                }
                R.drawable.ic_baseline_ebook -> {
                    v.tag = categoriesTag?.get(3)
                    updateDragData(v.tag as String, v)
                }
                R.drawable.ic_baseline_movies -> {
                    v.tag = categoriesTag?.get(4)
                    updateDragData(v.tag as String, v)
                }
                R.drawable.ic_baseline_pets -> {
                    v.tag = categoriesTag?.get(5)
                    updateDragData(v.tag as String, v)
                }
                R.drawable.ic_baseline_travel -> {
                    v.tag = categoriesTag?.get(6)
                    updateDragData(v.tag as String, v)
                }
                R.drawable.ic_baseline_games -> {
                    v.tag = categoriesTag?.get(7)
                    updateDragData(v.tag as String, v)
                }
                R.drawable.ic_baseline_no_category -> {
                    v.tag = categoriesTag?.get(8)
                    updateDragData(v.tag as String, v)
                }
            }

            return true
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun updateDragData(categoryTag : String, view : View)
    {
        shoppingVM.apply {
        data = shoppingVM.getClipData(categoryTag as String, view)
        view.setBackgroundResource(0)
        view.visibility = View.INVISIBLE
        val categoriesShadow = CategoryShadowBuilder(view)
        view.startDragAndDrop(data, categoriesShadow, null, 0)
        }

    }

   class CategoryShadowBuilder(v: View) : View.DragShadowBuilder(v) {
        var width = 0
        var height = 0

        init {
            dShadowImage = v.getBackground()
        }

        @Override
        override fun onProvideShadowMetrics(size: Point, touch: Point) {
            // Defines local variables

            // Sets the width of the shadow to half the width of the original View
            width = view.width / 4

            // Sets the height of the shadow to half the height of the original View
            height = view.height / 4

            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
            // Canvas that the system will provide. As a result, the drag shadow will fill the
            // Canvas.
            dShadowImage.setBounds(0, 0, width, height)

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            size.set(width, height)

            // Sets the touch point's position to be in the middle of the drag shadow
            touch.set(width / 2, height / 2)
        }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        override fun onDrawShadow(canvas: Canvas?) {
            if (canvas != null) {
                dShadowImage.draw(canvas)
            }
        }

        companion object {
            lateinit var dShadowImage: Drawable
        }
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
            R.drawable.clothes,
            R.drawable.computer,
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

/*private operator fun Any.getValue(shop: Shop, property: ShoppingData.KProperty<*>): Boolean? {

    return (takeIf { !shop.isDestroyed && property.getValue() } as Boolean?)!!
}*/



private fun <T> StateFlow<T>.collectAsStateWithLifecycle() {

}


