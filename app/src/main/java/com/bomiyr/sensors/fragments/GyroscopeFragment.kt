package com.bomiyr.sensors.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bomiyr.sensors.R

/**
 * Created by Misha on 27.11.2016.
 */
class GyroscopeFragment : Fragment() {

    var gyroscopes: List<Sensor>? = null
    var listeners: MutableMap<Sensor, SensorEventListener>? = null

    var lastUpdateTime = System.currentTimeMillis();

    private val sensorManager: SensorManager
        get() = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sensorManager = sensorManager
        gyroscopes = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
        listeners = mutableMapOf()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.gyroscope_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gyroscopesContainer = view.findViewById(R.id.gyroscopes) as LinearLayout

        val inflater = LayoutInflater.from(activity)
        gyroscopes?.forEach { gyroscope ->
            val gyroscopeView = inflater.inflate(R.layout.gyroscope_view, gyroscopesContainer, false)
            val name = gyroscopeView.findViewById(R.id.name) as TextView
            val vendor = gyroscopeView.findViewById(R.id.vendor) as TextView
            val version = gyroscopeView.findViewById(R.id.version) as TextView
            val accuracy = gyroscopeView.findViewById(R.id.accuracy) as TextView
            val vX = gyroscopeView.findViewById(R.id.angular_speed_x) as TextView
            val vY = gyroscopeView.findViewById(R.id.angular_speed_y) as TextView
            val vZ = gyroscopeView.findViewById(R.id.angular_speed_z) as TextView

            name.text = "Name: ${gyroscope.name}"
            vendor.text = "Vendor: ${gyroscope.vendor}"
            version.text = "Version: ${gyroscope.version}"

            gyroscopesContainer.addView(gyroscopeView)

            listeners?.put(gyroscope, object : SensorEventListener {
                override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                    accuracy.text = "Accuracy: ${p1}"
                }

                override fun onSensorChanged(p0: SensorEvent?) {

                    if(System.currentTimeMillis() - lastUpdateTime >= 500L) {

                        vX.text = "vX: ${p0?.values?.get(0)} rad/s"
                        vY.text = "vY: ${p0?.values?.get(1)} rad/s"
                        vZ.text = "vZ: ${p0?.values?.get(2)} rad/s"
                        accuracy.text = "Accuracy: ${p0?.accuracy}"

                        lastUpdateTime = System.currentTimeMillis()
                    }
                }
            })

        }
    }

    override fun onResume() {
        super.onResume()
        listeners?.forEach { sensorManager.registerListener(it.value, it.key, SensorManager.SENSOR_DELAY_UI) }


    }

    override fun onPause() {
        super.onPause()

        listeners?.forEach { sensorManager.unregisterListener(it.value, it.key) }
    }
}