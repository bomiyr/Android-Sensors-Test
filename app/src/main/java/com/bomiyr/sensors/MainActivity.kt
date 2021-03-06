package com.bomiyr.sensors

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.bomiyr.sensors.fragments.AccelerometerFragment
import com.bomiyr.sensors.fragments.GpsFragment
import com.bomiyr.sensors.fragments.GyroscopeFragment
import com.bomiyr.sensors.fragments.RotationVectorFragment

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val toolbar = findViewById(R.id.toolbar) as Toolbar?
//        setSupportActionBar(toolbar)

        val systemService = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val accelerometer = systemService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer != null) {
            var accelerometerFragment = supportFragmentManager.findFragmentByTag("accelerometer")
            if(accelerometerFragment == null){
                accelerometerFragment = AccelerometerFragment()
                supportFragmentManager.beginTransaction().add(R.id.activity_main, accelerometerFragment, "accelerometer").commit()
            }
        }

        val gyroscope = systemService.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyroscope != null) {
            var gyroscopeFragment = supportFragmentManager.findFragmentByTag("gyroscope")
            if(gyroscopeFragment == null){
                gyroscopeFragment = GyroscopeFragment()
                supportFragmentManager.beginTransaction().add(R.id.activity_main, gyroscopeFragment, "gyroscope").commit()
            }
        }

        val rotationVector = systemService.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        if (rotationVector != null) {
            var rotationVectorFragment = supportFragmentManager.findFragmentByTag("rotation_vector")
            if(rotationVectorFragment == null){
                rotationVectorFragment = RotationVectorFragment()
                supportFragmentManager.beginTransaction().add(R.id.activity_main, rotationVectorFragment, "rotation_vector").commit()
            }
        }

        if (rotationVector != null) {
            var gpsFragment = supportFragmentManager.findFragmentByTag("gps")
            if(gpsFragment == null){
                gpsFragment = GpsFragment()
                supportFragmentManager.beginTransaction().add(R.id.activity_main, gpsFragment, "gps").commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.settings) {
            startSettingsActivity(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
