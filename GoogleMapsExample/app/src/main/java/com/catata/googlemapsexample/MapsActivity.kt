package com.catata.googlemapsexample

import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.catata.googlemapsexample.databinding.ActivityMapsBinding
import android.graphics.Bitmap

import android.graphics.drawable.BitmapDrawable
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.LatLngBounds
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationCallback

import com.google.android.gms.location.LocationSettingsResponse

import com.google.android.gms.tasks.OnSuccessListener

import com.google.android.gms.location.LocationServices

import com.google.android.gms.location.SettingsClient

import com.google.android.gms.location.LocationSettingsRequest

import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import android.content.Context

import android.content.Intent

import android.content.DialogInterface

import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import java.lang.Exception


const val REQUEST_PERSMISSION_CODE = 1000



class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var mLatitude = 0.0
    private var mLongitude = 0.0
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create()
        locationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest!!.interval = 16000 //16seconds
        locationRequest!!.fastestInterval = 8000//8seconds
        locationRequest.smallestDisplacement = 0f


        locationCallback = object:LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for ( location in locationResult.locations) {
                    if (location != null) {
                        mLatitude = location.latitude
                        mLongitude = location.longitude
                        Toast.makeText(this@MapsActivity, "Lat: " + mLatitude + "  Lon: " +
                                mLongitude, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

        var titles:String = ""
        val locations = Location.readLocations(this)
        for (location:Location in locations){
            titles += "- " + location.title + " "
        }
        Toast.makeText(this, "Titles: $titles", Toast.LENGTH_SHORT).show()



}

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val torrent = LatLng(39.4295152, -0.4660814)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        val markerTorrent = mMap.addMarker(MarkerOptions().position(torrent).title("IES Serra Perenxisa").snippet("Torrent"))
        markerTorrent.tag = "We are in IES Serra Perenxisa"
        markerTorrent.setIcon(BitmapDescriptorFactory.fromBitmap(getIcon(50,50, R.mipmap.my_marker)))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(40.0,0.0)))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10f))


        mMap.setOnInfoWindowClickListener(this)

        /*mMap.setOnMarkerClickListener {
            marker ->
            Toast.makeText(this, "Click on Marker",Toast.LENGTH_SHORT).show()
            return@setOnMarkerClickListener true

        }*/

        val MyAREA = LatLngBounds(LatLng(37.5, -3.0), LatLng(41.5, 2.0))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MyAREA.center, 7.5f))

        mMap.setMinZoomPreference(8.0f);
        mMap.setMaxZoomPreference(16.0f);

        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        /*mMap.setOnCameraIdleListener {
            Toast.makeText(this,
                "Zoom changed to: " + mMap.cameraPosition.zoom,
                Toast.LENGTH_SHORT).show()
        }*/

        mMap.uiSettings.isCompassEnabled = false
        mMap.uiSettings.isRotateGesturesEnabled = false
        mMap.uiSettings.isMapToolbarEnabled = false

       /* mMap.setOnMapClickListener { latlng ->
            Toast.makeText(this,
                "Map clicked on lat: ${latlng.latitude}, long: ${latlng.longitude}",
                Toast.LENGTH_SHORT).show()
        }*/

        /*
        val SYDNEY = LatLng(-33.88,151.21)
        val MOUNTAIN_VIEW = LatLng(37.4, -122.1)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15f)) //Starts with zoom of 15 de zoom
        mMap.animateCamera(CameraUpdateFactory.zoomIn())
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f), 5000, null) //5 seconds
        val cameraPosition = CameraPosition.Builder()
            .target(MOUNTAIN_VIEW)   //final destination
                .zoom(17f)	//new final zoom
                .bearing(90f)	//camera orientation to the east
                .tilt(30f)	//camera to 30 degrees
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/



        requestLocations()
        enableLocation()

    }

    override fun onInfoWindowClick(marker: Marker?) {
        Toast.makeText(this, "Marker pressed on: \n${marker?.tag}( ${marker?.position?.latitude}" +
                " ${marker?.position?.longitude})", Toast.LENGTH_SHORT).show();
    }

    private fun getIcon(height:Int, width:Int, resource:Int):Bitmap{
        val bitmapdraw = resources.getDrawable(resource,null) as BitmapDrawable
        val b = bitmapdraw.bitmap
        return Bitmap.createScaledBitmap(b, width, height, false)
    }

    private fun isLocationGranted(){
        //Just fine location, if we haven't permission we should stop the app
        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }

        /*
         * This is a more complex option but better
         * Here we need both location systems and if we're no granted we request for them*********/
        if (ActivityCompat.checkSelfPermission(this@MapsActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this@MapsActivity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this@MapsActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERSMISSION_CODE)
        } else {
            mMap.isMyLocationEnabled = true
        }


    }

    private fun requestLocations() {
        if (ActivityCompat.checkSelfPermission(this@MapsActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MapsActivity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this@MapsActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERSMISSION_CODE)
        } else {
            mFusedLocationClient!!.requestLocationUpdates(locationRequest!!,
                locationCallback!!, Looper.getMainLooper())
        }
    }

    private fun removeLocations() {
        mFusedLocationClient!!.removeLocationUpdates(locationCallback!!)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_PERSMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            requestLocations()
        }else{
            Toast.makeText(this, "You need allow to access location in this app", Toast.LENGTH_SHORT).show()
        }
    }


    private fun enableLocation(){
        val lm:LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled = false
        var network_enabled = false

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Network not enabled")
                .setPositiveButton("Open Settings",
                    DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    })
                .setNegativeButton("Cancel", null)
                .show()
        }
    }




}