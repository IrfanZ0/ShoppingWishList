package com.tritongames.shoppingwishlist.presentation


import android.net.Uri
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.google.ar.core.exceptions.UnavailableException
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.rendering.ModelRenderable
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.repository.bestbuy.BestBuyRepository
import com.tritongames.shoppingwishlist.data.repository.category.ShopCategoryRepository
import com.tritongames.shoppingwishlist.data.repository.userPreferences.UserPreferenceRepository
import com.tritongames.shoppingwishlist.data.viewmodels.BestBuyViewModel
import com.tritongames.shoppingwishlist.data.viewmodels.ShoppingDataViewModel
import com.tritongames.shoppingwishlist.util.ARSessionManager
import com.tritongames.shoppingwishlist.util.BBViewModelFactory
import com.tritongames.shoppingwishlist.util.DispatcherProvider
import com.tritongames.shoppingwishlist.util.ShopDataViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.CompletableFuture
import javax.inject.Inject


@AndroidEntryPoint
class ShoppingARView : AppCompatActivity() {

    private lateinit var imageGrid: LinearLayout
    private lateinit var imgView: ImageView
    private lateinit var bbVM: BestBuyViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var shopDataVM: ShoppingDataViewModel
    private lateinit var arSceneView: ArSceneView
    private lateinit var laptopRenderable: ModelRenderable
    private val laptopFilePath: String = "app/sampledata/models/Laptops/Laptop.glb"
    private var cameraPermission = false
    private val arSessionManager: ARSessionManager = ARSessionManager()
    private lateinit var frame: Frame
    private val RC_PERMISSIONS = 0x123
    private var hasPlacedRenderable = false
    private var gestureDetector: GestureDetector? = null
    private var loadingMessageSnackbar: Snackbar? = null
    private var cameraPermissionRequested = false
    private lateinit var recyclerV: RecyclerView
    private lateinit var merchImageAdapter: MerchandiseImagesAdapter
    // True once scene is loaded
    private var hasFinishedLoading = false
    // True once the scene has been placed.
    private var hasPlacedMerchandise = false




    @Inject
    lateinit var bbRepository : BestBuyRepository
    @Inject
    lateinit var userPreferenceRepository: UserPreferenceRepository
    @Inject
    lateinit var dispatchers: DispatcherProvider
    @Inject
    lateinit var shopCatRepository : ShopCategoryRepository
    @Inject
    lateinit var dataStore: DataStore<androidx.datastore.preferences.core.Preferences>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_arview)

        arSceneView = findViewById(R.id.ar_scene_view)


        // Set up a tap gesture detector.
        gestureDetector = GestureDetector(
            this,
            object : SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    onSingleTap(e)
                    return true
                }

                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }
            })
        // Set a touch listener on the Scene to listen for taps.
        arSceneView
            .scene
            .setOnTouchListener { hitTestResult: HitTestResult?, event: MotionEvent? ->
                // If the solar system hasn't been placed yet, detect a tap and then check to see if
                // the tap occurred on an ARCore plane to place the solar system.
                if (!hasPlacedMerchandise) {
                    return@setOnTouchListener gestureDetector!!.onTouchEvent(event!!)
                }
                false
            }

        // Set an update listener on the Scene that will hide the loading message once a Plane is
        // detected.

        // Set an update listener on the Scene that will hide the loading message once a Plane is
        // detected.
        arSceneView
            .scene
            .addOnUpdateListener { frameTime: FrameTime? ->
                if (loadingMessageSnackbar == null) {
                    return@addOnUpdateListener
                }
                val frame = arSceneView.arFrame ?: return@addOnUpdateListener
                if (frame.camera.trackingState != TrackingState.TRACKING) {
                    return@addOnUpdateListener
                }
                for (plane in frame.getUpdatedTrackables(Plane::class.java)) {
                    if (plane.trackingState === TrackingState.TRACKING) {
                        hideLoadingMessage()
                    }
                }
            }

        // Lastly request CAMERA permission which is required by ARCore.

        // Lastly request CAMERA permission which is required by ARCore.
        arSessionManager.requestCameraPermission(this, RC_PERMISSIONS)
       /* supportFragmentManager.commitNow {
            setReorderingAllowed(true)
            val myShopFragment = ShoppingARFragment()
            add(R.id.fragment_container_view, myShopFragment)
        }*/

        //arFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as ArFragment

        arSessionManager.requestCameraPermission(this, RC_PERMISSIONS)


        viewModelFactory =  BBViewModelFactory(bbRepository, userPreferenceRepository, dispatchers)
        bbVM = ViewModelProvider(this@ShoppingARView, viewModelFactory )[BestBuyViewModel::class.java]

        viewModelFactory =  ShopDataViewModelFactory(shopCatRepository, dispatchers, dataStore)
        shopDataVM = ViewModelProvider(this@ShoppingARView, viewModelFactory )[ShoppingDataViewModel::class.java]

        imgView = ImageView(this@ShoppingARView)
        recyclerV = findViewById(R.id.recView)
        val layoutManagerVertical = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerV.layoutManager = layoutManagerVertical
        val imageList = mutableListOf("sampledata/Laptop0120.jpg")

        merchImageAdapter = MerchandiseImagesAdapter(imageList)
        recyclerV.adapter = merchImageAdapter



        //  imageGrid = findViewById(R.id.image_grid)

        /*for(img in bbVM.getAllPS4Images()){
            Picasso.get().load(img.toUri()).into(imgView)
            imageGrid.addView(imgView)
        }*/

//      imgView.setImageURI(Uri.parse("C:\\Github\\ShoppingWishList\\app\\sampledata\\drawable\\plasmaTV60.jpg"))
//      imageGrid.addView(imgView)

//      for(img in shopDataVM.getProductImages()){
//         imgView.setImageURI(Uri.parse(img))
//          imageGrid.addView(imgView)
//      }



    }



override fun onResume() {
    super.onResume()
    if (arSceneView.session == null) {
        // If the session wasn't created yet, don't resume rendering.
        // This can happen if ARCore needs to be updated or permissions are not granted yet.
        try {
            val lightEstimationMode = Config.LightEstimationMode.AMBIENT_INTENSITY
            val session: Session? =
                if (cameraPermissionRequested) arSessionManager.createArSessionWithInstallRequest(
                    this,
                    lightEstimationMode
                ) else arSessionManager.createArSessionNoInstallRequest(this, lightEstimationMode)
            if (session == null) {
                cameraPermissionRequested = arSessionManager.hasCameraPermission(this)
                return
            } else {
                arSceneView.setupSession(session)
            }
        } catch (e: UnavailableException) {
            arSessionManager.handleSessionException(this, e)
        }
    }

    try {
        arSceneView.resume()
    } catch (ex: CameraNotAvailableException) {
        arSessionManager.displayError(this, "Unable to get camera", ex)
        finish()
        return
    }

    if (arSceneView.session != null) {
        showLoadingMessage()
    }
}

override fun onPause() {
    super.onPause()
    arSceneView.pause()
}

override fun onDestroy() {
    super.onDestroy()
    arSceneView.destroy()
}

    private fun onSingleTap(tap: MotionEvent) {
        if (!hasFinishedLoading) {
            // We can't do anything yet.
            return;
        }

        val frame: Frame? = arSceneView.arFrame;
        if (frame != null) {
            if (!hasPlacedMerchandise && tryPlaceMerchandise(tap, frame)) {
                hasPlacedMerchandise = true;
            }
        }
    }
private fun get3DModel(file: String): CompletableFuture<ModelRenderable> {

    val model3DRenderable = ModelRenderable.builder() //ModelRenderer.Builder
        .setSource(this, Uri.parse(file)) //Renderable
        .build() //CompletableFuture<Renderable>

    hasFinishedLoading = true
    return model3DRenderable
}
    private fun getMerchandiseNode(): Node {
        val merchNode : Node = Node()
        merchNode.renderable = get3DModel(laptopFilePath).get()
        return merchNode
    }

    private fun tryPlaceMerchandise(tap: MotionEvent?, frame: Frame): Boolean {
        if (tap != null && frame.camera.trackingState == TrackingState.TRACKING) {
            for (hit in frame.hitTest(tap)) {
                val trackable = hit.trackable
                if (trackable is Plane && trackable.isPoseInPolygon(hit.hitPose)) {
                    // Create the Anchor.
                    val anchor = hit.createAnchor()
                    val anchorNode = AnchorNode(anchor)
                    anchorNode.setParent(arSceneView.scene)
                    val merchandiseNode: Node = getMerchandiseNode()
                    anchorNode.addChild(merchandiseNode)
                    return true
                }
            }
        }
        return false
    }

    private fun showLoadingMessage() {
        if (loadingMessageSnackbar != null && loadingMessageSnackbar!!.isShownOrQueued) {
            return
        }
        loadingMessageSnackbar = Snackbar.make(
            this@ShoppingARView.findViewById(android.R.id.content),
            com.tritongames.shoppingwishlist.R.string.plane_finding,
            Snackbar.LENGTH_INDEFINITE
        )
        loadingMessageSnackbar!!.view.setBackgroundColor(-0x40cdcdce)
        loadingMessageSnackbar!!.show()
    }

    private fun hideLoadingMessage() {
        if (loadingMessageSnackbar == null) {
            return
        }
        loadingMessageSnackbar!!.dismiss()
        loadingMessageSnackbar = null
    }
override fun onRequestPermissionsResult(
    requestCode: Int, permissions: Array<String?>, results: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, results)
    if (!arSessionManager.hasCameraPermission(this)) {
        if (!arSessionManager.shouldShowRequestPermissionRationale(this)) {
            // Permission denied with checking "Do not ask again".
            arSessionManager.launchPermissionSettings(this)
        } else {
            Toast.makeText(
                this, "Camera permission is needed to run this@ShoppingARView application", Toast.LENGTH_LONG
            )
                .show()
        }
        finish()
    }
}


    /*override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Standard Android full-screen functionality.
            window
                .decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }*/


}

