package com.tritongames.shoppingwishlist.presentation

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.SystemClock
import android.util.AttributeSet
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.viewmodels.ShoppingDataViewModel

class ShoppingARFragment : Fragment() {
    private lateinit var recView: RecyclerView
    val shopDataVM: ShoppingDataViewModel
        get() {
            return ShoppingDataViewModel(null, null, null)
        }


    private val dragDrop: DragDrop = DragDrop()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /*  recView = view.findViewById(R.id.merchRV) as RecyclerView
         recView.adapter = merchImageAdapter

        val clickPos = merchImageAdapter.pos*/

        val atts: AttributeSet? = null
                                                              
        val contextView: Context? = context
        val downTime: Long = 1000L
        val eventTime: Long = SystemClock.uptimeMillis()
        val xPos: Float = 0F
        val yPos: Float = 0F
        val metaState = 0
        val mEvent: MotionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_CANCEL, xPos, yPos, metaState )
        val parcel: Parcel = Parcel.obtain()
        parcel.writeInt(mEvent.action);
        parcel.writeFloat(xPos);
        parcel.writeFloat(yPos);
        parcel.writeInt(0); // Result when (mEvent.action) {
        parcel.writeInt(0); // No Clipdata    MotionEvent.ACTION_DOWN -> {
        parcel.writeInt(0); // No Clip Description        if (dragDrop.onDrag(view, dragEvent) ){
        parcel.setDataPosition(0);
        val dragEvent: DragEvent = DragEvent.CREATOR.createFromParcel(parcel)
       /* var categoryTag: String = merchImageAdapter.merchImages[clickPos]

        if (dragDrop.onDrag(view, dragEvent)) {
            when (dragEvent.action){
                MotionEvent.ACTION_DOWN ->{
                  //   dragDrop.updateDragData(categoryTag, view)
                }
              // MotionEvent.ACTION_UP ->{
                    //  categoryTag =  merchImageAdapter.merchImages[0]
                }
               // MotionEvent.ACTION_MOVE ->{

                }

        }






        }
*/








    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_a_r, container, false)

    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShoppingARFragment.
         */
    }


}