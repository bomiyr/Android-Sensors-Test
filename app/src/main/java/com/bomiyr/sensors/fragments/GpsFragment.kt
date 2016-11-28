package com.bomiyr.sensors.fragments

import android.content.IntentSender.SendIntentException
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bomiyr.sensors.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*


/**
 * Created by Misha on 28.11.2016.
 */
class GpsFragment : Fragment(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    val REQUEST_CHECK_SETTINGS = 110

    lateinit private var googleApiClient: GoogleApiClient

    lateinit private var statusView: TextView
    lateinit private var providerView: TextView
    lateinit private var latitudeView: TextView
    lateinit private var longitudeView: TextView
    lateinit private var altitudeView: TextView
    lateinit private var accuracyView: TextView
    lateinit private var speedView: TextView
    lateinit private var timeView: TextView
    lateinit private var bearingView: TextView

    var locationUpdateStarted = false

    val locationRequest: LocationRequest by lazy {
        val value = LocationRequest()
        value.interval = 1000
        value.fastestInterval = 500
        value.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        if(savedInstanceState != null){
            locationUpdateStarted = savedInstanceState.getBoolean("locationUpdateStarted", false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statusView = view.findViewById(R.id.status) as TextView
        providerView = view.findViewById(R.id.provider) as TextView
        latitudeView = view.findViewById(R.id.latitude) as TextView
        longitudeView = view.findViewById(R.id.longitude) as TextView
        altitudeView = view.findViewById(R.id.altitude) as TextView
        accuracyView = view.findViewById(R.id.accuracy) as TextView
        speedView = view.findViewById(R.id.speed) as TextView
        timeView = view.findViewById(R.id.time) as TextView
        bearingView = view.findViewById(R.id.bearing) as TextView
    }

    override fun onStart() {
        googleApiClient.connect()
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

        if (googleApiClient.isConnected && locationUpdateStarted) {
            startLocationUpdates();
        }
    }

    override fun onPause() {
        super.onPause()

        stopLocationUpdates();
    }

    override fun onStop() {
        googleApiClient.disconnect()
        super.onStop()
    }

    override fun onConnected(p0: Bundle?) {
        statusView.text = "CONNECTED"
        statusView.setTextColor(Color.GREEN)
        statusView.visibility = View.GONE

        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)


        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                builder.build())

        result.setResultCallback {
            val locationSettingsStates = it.getLocationSettingsStates();

            when (it.status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
                    startLocationUpdates()
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        it.status.startResolutionForResult(
                                activity,
                                REQUEST_CHECK_SETTINGS)
                    } catch (e: SendIntentException) {
                        // Ignore the error.
                    }

                }

            }

        }
    }

    override fun onConnectionSuspended(p0: Int) {
        statusView.text = "SUSPENDED"
        statusView.setTextColor(Color.YELLOW)
        statusView.visibility = View.VISIBLE
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        statusView.text = "FAILED"
        statusView.setTextColor(Color.RED)
        statusView.visibility = View.VISIBLE
    }

    protected fun startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this)
        locationUpdateStarted = true
    }

    protected fun stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this)
    }

    override fun onLocationChanged(p0: Location?) {
        providerView.text = "Provider: ${p0?.provider}"
        latitudeView.text = "lat: ${p0?.latitude}"
        longitudeView.text = "long: ${p0?.longitude}"
        altitudeView.text = "alt: ${p0?.altitude}"
        accuracyView.text = "Accuracy: ${p0?.accuracy}"
        speedView.text = "Speed: ${p0?.speed}"
        timeView.text = "Time: ${p0?.time}"
        bearingView.text = "Bearing: ${p0?.bearing}"
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean("locationUpdateStarted", locationUpdateStarted)
    }
}
