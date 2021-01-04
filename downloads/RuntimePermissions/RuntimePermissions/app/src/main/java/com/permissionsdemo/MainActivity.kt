package com.permissionsdemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //Permission code that will be checked in the method onRequestPermissionsResult
    private val ALL_PERMISSIONS_CODE = 123
    private val STORAGE_PERMISSION_CODE = 1
    private val LOCATION_PERMISSION_CODE = 2
    private val CAMERA_PERMISSION_CODE = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btnAllPermissions.setOnClickListener { view ->

            /*Requests permissions to be granted to this application. These permissions
             must be requested in your manifest, they should not be granted to your app,
             and they should have protection level*/
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                ALL_PERMISSIONS_CODE
            )
        }

        btnStoragePermission.setOnClickListener { view ->

            //First checking if the app is already having the permission
            if (isPermissionAllowed(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //If permission is already having then showing the toast
                Toast.makeText(
                    this@MainActivity,
                    "You already have the permission for storage.",
                    Toast.LENGTH_LONG
                ).show()
                //Existing the method with return
                return@setOnClickListener
            }

            //If the app has not the permission then asking for the permission
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)
        }

        btnCameraPermission.setOnClickListener { view ->

            //First checking if the app is already having the permission
            if (isPermissionAllowed(Manifest.permission.CAMERA)) {
                //If permission is already having then showing the toast
                Toast.makeText(
                    this@MainActivity,
                    "You already have the permission for camera.",
                    Toast.LENGTH_LONG
                ).show()
                //Existing the method with return
                return@setOnClickListener
            }

            //If the app has not the permission then asking for the permission
            requestPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
        }

        btnLocationPermission.setOnClickListener { view ->

            //First checking if the app is already having the permission
            if (isPermissionAllowed(Manifest.permission.ACCESS_FINE_LOCATION)) {
                //If permission is already having then showing the toast
                Toast.makeText(
                    this@MainActivity,
                    "You already have the permission for location.",
                    Toast.LENGTH_LONG
                ).show()
                //Existing the method with return
                return@setOnClickListener
            }

            //If the app has not the permission then asking for the permission
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE)
        }
    }

    /**
     * This function will notify the user after tapping on allow or deny
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {

        if (requestCode == ALL_PERMISSIONS_CODE) {
            val perms =
                HashMap<String, Int>()
            // Initial
            perms[Manifest.permission.ACCESS_FINE_LOCATION] = PackageManager.PERMISSION_GRANTED
            perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
            perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
            // Fill with results
            for (i in permissions.indices)
                perms[permissions[i]] = grantResults[i]
            if (perms[Manifest.permission.ACCESS_FINE_LOCATION] == PackageManager.PERMISSION_GRANTED
                && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                && perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
            ) {
                // All Permissions Granted
                Toast.makeText(
                    this@MainActivity,
                    "All Permissions granted.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Permission Denied
                Toast.makeText(
                    this@MainActivity,
                    "Some permissions are denied. You can also allow it from settings.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(
                    this,
                    "Permission granted now you can read the storage",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    "Oops you just denied the permission for storage. You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else if (requestCode == LOCATION_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted for location.", Toast.LENGTH_LONG).show()
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    "Oops you just denied the permission for location. You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else if (requestCode == CAMERA_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted for camera.", Toast.LENGTH_LONG).show()
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    "Oops you just denied the permission for camera. You can also allow it from settings.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * We are calling this method to check the permission status on runtime
     */
    private fun isPermissionAllowed(sPermission: String): Boolean {

        //Getting the permission status
        val result = ContextCompat.checkSelfPermission(this, sPermission)

        //If permission is granted returning true or false
        return if (result == PackageManager.PERMISSION_GRANTED) true else false
    }

    //Requesting permission
    private fun requestPermission(sPermissionName: String, iRequestCode: Int) {

        // Gets whether you should show UI with rationale for requesting a permission.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, sPermissionName)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        /*Requests permissions to be granted to this application. These permissions
             must be requested in your manifest, they should not be granted to your app,
             and they should have protection level*/
        ActivityCompat.requestPermissions(this, arrayOf(sPermissionName), iRequestCode)
    }
}