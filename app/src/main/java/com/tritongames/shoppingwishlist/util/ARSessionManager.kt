package com.tritongames.shoppingwishlist.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.ar.core.ArCoreApk
import com.google.ar.core.ArCoreApk.InstallStatus
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableApkTooOldException
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException
import com.google.ar.core.exceptions.UnavailableException
import com.google.ar.core.exceptions.UnavailableSdkTooOldException


class ARSessionManager {
    private val TAG = "SceneformDemoUtils"
    private val MIN_OPENGL_VERSION = 3.0

    private fun ARSessionManager() {}

    /**
     * Creates and shows a Toast containing an error message. If there was an exception passed in it
     * will be appended to the toast. The error will also be written to the Log
     */
    fun displayError(
        context: Context, errorMsg: String, problem: Throwable?
    ) {
        val tag = context.javaClass.simpleName
        val toastText: String = if (problem?.message != null) {
            Log.e(tag, errorMsg, problem)
            errorMsg + ": " + problem.message
        } else if (problem != null) {
            Log.e(tag, errorMsg, problem)
            errorMsg
        } else {
            Log.e(tag, errorMsg)
            errorMsg
        }
        Handler(Looper.getMainLooper())
            .post {
                val toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
    }

    @Throws(UnavailableException::class)
    private fun createArSession(
        activity: Context?, installRequested: Boolean, lightEstimationMode: Config.LightEstimationMode
    ): Session? {
        var installRequest = installRequested
        var session: Session? = null
        // if we have the camera permission, create the session
        if (hasCameraPermission(activity)) {
            when (ArCoreApk.getInstance().requestInstall(activity as Activity?, !installRequested)) {
                InstallStatus.INSTALL_REQUESTED -> {
                    installRequest = true
                    return null
                }

                InstallStatus.INSTALLED -> {
                    installRequest = false

                }
            }
            session = Session(activity)
            // IMPORTANT!!!  ArSceneView requires the `LATEST_CAMERA_IMAGE` non-blocking update mode.
            val config = Config(session)
            config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
            config.lightEstimationMode = lightEstimationMode
            session.configure(config)
        }
        return session
    }

    /**
     * Creates an ARCore session, requesting ARCore installation if necessary. This checks for the
     * CAMERA permission, and if granted, checks the state of the ARCore installation and requests
     * installation if not already installed. If there is a problem an exception is thrown. Care must
     * be taken to update the installRequested flag as needed to avoid an infinite checking loop. It
     * should be set to true if null is returned from this method, and called again when the
     * application is resumed.
     *
     * @param activity - the activity currently active.
     * @param lightEstimationMode - the light estimation mode to be set on the returned session.
     */
    @Throws(UnavailableException::class)
    fun createArSessionWithInstallRequest(
        activity: Context?, lightEstimationMode: Config.LightEstimationMode
    ): Session? {
        return createArSession(activity, true, lightEstimationMode)
    }

    /**
     * Creates an ARCore session, but does not install ARCore even if it is unavailable. This checks
     * for the CAMERA permission, and if granted, checks the state of the ARCore installation. If
     * there is a problem an exception is thrown. Care must be taken to update the installRequested
     * flag as needed to avoid an infinite checking loop. It should be set to true if null is returned
     * from this method, and called again when the application is resumed.
     *
     * @param activity - the activity currently active.
     * @param lightEstimationMode - the light estimation mode to be set on the returned session.
     */
    @Throws(UnavailableException::class)
    fun createArSessionNoInstallRequest(
        activity: Context?, lightEstimationMode: Config.LightEstimationMode
    ): Session? {
        return createArSession(activity, false, lightEstimationMode)
    }

    /** Check to see we have the necessary permissions for this app, and ask for them if we don't.  */
    fun requestCameraPermission(activity: Context?, requestCode: Int) {
        ActivityCompat.requestPermissions(
            activity!! as Activity, arrayOf<String>(android.Manifest.permission.CAMERA), requestCode
        )
    }

    /** Check to see we have the necessary permissions for this app.  */
    fun hasCameraPermission(activity: Context?): Boolean {
        return (ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
    }

    /** Check to see if we need to show the rationale for this permission.  */
    fun shouldShowRequestPermissionRationale(activity: Context?): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            activity!! as Activity, android.Manifest.permission.CAMERA
        )
    }

    /** Launch Application Setting to grant permission.  */
    fun launchPermissionSettings(activity: Context?) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        if (activity != null) {
            intent.data = Uri.fromParts("package", activity.packageName, null)
        }
        activity?.startActivity(intent)
    }

    fun handleSessionException(
        activity: Context?, sessionException: UnavailableException
    ) {
        val message: String
        if (sessionException is UnavailableArcoreNotInstalledException) {
            message = "Please install ARCore"
        } else if (sessionException is UnavailableApkTooOldException) {
            message = "Please update ARCore"
        } else if (sessionException is UnavailableSdkTooOldException) {
            message = "Please update this app"
        } else if (sessionException is UnavailableDeviceNotCompatibleException) {
            message = "This device does not support AR"
        } else {
            message = "Failed to create AR session"
            Log.e(TAG, "Exception: $sessionException")
        }
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     *
     * Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     *
     * Finishes the activity if Sceneform can not run
     */
    fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
        val openGlVersionString =
            (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later")
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        return true
    }
}