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
class AccelerometerFragment : Fragment() {

    var accelerometers: List<Sensor>? = null
    var listeners: MutableMap<Sensor, SensorEventListener>? = null

    private val sensorManager: SensorManager
        get() = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    var lastUpdateTime = System.currentTimeMillis();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sensorManager = sensorManager
        accelerometers = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        listeners = mutableMapOf()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.accelerometer_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accelerometeresContainer = view.findViewById(R.id.accelerometers) as LinearLayout

        val inflater = LayoutInflater.from(activity)
        accelerometers?.forEach { accelerometer ->
            val accelerometerView = inflater.inflate(R.layout.accelerometer_view, accelerometeresContainer, false)
            val name = accelerometerView.findViewById(R.id.name) as TextView
            val vendor = accelerometerView.findViewById(R.id.vendor) as TextView
            val version = accelerometerView.findViewById(R.id.version) as TextView
            val accuracy = accelerometerView.findViewById(R.id.accuracy) as TextView
            val gx = accelerometerView.findViewById(R.id.gx) as TextView
            val gy = accelerometerView.findViewById(R.id.gy) as TextView
            val gz = accelerometerView.findViewById(R.id.gz) as TextView

            name.text = "Name: ${accelerometer.name}"
            vendor.text = "Vendor: ${accelerometer.vendor}"
            version.text = "Version: ${accelerometer.version}"

            accelerometeresContainer.addView(accelerometerView)

            listeners?.put(accelerometer, object : SensorEventListener {
                override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                    accuracy.text = "Accuracy: ${p1}"
                }

                override fun onSensorChanged(p0: SensorEvent?) {
                    if (System.currentTimeMillis() - lastUpdateTime >= 500L) {
                        gx.text = "Gx: ${p0?.values?.get(0)} m/s2"
                        gy.text = "Gy: ${p0?.values?.get(1)} m/s2"
                        gz.text = "Gz: ${p0?.values?.get(2)} m/s2"
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