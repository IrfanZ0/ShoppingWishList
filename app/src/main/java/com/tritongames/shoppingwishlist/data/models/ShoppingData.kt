package com.tritongames.shoppingwishlist.data.models

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.*
import com.tritongames.shoppingwishlist.R

class ShoppingData {
    val getter: KProperty<*>
        get() {
            TODO()
        }

    fun getValue(nothing: Nothing?, property: KProperty<*>) {

    }

    class KProperty<T> {
        fun getValue(): Boolean {
            return true
        }

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmField
        var imgGrid: ImageView? = null

        @SuppressLint("StaticFieldLeak")
        @JvmField
        var category1: ImageView? = null

        @JvmField
        var category2: ImageView? = null

        @JvmField
        var category3: ImageView? = null

        @JvmField
        var nextBTN: Button? = null

        @JvmField
        var newList: Button? = null

        @JvmField
        var w1Text: TextView? = null

        @JvmField
        var w2Text: TextView? = null

        @JvmField
        var w3Text: TextView? = null
        var inflator: LayoutInflater? = null
        var context: Context? = null
        var spinName: Spinner? = null

        //DragEventListener dragListener;
        var mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val clip_description = arrayListOf<ClipDescription?>()
        val clip_data = arrayListOf<ClipData?>()
        val clip_data_item = arrayListOf<ClipData.Item?>()

        var data: ClipData? = null
        var magicBeam: Drawable? = null
        //var cLongClickListener: Shop.CategoryOnItemLongClickListener? = null
        var magicbeam_item: ClipData.Item? = null
        var magicbeamData: ClipData? = null
        var noCategoryTag: CharSequence? = null


        var nameAdapter: ArrayAdapter<String>? = null
        var mName: List<String>? = null

        var categoriesPic: MutableList<Int>? = null
        var categoriesText: MutableList<String>? = null
        var categoriesTag: MutableList<CharSequence>? = null

    }

    init {
        categoriesPic?.add(0, R.drawable.ic_baseline_shopping)
        categoriesPic?.add(1, R.drawable.ic_baseline_computers)
        categoriesPic?.add(2, R.drawable.ic_baseline_dining)
        categoriesPic?.add(3, R.drawable.ic_baseline_ebook)
        categoriesPic?.add(4, R.drawable.ic_baseline_movies)
        categoriesPic?.add(5, R.drawable.ic_baseline_pets)
        categoriesPic?.add(6, R.drawable.ic_baseline_travel)
        categoriesPic?.add(7, R.drawable.ic_baseline_games)
        categoriesPic?.add(8, R.drawable.ic_baseline_no_category)

        categoriesText?.add(0, "Clothes")
        categoriesText?.add(1, "Computers")
        categoriesText?.add(2, "Dining")
        categoriesText?.add(3, "eBooks")
        categoriesText?.add(4, "Movies")
        categoriesText?.add(5, "Pets")
        categoriesText?.add(6, "Travel")
        categoriesText?.add(7, "Video Games")
        categoriesText?.add(8, "No Category")

        val cdClothes: ClipDescription? = null
        val cdComputer: ClipDescription? = null
        val cdDining: ClipDescription? = null
        val cdEbook: ClipDescription? = null
        val cdMovies: ClipDescription? = null
        val cdPets: ClipDescription? = null
        val cdTravel: ClipDescription? = null
        val cdVideoGames: ClipDescription? = null
        clip_description.addAll(listOf(cdClothes, cdComputer, cdDining, cdEbook, cdMovies, cdPets, cdTravel, cdVideoGames))

        val clothesData: ClipData? = null
        val computerData: ClipData? = null
        val diningData: ClipData? = null
        val ebookData: ClipData? = null
        val moviesData: ClipData? = null
        val petsData: ClipData? = null
        val travelData: ClipData? = null
        val videoGamesData: ClipData? = null
        val noCategoryData: ClipData? = null
        clip_data.addAll((listOf(clothesData, computerData, diningData, ebookData, moviesData, petsData, travelData, videoGamesData, noCategoryData)))

        val clothes_item: ClipData.Item? = null
        val computers_item: ClipData.Item? = null
        val dining_item: ClipData.Item? = null
        val ebook_item: ClipData.Item? = null
        val movies_item: ClipData.Item? = null
        val pets_item: ClipData.Item? = null
        val travel_item: ClipData.Item? = null
        val videoGames_item: ClipData.Item? = null
        val noCategory_item: ClipData.Item? = null
        clip_data_item.addAll(listOf(computers_item, dining_item, ebook_item, movies_item, pets_item, travel_item, videoGames_item, noCategory_item))

        val clothesTag: CharSequence = "Clothes"
        val computersTag: CharSequence = "Computers"
        val diningTag: CharSequence = "Dining"
        val ebookTag: CharSequence = "eBooks"
        val moviesTag: CharSequence = "Movies"
        val petsTag: CharSequence = "Pets"
        val travelTag: CharSequence = "Travel"
        val videogamesTag: CharSequence = "Video Games"
        val noCategoryTag: CharSequence = "No Category"

        categoriesTag?.addAll(listOf(clothesTag, computersTag, diningTag, ebookTag, moviesTag, petsTag, travelTag, videogamesTag, noCategoryTag))
    }



}