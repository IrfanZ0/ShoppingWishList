package com.tritongames.shopwishlist

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tritongames.shopwishlist.JSONclasses.context
import com.tritongames.shopwishlist.presentation.ShoppingDataViewModel
import com.tritongames.shopwishlist.util.DispatcherProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class WishList : AppCompatActivity(), OnMapReadyCallback {

    lateinit var it: ArrayAdapter<CharSequence>
    var spin1: Spinner? = null
    var spin2: Spinner? = null
    var spin3: Spinner? = null
    var s: Shop? = null
    var rID1 = 0
    var rID2 = 0
    var rID3 = 0
    var resID1 = 0
    var resID2 = 0
    var resID3 = 0
    var contextWish: Context? = null
    var fName: EditText? = null
    var lName: EditText? = null
    var first: String? = null
    var last: String? = null
    var wish1: String? = null
    var wish2: String? = null
    var wish3: String? = null
    var contactsAdapter: ArrayAdapter<String>? = null
    var wishAdapter: ArrayAdapter<String>? = null
    var contacts: ArrayList<String>? = null
    var mDetector: GestureDetectorCompat? = null
    var name: String? = null
    var itemContacts: String? = null
    lateinit var data: Array<String>
    var sv: StackView? = null
    var s1adapter: ArrayAdapter<CharSequence>? = null
    var s2adapter: ArrayAdapter<CharSequence>? = null
    var s3adapter: ArrayAdapter<CharSequence>? = null


    init {
        contextWish = context
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spinner_wish_list)
       // actionBar!!.setDisplayHomeAsUpEnabled(true)
        mDetector = GestureDetectorCompat(this, myGestureListener())
        contextWish = applicationContext
        spin1 = findViewById<Spinner>(R.id.category1)
        spin2 = findViewById<Spinner>(R.id.category2)
        spin3 = findViewById<Spinner>(R.id.category3)
        // fName = (EditText)findViewById(R.id.firstName);
        // lName = (EditText)findViewById(R.id.lastName);


        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val shopDataViewModel: ShoppingDataViewModel by viewModels()
        // collecting drawables from the three ImageViews in Shop class and using that to set text to individual EditTexts.
        lifecycleScope.launch(){
            shopDataViewModel.shopDataLoad.collect{ event ->
                when (event){
                    is ShoppingDataViewModel.ShopDataLoadEvent.Success ->{
                        val d1: Int? = Shop.dImage1
                        setAdapter(spin1, d1)
                        val d2: Int? = Shop.dImage2
                        setAdapter(spin2, d2)
                        val d3: Int? = Shop.dImage3
                        setAdapter(spin3, d3)

                    }
                    is ShoppingDataViewModel.ShopDataLoadEvent.Error ->{

                    }
                    else -> Unit
                }
            }

        }

//       spin1?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            @Override
//            override fun onItemSelected(av: AdapterView<*>?, v: View?,
//                                        pos: Int, posID: Long) {
//
//                val selectedItem: String = spin1?.adapter?.getItem(0).toString()
//                if (selectedItem.contains("Levis")) {
//                    selectedItem.toLowerCase(Locale.US)
//                    val uriString = "http://www.macys.com/m/campaign/levis/levis?cm_mmc=VanityUrl-_-levis-_-n-_-n"
//                    val uriclothes: Uri = Uri.parse(uriString)
//                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
//                    startActivity(intentclothes)
//                } else if (selectedItem.contains("Dockers")) {
//                    val uriString = "http://www1.macys.com/shop/search?keyword=dockers#cm_pv=slp"
//                    val uriclothes: Uri = Uri.parse(uriString)
//                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
//                    startActivity(intentclothes)
//                } else if (selectedItem.contains("Calvin Klein")) {
//                    val uriString = "http://www1.macys.com/shop/search?keyword=calvin+klein#cm_pv=slp"
//                    val uriclothes: Uri = Uri.parse(uriString)
//                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
//                    startActivity(intentclothes)
//                } else if (selectedItem.contains("Guess")) {
//                    val uriString = "http://www1.macys.com/shop/search?keyword=guess#cm_pv=slp"
//                    val uriclothes: Uri = Uri.parse(uriString)
//                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
//                    startActivity(intentclothes)
//                } else if (selectedItem.contains("Lucky Brand")) {
//                    val uriString = "http://www1.macys.com/shop/search?keyword=lucky+brand#cm_pv=slp"
//                    val uriclothes: Uri = Uri.parse(uriString)
//                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
//                    startActivity(intentclothes)
//                } else if (selectedItem.contains("Nike")) {
//                    val uriString = "http://www1.macys.com/shop/search?keyword=nike#cm_pv=slp"
//                    val uriclothes: Uri = Uri.parse(uriString)
//                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
//                    startActivity(intentclothes)
//                } else if (selectedItem.contains("Carter")) {
//                    val uriString = "http://www1.macys.com/shop/search?keyword=carter%27s#cm_pv=slp"
//                    val uriclothes: Uri = Uri.parse(uriString)
//                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
//                    startActivity(intentclothes)
//                } else if (selectedItem.contains("Tommy Hilfiger")) {
//                    val uriString = "http://www1.macys.com/shop/search?keyword=tommy+hilfiger#cm_pv=slp"
//                    val uriclothes: Uri = Uri.parse(uriString)
//                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
//                    startActivity(intentclothes)
//                } else if (selectedItem.contains("Sony")) {
//                    val uriString = "http://www.bestbuy.com/site/Brands/Sony-Store/cat15063.c?id=cat15063&pageType=REDIRECT&issolr=1&searchterm=Sony"
//                    val uricomputers: Uri = Uri.parse(uriString)
//                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
//                    startActivity(intentcomputers)
//                } else if (selectedItem.contains("MacBook Air")) {
//                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=MacBook+Air"
//                    val uricomputers: Uri = Uri.parse(uriString)
//                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
//                    startActivity(intentcomputers)
//                } else if (selectedItem.contains("Apple")) {
//                    val uriString = "http://www.bestbuy.com/site/Brands/Apple/pcmcat128500050005.c?id=pcmcat128500050005&pageType=REDIRECT&issolr=1&searchterm=Apple"
//                    val uricomputers: Uri = Uri.parse(uriString)
//                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
//                    startActivity(intentcomputers)
//                } else if (selectedItem.contains("Samsung")) {
//                    val uriString = "http://www.bestbuy.com/site/Brands/Samsung/pcmcat140800050115.c?id=pcmcat140800050115&pageType=REDIRECT&issolr=1&searchterm=Samsung"
//                    val uricomputers: Uri = Uri.parse(uriString)
//                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
//                    startActivity(intentcomputers)
//                } else if (selectedItem.contains("Acer")) {
//                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Acer"
//                    val uricomputers: Uri = Uri.parse(uriString)
//                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
//                    startActivity(intentcomputers)
//                } else if (selectedItem.contains("Asus")) {
//                    val uriString = "http://www.bestbuy.com/site/Brands/Asus/pcmcat190000050006.c?id=pcmcat190000050006&pageType=REDIRECT&issolr=1&searchterm=Asus"
//                    val uricomputers: Uri = Uri.parse(uriString)
//                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
//                    startActivity(intentcomputers)
//                } else if (selectedItem.contains("Lenovo")) {
//                    val uriString = "http://www.bestbuy.com/site/Brands/Lenovo/pcmcat230600050000.c?id=pcmcat230600050000&pageType=REDIRECT&issolr=1&searchterm=Lenovo"
//                    val uricomputers: Uri = Uri.parse(uriString)
//                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
//                    startActivity(intentcomputers)
//                } else if (selectedItem.contains("Toshiba")) {
//                    val uriString = "http://www.bestbuy.com/site/Brands/Toshiba/pcmcat136800050058.c?id=pcmcat136800050058&pageType=REDIRECT&issolr=1&searchterm=Toshiba"
//                    val uricomputers: Uri = Uri.parse(uriString)
//                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
//                    startActivity(intentcomputers)
//                } else if (selectedItem.contains("Papa Johns")) {
//                    val uriString = "http://www.papajohns.com/index.html"
//                    val uridining: Uri = Uri.parse(uriString)
//                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
//                    startActivity(intentdining)
//                } else if (selectedItem.contains("BJs Restaurants")) {
//                    val uriString = "http://www.bjsrestaurants.com/"
//                    val uridining: Uri = Uri.parse(uriString)
//                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
//                    startActivity(intentdining)
//                } else if (selectedItem.contains("Dennys")) {
//                    val uriString = "http://www.dennys.com/"
//                    val uridining: Uri = Uri.parse(uriString)
//                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
//                    startActivity(intentdining)
//                } else if (selectedItem.contains("California Sushi Roll")) {
//                    val uriString = "http://www.california-sushi-roll.com/"
//                    val uridining: Uri = Uri.parse(uriString)
//                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
//                    startActivity(intentdining)
//                } else if (selectedItem.contains("Taco Bell")) {
//                    val uriString = "www.tacobell.com/"
//                    val uridining: Uri = Uri.parse(uriString)
//                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
//                    startActivity(intentdining)
//                } else if (selectedItem.contains("The Cheesecake Factory")) {
//                    val uriString = "http://www.thecheesecakefactory.com/"
//                    val uridining: Uri = Uri.parse(uriString)
//                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
//                    startActivity(intentdining)
//                } else if (selectedItem.contains("Pizza Hut")) {
//                    val uriString = "http://www.pizzahut.com/"
//                    val uridining: Uri = Uri.parse(uriString)
//                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
//                    startActivity(intentdining)
//                } else if (selectedItem.contains("Carls Jr")) {
//                    val uriString = "http://www.carlsjr.com/"
//                    val uridining: Uri = Uri.parse(uriString)
//                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
//                    startActivity(intentdining)
//                } else if (selectedItem.contains("eBooks")) {
//                    val uriString = "http://www.ebooks.com/subjects/Romance/"
//                    val uriebooks: Uri = Uri.parse(uriString)
//                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
//                    startActivity(intentebooks)
//                } else if (selectedItem.contains("eBooks")) {
//                    val uriString = "http://www.ebooks.com/subjects/Science-Fiction/"
//                    val uriebooks: Uri = Uri.parse(uriString)
//                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
//                    startActivity(intentebooks)
//                } else if (selectedItem.contains("eBooks")) {
//                    val uriString = "http://www.ebooks.com/subjects/Technology/"
//                    val uriebooks: Uri = Uri.parse(uriString)
//                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
//                    startActivity(intentebooks)
//                } else if (selectedItem.contains("eBooks")) {
//                    val uriString = "http://www.ebooks.com/subjects/Business/"
//                    val uriebooks: Uri = Uri.parse(uriString)
//                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
//                    startActivity(intentebooks)
//                } else if (selectedItem.contains("eBooks")) {
//                    val uriString = "http://www.ebooks.com/subjects/Health/"
//                    val uriebooks: Uri = Uri.parse(uriString)
//                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
//                    startActivity(intentebooks)
//                } else if (selectedItem.contains("eBooks")) {
//                    val uriString = "http://www.ebooks.com/subjects/History/"
//                    val uriebooks: Uri = Uri.parse(uriString)
//                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
//                    startActivity(intentebooks)
//                } else if (selectedItem.contains("eBooks")) {
//                    val uriString = "http://www.ebooks.com/subjects/Mystery/"
//                    val uriebooks: Uri = Uri.parse(uriString)
//                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
//                    startActivity(intentebooks)
//                } else if (selectedItem.contains("eBooks")) {
//                    val uriString = "http://www.ebooks.com/subjects/Childrens-young-adult-fiction/"
//                    val uriebooks: Uri = Uri.parse(uriString)
//                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
//                    startActivity(intentebooks)
//                } else if (selectedItem.contains("Movies")) {
//                    val uriString = "https://www.amctheatres.com/"
//                    val urimovies: Uri = Uri.parse(uriString)
//                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
//                    startActivity(intentmovies)
//                } else if (selectedItem.contains("Movies")) {
//                    val uriString = "https://www.amctheatres.com/"
//                    val urimovies: Uri = Uri.parse(uriString)
//                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
//                    startActivity(intentmovies)
//                } else if (selectedItem.contains("") && parentItem.contains("Movies")) {
//                    val uriString = "https://www.amctheatres.com/"
//                    val urimovies: Uri = Uri.parse(uriString)
//                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
//                    startActivity(intentmovies)
//                } else if (selectedItem.contains("Comedy") && parentItem.contains("Movies")) {
//                    val uriString = "https://www.amctheatres.com/"
//                    val urimovies: Uri = Uri.parse(uriString)
//                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
//                    startActivity(intentmovies)
//                } else if (selectedItem.contains("Romantic") && parentItem.contains("Movies")) {
//                    val uriString = "https://www.amctheatres.com/"
//                    val urimovies: Uri = Uri.parse(uriString)
//                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
//                    startActivity(intentmovies)
//                } else if (selectedItem.contains("Children's") && parentItem.contains("Movies")) {
//                    val uriString = "https://www.amctheatres.com/"
//                    val urimovies: Uri = Uri.parse(uriString)
//                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
//                    startActivity(intentmovies)
//                } else if (selectedItem.contains("Family") && parentItem.contains("Movies")) {
//                    val uriString = "http://www.amctheatres.com/"
//                    val urimovies: Uri = Uri.parse(uriString)
//                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
//                    startActivity(intentmovies)
//                } else if (selectedItem.contains("Action") && parentItem.contains("Movies")) {
//                    val uriString = "https://www.amctheatres.com/"
//                    val urimovies: Uri = Uri.parse(uriString)
//                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
//                    startActivity(intentmovies)
//                } else if (selectedItem.contains("Cat")) {
//                    val uriString = "http://www.petco.com/Cat-Home.aspx"
//                    val uripets: Uri = Uri.parse(uriString)
//                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
//                    startActivity(intentpets)
//                } else if (selectedItem.contains("Dog")) {
//                    val uriString = "http://www.petco.com/Dog-Home.aspx"
//                    val uripets: Uri = Uri.parse(uriString)
//                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
//                    startActivity(intentpets)
//                } else if (selectedItem.contains("Bird")) {
//                    val uriString = "http://www.petco.com/Bird-Home.aspx"
//                    val uripets: Uri = Uri.parse(uriString)
//                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
//                    startActivity(intentpets)
//                } else if (selectedItem.contains("Fish")) {
//                    val uriString = "http://www.petco.com/Fish-Home.aspx"
//                    val uripets: Uri = Uri.parse(uriString)
//                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
//                    startActivity(intentpets)
//                } else if (selectedItem.contains("Rabbits")) {
//                    val uriString = "http://www.petco.com/Rabbits-Home.aspx?CoreSearch=Rabbit"
//                    val uripets: Uri = Uri.parse(uriString)
//                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
//                    startActivity(intentpets)
//                } else if (selectedItem.contains("Hamsters")) {
//                    val uriString = "http://www.petco.com/HamstersHome.aspx?CoreSearch=Hamsters"
//                    val uripets: Uri = Uri.parse(uriString)
//                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
//                    startActivity(intentpets)
//                } else if (selectedItem.contains("Turtles")) {
//                    val uriString = "http://www.petco.com/Turtles-Home.aspx?CoreSearch=Turtles"
//                    val uripets: Uri = Uri.parse(uriString)
//                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
//                    startActivity(intentpets)
//                } else if (selectedItem.contains("Snakes")) {
//                    val uriString = "http://www.petco.com/Snakes-Home.aspx?CoreSearch=Snakes"
//                    val uripets: Uri = Uri.parse(uriString)
//                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
//                    startActivity(intentpets)
//                } else if (selectedItem.contains("Universal Studios")) {
//                    val uriString = "http://www.universalstudios.com/"
//                    val uritravel: Uri = Uri.parse(uriString)
//                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
//                    startActivity(intenttravel)
//                } else if (selectedItem.contains("Magic Mountain")) {
//                    val uriString = "https://www.sixflags.com"
//                    val uritravel: Uri = Uri.parse(uriString)
//                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
//                    startActivity(intenttravel)
//                } else if (selectedItem.contains("Sea World")) {
//                    val uriString = "https://www.seaworld.com/"
//                    val uritravel: Uri = Uri.parse(uriString)
//                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
//                    startActivity(intenttravel)
//                } else if (selectedItem.contains("Legoland")) {
//                    val uriString = "https://www.legoland.com/"
//                    val uritravel: Uri = Uri.parse(uriString)
//                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
//                    startActivity(intenttravel)
//                } else if (selectedItem.contains("Disneyland")) {
//                    val uriString = "https://www.disneyland.com/"
//                    val uritravel: Uri = Uri.parse(uriString)
//                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
//                    startActivity(intenttravel)
//                } else if (selectedItem.contains("San Diego Wild Animal Park")) {
//                    val uriString = "http://www.sdzsafaripark.org/"
//                    val uritravel: Uri = Uri.parse(uriString)
//                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
//                    startActivity(intenttravel)
//                } else if (selectedItem.contains("Disney California")) {
//                    val uriString = "http://disneyland.disney.go.com/destinations/disney-california-adventure/"
//                    val uritravel: Uri = Uri.parse(uriString)
//                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
//                    startActivity(intenttravel)
//                } else if (selectedItem.contains("Hurricane Harbor")) {
//                    val uriString = "https://www.sixflags.com/hurricaneharborla"
//                    val uritravel: Uri = Uri.parse(uriString)
//                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
//                    startActivity(intenttravel)
//                } else if (selectedItem.contains("Playstation 4")) {
//                    val uriString = "http://www.bestbuy.com/site/Video-Games/PlayStation-4-PS4/pcmcat295700050012.c?id=pcmcat295700050012&pageType=REDIRECT&issolr=1&searchterm=Playstation%204"
//                    val urivideogames: Uri = Uri.parse(uriString)
//                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
//                    startActivity(intentvideogames)
//                } else if (selectedItem.contains("XBOX")) {
//                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=XBOX"
//                    val urivideogames: Uri = Uri.parse(uriString)
//                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
//                    startActivity(intentvideogames)
//                } else if (selectedItem.contains("Nintendo Wii U")) {
//                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Nintendo+Wii+U"
//                    val urivideogames: Uri = Uri.parse(uriString)
//                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
//                    startActivity(intentvideogames)
//                } else if (selectedItem.contains("Playstation 3")) {
//                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Playstation+3"
//                    val urivideogames: Uri = Uri.parse(uriString)
//                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
//                    startActivity(intentvideogames)
//                } else if (selectedItem.contains("Nintendo Wii")) {
//                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Nintendo+Wii"
//                    val urivideogames: Uri = Uri.parse(uriString)
//                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
//                    startActivity(intentvideogames)
//                } else if (selectedItem.contains("Android")) {
//                    val uriString = "https://play.google.com/store"
//                    val urivideogames: Uri = Uri.parse(uriString)
//                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
//                    startActivity(intentvideogames)
//                } else if (selectedItem.contains("iPhone")) {
//                    val uriString = "http://store.apple.com/us"
//                    val urivideogames: Uri = Uri.parse(uriString)
//                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
//                    startActivity(intentvideogames)
//                } else if (selectedItem.contains("PC")) {
//                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=PC+games"
//                    val urivideogames: Uri = Uri.parse(uriString)
//                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
//                    startActivity(intentvideogames)
//                } else {
//                }*/
//            }
//
//            @Override
//            override fun onNothingSelected(av: AdapterView<*>?) {
//
//            }
//        }

        spin2?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @Override
            override fun onItemSelected(av: AdapterView<*>?, v: View?,
                                        pos: Int, posID: Long) {

                val selectedItem: String = spin2!!.selectedItem.toString()
                val parentItem: String = spin2!!.adapter.getItem(0).toString()
                if (selectedItem.contains("Levis")) {
                    selectedItem.lowercase(Locale.US)
                    val uriString = "http://www.macys.com/m/campaign/levis/levis?cm_mmc=VanityUrl-_-levis-_-n-_-n"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Dockers")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=dockers#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Calvin Klein")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=calvin+klein#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Guess")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=guess#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Lucky Brand")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=lucky+brand#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Nike")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=nike#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Carter")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=carter%27s#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Tommy Hilfiger")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=tommy+hilfiger#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Sony")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Sony-Store/cat15063.c?id=cat15063&pageType=REDIRECT&issolr=1&searchterm=Sony"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("MacBook Air")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=macbook+air"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Apple")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Apple/pcmcat128500050005.c?id=pcmcat128500050005&pageType=REDIRECT&issolr=1&searchterm=Apple"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Samsung")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Samsung/pcmcat140800050115.c?id=pcmcat140800050115&pageType=REDIRECT&issolr=1&searchterm=Samsung"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Acer")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Acer"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Asus")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Asus/pcmcat190000050006.c?id=pcmcat190000050006&pageType=REDIRECT&issolr=1&searchterm=Asus"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Lenovo")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Lenovo/pcmcat230600050000.c?id=pcmcat230600050000&pageType=REDIRECT&issolr=1&searchterm=Lenovo"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Toshiba")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Toshiba/pcmcat136800050058.c?id=pcmcat136800050058&pageType=REDIRECT&issolr=1&searchterm=Toshiba"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Papa Johns")) {
                    val uriString = "http://www.papajohns.com/index.html"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("BJs Restaurants")) {
                    val uriString = "http://www.bjsrestaurants.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("Dennys")) {
                    val uriString = "http://www.dennys.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("California Sushi Roll")) {
                    val uriString = "http://www.california-sushi-roll.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("Taco Bell")) {
                    val uriString = "www.tacobell.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("The Cheesecake Factory")) {
                    val uriString = "http://www.thecheesecakefactory.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("Pizza Hut")) {
                    val uriString = "http://www.pizzahut.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("Carls Jr")) {
                    val uriString = "http://www.carlsjr.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("Romance") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Romance/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Science Fiction") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Science-Fiction/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Technology") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Technology/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Business") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Business/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Health") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Health/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("History") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/History/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Mystery") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Mystery/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Childrens") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Childrens-young-adult-fiction/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Science Fiction") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Mystery") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Horror") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Comedy") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Romantic") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Children's") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Family") && parentItem.contains("Movies")) {
                    val uriString = "http://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Action") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Cat")) {
                    val uriString = "http://www.petco.com/Cat-Home.aspx"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Dog")) {
                    val uriString = "http://www.petco.com/Dog-Home.aspx"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Bird")) {
                    val uriString = "http://www.petco.com/Bird-Home.aspx"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Fish")) {
                    val uriString = "http://www.petco.com/Fish-Home.aspx"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Rabbits")) {
                    val uriString = "http://www.petco.com/Rabbits-Home.aspx?CoreSearch=Rabbit"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Hamsters")) {
                    val uriString = "http://www.petco.com/HamstersHome.aspx?CoreSearch=Hamsters"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Turtles")) {
                    val uriString = "http://www.petco.com/Turtles-Home.aspx?CoreSearch=Turtles"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Snakes")) {
                    val uriString = "http://www.petco.com/Snakes-Home.aspx?CoreSearch=Snakes"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Universal Studios")) {
                    val uriString = "http://www.universalstudios.com/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Magic Mountain")) {
                    val uriString = "https://www.sixflags.com"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Sea World")) {
                    val uriString = "https://www.seaworld.com/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Legoland")) {
                    val uriString = "https://www.legoland.com/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Disneyland")) {
                    val uriString = "https://www.disneyland.com/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("San Diego Wild Animal Park")) {
                    val uriString = "http://www.sdzsafaripark.org/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Disney California")) {
                    val uriString = "http://disneyland.disney.go.com/destinations/disney-california-adventure/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Hurricane Harbor")) {
                    val uriString = "https://www.sixflags.com/hurricaneharborla"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Playstation 4")) {
                    val uriString = "http://www.bestbuy.com/site/Video-Games/PlayStation-4-PS4/pcmcat295700050012.c?id=pcmcat295700050012&pageType=REDIRECT&issolr=1&searchterm=Playstation%204"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("XBOX")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=XBOX"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("Nintendo Wii U")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Nintendo+Wii+U"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("Playstation 3")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Playstation+3"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("Nintendo Wii")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Nintendo+Wii"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("Android")) {
                    val uriString = "https://play.google.com/store"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("iPhone")) {
                    val uriString = "http://store.apple.com/us"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("PC")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=PC+games"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else {
                }
            }

            @Override
            override fun onNothingSelected(av: AdapterView<*>?) {

            }
        }

        spin3?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @Override
            override fun onItemSelected(av: AdapterView<*>?, v: View?,
                                        pos: Int, posID: Long) {

                val selectedItem: String = spin3!!.selectedItem.toString()
                val parentItem: String = spin3!!.adapter.getItem(0).toString()
                if (selectedItem.contains("Levis")) {
                    selectedItem.lowercase(Locale.US)
                    val uriString = "http://www.macys.com/m/campaign/levis/levis?cm_mmc=VanityUrl-_-levis-_-n-_-n"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Dockers")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=dockers#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Calvin Klein")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=calvin+klein#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Guess")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=guess#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Lucky Brand")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=lucky+brand#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Nike")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=nike#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Carter")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=carter%27s#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Tommy Hilfiger")) {
                    val uriString = "http://www1.macys.com/shop/search?keyword=tommy+hilfiger#cm_pv=slp"
                    val uriclothes: Uri = Uri.parse(uriString)
                    val intentclothes = Intent(Intent.ACTION_VIEW, uriclothes)
                    startActivity(intentclothes)
                } else if (selectedItem.contains("Sony")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Sony-Store/cat15063.c?id=cat15063&pageType=REDIRECT&issolr=1&searchterm=Sony"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("MacBook Air")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=MacBook+Air"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Apple")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Apple/pcmcat128500050005.c?id=pcmcat128500050005&pageType=REDIRECT&issolr=1&searchterm=Apple"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Samsung")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Samsung/pcmcat140800050115.c?id=pcmcat140800050115&pageType=REDIRECT&issolr=1&searchterm=Samsung"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Acer")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Acer"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Asus")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Asus/pcmcat190000050006.c?id=pcmcat190000050006&pageType=REDIRECT&issolr=1&searchterm=Asus"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Lenovo")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Lenovo/pcmcat230600050000.c?id=pcmcat230600050000&pageType=REDIRECT&issolr=1&searchterm=Lenovo"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Toshiba")) {
                    val uriString = "http://www.bestbuy.com/site/Brands/Toshiba/pcmcat136800050058.c?id=pcmcat136800050058&pageType=REDIRECT&issolr=1&searchterm=Toshiba"
                    val uricomputers: Uri = Uri.parse(uriString)
                    val intentcomputers = Intent(Intent.ACTION_VIEW, uricomputers)
                    startActivity(intentcomputers)
                } else if (selectedItem.contains("Papa Johns")) {
                    val uriString = "http://www.papajohns.com/index.html"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("BJs Restaurants")) {
                    val uriString = "http://www.bjsrestaurants.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("Dennys")) {
                    val uriString = "http://www.dennys.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("California Sushi Roll")) {
                    val uriString = "http://www.california-sushi-roll.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("Taco Bell")) {
                    val uriString = "www.tacobell.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("The Cheesecake Factory")) {
                    val uriString = "http://www.thecheesecakefactory.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("Pizza Hut")) {
                    val uriString = "http://www.pizzahut.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("Carls Jr")) {
                    val uriString = "http://www.carlsjr.com/"
                    val uridining: Uri = Uri.parse(uriString)
                    val intentdining = Intent(Intent.ACTION_VIEW, uridining)
                    startActivity(intentdining)
                } else if (selectedItem.contains("Romance") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Romance/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Science Fiction") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Science-Fiction/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Technology") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Technology/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Business") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Business/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Health") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Health/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("History") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/History/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Mystery") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Mystery/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Childrens") && parentItem.contains("eBooks")) {
                    val uriString = "http://www.ebooks.com/subjects/Childrens-young-adult-fiction/"
                    val uriebooks: Uri = Uri.parse(uriString)
                    val intentebooks = Intent(Intent.ACTION_VIEW, uriebooks)
                    startActivity(intentebooks)
                } else if (selectedItem.contains("Science Fiction") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Mystery") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Horror") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Comedy") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Romantic") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Children's") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Family") && parentItem.contains("Movies")) {
                    val uriString = "http://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Action") && parentItem.contains("Movies")) {
                    val uriString = "https://www.amctheatres.com/"
                    val urimovies: Uri = Uri.parse(uriString)
                    val intentmovies = Intent(Intent.ACTION_VIEW, urimovies)
                    startActivity(intentmovies)
                } else if (selectedItem.contains("Cat")) {
                    val uriString = "http://www.petco.com/Cat-Home.aspx"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Dog")) {
                    val uriString = "http://www.petco.com/Dog-Home.aspx"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Bird")) {
                    val uriString = "http://www.petco.com/Bird-Home.aspx"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Fish")) {
                    val uriString = "http://www.petco.com/Fish-Home.aspx"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Rabbits")) {
                    val uriString = "http://www.petco.com/Rabbits-Home.aspx?CoreSearch=Rabbit"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Hamsters")) {
                    val uriString = "http://www.petco.com/HamstersHome.aspx?CoreSearch=Hamsters"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Turtles")) {
                    val uriString = "http://www.petco.com/Turtles-Home.aspx?CoreSearch=Turtles"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Snakes")) {
                    val uriString = "http://www.petco.com/Snakes-Home.aspx?CoreSearch=Snakes"
                    val uripets: Uri = Uri.parse(uriString)
                    val intentpets = Intent(Intent.ACTION_VIEW, uripets)
                    startActivity(intentpets)
                } else if (selectedItem.contains("Universal Studios")) {
                    val uriString = "http://www.universalstudios.com/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Magic Mountain")) {
                    val uriString = "https://www.sixflags.com"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Sea World")) {
                    val uriString = "https://www.seaworld.com/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Legoland")) {
                    val uriString = "https://www.legoland.com/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Disneyland")) {
                    val uriString = "https://www.disneyland.com/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("San Diego Wild Animal Park")) {
                    val uriString = "http://www.sdzsafaripark.org/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Disney California")) {
                    val uriString = "http://disneyland.disney.go.com/destinations/disney-california-adventure/"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Hurricane Harbor")) {
                    val uriString = "https://www.sixflags.com/hurricaneharborla"
                    val uritravel: Uri = Uri.parse(uriString)
                    val intenttravel = Intent(Intent.ACTION_VIEW, uritravel)
                    startActivity(intenttravel)
                } else if (selectedItem.contains("Playstation 4")) {
                    val uriString = "http://www.bestbuy.com/site/Video-Games/PlayStation-4-PS4/pcmcat295700050012.c?id=pcmcat295700050012&pageType=REDIRECT&issolr=1&searchterm=Playstation%204"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("XBOX")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=XBOX"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("Nintendo Wii U")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Nintendo+Wii+U"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("Playstation 3")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Playstation+3"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("Nintendo Wii")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=Nintendo+Wii"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("Android")) {
                    val uriString = "https://play.google.com/store"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("iPhone")) {
                    val uriString = "http://store.apple.com/us"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else if (selectedItem.contains("PC")) {
                    val uriString = "http://www.bestbuy.com/site/searchpage.jsp?_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=15&sp=&qp=&list=n&iht=y&usc=All+Categories&ks=960&fs=saas&saas=saas&keys=keys&st=PC+games"
                    val urivideogames: Uri = Uri.parse(uriString)
                    val intentvideogames = Intent(Intent.ACTION_VIEW, urivideogames)
                    startActivity(intentvideogames)
                } else {
                }
            }

            @Override
            override fun onNothingSelected(av: AdapterView<*>?) {

            }
        }
    }

    private fun setAdapter(spinner: Spinner?, d1: Int?) {
        when(d1){
            R.drawable.ic_baseline_shopping ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it, R.array.Clothes) }
            }
            R.drawable.ic_baseline_computers ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it, R.array.Computers) }
            }
            R.drawable.ic_baseline_dining ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it, R.array.Dining) }
            }
            R.drawable.ic_baseline_ebook ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it, R.array.EBooks) }
            }
            R.drawable.ic_baseline_games ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it, R.array.Games) }
            }
            R.drawable.ic_baseline_movies ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it, R.array.Movies) }
            }
            R.drawable.ic_baseline_pets ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it, R.array.Pets) }
            }
            R.drawable.ic_baseline_travel ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it, R.array.Travel) }
            }
            R.drawable.ic_baseline_no_category ->{
                spinner?.adapter = s1adapter?.let { getAdapter(it, R.array.Empty) }
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val gMap: GoogleMap = googleMap
        val granadaHills = LatLng(34.2793576, -118.50215270000001)
        googleMap.addMarker(
            MarkerOptions()
                .position(granadaHills)
                .title("Granada Hills, Ca.")
        )

    }

    private fun getAdapter(adapter: ArrayAdapter<CharSequence>, item: Int): SpinnerAdapter?{
        adapter === ArrayAdapter.createFromResource(this, item, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }



    inner class myGestureListener  //	private String[] data = new String[5];
        : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }


        override fun onSingleTapUp(e: MotionEvent): Boolean {
            // read first name and last name from EditText and then add as Tab label
            return true
        }


        override fun onDoubleTap(e: MotionEvent): Boolean {
           str1 = spin1?.selectedItem as String
           str2 = spin2?.selectedItem as String
           str3 = spin3?.selectedItem as String
            return true
        }
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.wish_list, menu)
        return true
    }

    @Override
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        mDetector?.onTouchEvent(e!!)
        return super.onTouchEvent(e)
    }

    @JvmName("getName1")
    fun getName(): String {
        val first: String = fName?.text.toString()
        val last: String = lName?.text.toString()
        name = "$first $last"
        return name!!
    }

    companion object {

        var wlAdapter: SimpleCursorAdapter? = null
        var str1: String? = null
        var str2: String? = null
        var str3: String? = null
    }


}
