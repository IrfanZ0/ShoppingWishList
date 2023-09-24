package com.tritongames.shoppingwishlist.presentation

import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Color
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.categoriesTag
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.category1
import com.tritongames.shoppingwishlist.data.models.ShoppingData.Companion.data
import com.tritongames.shoppingwishlist.data.viewmodels.ShoppingDataViewModel
import com.tritongames.shoppingwishlist.presentation.Shop.Companion.dImage1
import com.tritongames.shoppingwishlist.presentation.Shop.Companion.gv
import com.tritongames.shoppingwishlist.presentation.Shop.Companion.position
import com.tritongames.shoppingwishlist.presentation.Shop.Companion.t1

open class DragDrop : AppCompatActivity(),
    View.OnDragListener,
    RecyclerView.OnItemTouchListener,
    AdapterView.OnItemLongClickListener {
    private val shoppingVM: ShoppingDataViewModel by viewModels()

    // DragListener methods
    fun updateDragData(categoryTag: String, view: View) {
        shoppingVM.apply {
            data = shoppingVM.getClipData(categoryTag, view)
            view.setBackgroundResource(0)
            view.visibility = View.INVISIBLE
            val categoriesShadow = View.DragShadowBuilder(view)
            view.startDragAndDrop(data, categoriesShadow, null, 0)
        }

    }

    private fun categoryShadowBuilder(view: View) {
        var width = 0
        var height = 0

        val dShadowImage = view.background.current


       /*   @Override
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
*/
        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
         @Override
        fun onDrawShadow(canvas: Canvas?) {
             if (canvas != null) {
                 dShadowImage.draw(canvas)
             }
         }


    }


    @Override
    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        if (v != null) {
                            if (v.isHovered) {
                                v.setBackgroundColor(Color.CYAN)
                                v.invalidate()
                            }
                        }

                    }
                    val o1: Any = gv?.adapter?.getItem(position) as Any
                    if (o1 is Int) dImage1 = o1
                }

                DragEvent.ACTION_DRAG_ENTERED -> {
                    val o1: Any = gv?.adapter?.getItem(position) as Any
                    if (o1 is Int) dImage1 = o1
                }

                DragEvent.ACTION_DROP -> {
                    // Depending of the three ImageViews hovered over by shadow, that ImageView
                    // shall obtain the drawable.
                    dImage1?.let { category1?.setImageResource(it) }
                    category1?.visibility = View.VISIBLE
                    t1 =
                        category1?.let {
                            shoppingVM.getClipData(dImage1.toString(), it).toString()
                        }
                    return true
                }

                DragEvent.ACTION_DRAG_EXITED -> if (v != null) {
                    v.visibility = View.VISIBLE
                }

                DragEvent.ACTION_DRAG_LOCATION -> {}
            }
        }
        return true
    }



    override fun onItemLongClick(
        av: AdapterView<*>?,
        v: View?,
        position: Int,
        id: Long
    ): Boolean {
        //ImageView convertView = (ImageView)v.findViewById(R.id.electronicsPic);
        // find the item in GridView that was long clicked, create a drag shadow of the view, and start the drag
        Shop.position = position

        if (av != null) {
            when (av.getItemAtPosition(position) as Int) {
                R.drawable.ic_baseline_shopping -> {

                    if (v != null) {
                        v.tag = categoriesTag?.get(0)
                        updateDragData(v.tag as String, v)
                    }
                }

                R.drawable.ic_baseline_computers -> {

                    if (v != null) {
                        v.tag = categoriesTag?.get(1)
                        updateDragData(v.tag as String, v)
                    }
                }

                R.drawable.ic_baseline_dining -> {

                    if (v != null) {
                        v.tag = categoriesTag?.get(2)
                        updateDragData(v.tag as String, v)
                    }
                }

                R.drawable.ic_baseline_ebook -> {

                    if (v != null) {
                        v.tag = categoriesTag?.get(3)
                        updateDragData(v.tag as String, v)
                    }
                }

                R.drawable.ic_baseline_movies -> {

                    if (v != null) {
                        v.tag = categoriesTag?.get(4)
                        updateDragData(v.tag as String, v)
                    }
                }

                R.drawable.ic_baseline_pets -> {

                    if (v != null) {
                        v.tag = categoriesTag?.get(5)
                        updateDragData(v.tag as String, v)
                    }
                }

                R.drawable.ic_baseline_travel -> {

                    if (v != null) {
                        v.tag = categoriesTag?.get(6)
                        updateDragData(v.tag as String, v)
                    }
                }

                R.drawable.ic_baseline_games -> {

                    if (v != null) {
                        v.tag = categoriesTag?.get(7)
                        updateDragData(v.tag as String, v)
                    }
                }

                R.drawable.ic_baseline_no_category -> {

                    if (v != null) {
                        v.tag = categoriesTag?.get(8)
                        updateDragData(v.tag as String, v)
                    }
                }
            }
        }

        return true
    }

    // Drag Events for Recycler View
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        TODO("Not yet implemented")
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        TODO("Not yet implemented")
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("Not yet implemented")
    }


}