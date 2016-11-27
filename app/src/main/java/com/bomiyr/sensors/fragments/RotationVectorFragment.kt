package com.bomiyr.sensors.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bomiyr.sensors.R

/**
 * Created by Misha on 27.11.2016.
 */
class RotationVectorFragment : Fragment() {

    var rotationVectors: List<Sensor>? = null
    var listeners: MutableMap<Sensor, SensorEventListener>? = null

    private val sensorManager: SensorManager
        get() = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    var lastUpdateTime = System.currentTimeMillis();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sensorManager = sensorManager
        rotationVectors = sensorManager.getSensorList(Sensor.TYPE_ROTATION_VECTOR);
        listeners = mutableMapOf()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.rotation_vector_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rotationVectorsContainer = view.findViewById(R.id.rotation_vectors) as LinearLayout

        val inflater = LayoutInflater.from(activity)
        rotationVectors?.forEach { rotationVector ->
            val rotationVectorView = inflater.inflate(R.layout.rotation_vector_view, rotationVectorsContainer, false)
            val name = rotationVectorView.findViewById(R.id.name) as TextView
            val vendor = rotationVectorView.findViewById(R.id.vendor) as TextView
            val version = rotationVectorView.findViewById(R.id.version) as TextView
            val accuracy = rotationVectorView.findViewById(R.id.accuracy) as TextView
            val x = rotationVectorView.findViewById(R.id.x) as TextView
            val y = rotationVectorView.findViewById(R.id.y) as TextView
            val z = rotationVectorView.findViewById(R.id.z) as TextView
            val cosView = rotationVectorView.findViewById(R.id.z) as TextView
            val estimatedHeadingAccuracyView = rotationVectorView.findViewById(R.id.estimated_heading_accuracy) as TextView

            name.text = "Name: ${rotationVector.name}"
            vendor.text = "Vendor: ${rotationVector.vendor}"
            version.text = "Version: ${rotationVector.version}"

            rotationVectorsContainer.addView(rotationVectorView)

            listeners?.put(rotationVector, object : SensorEventListener {
                override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                    accuracy.text = "Accuracy: ${p1}"
                }

                override fun onSensorChanged(p0: SensorEvent?) {
                    if (System.currentTimeMillis() - lastUpdateTime >= 500L) {
                        x.text = "x*sin(θ/2): ${p0?.values?.get(0)}"
                        y.text = "y*sin(θ/2): ${p0?.values?.get(1)}"
                        z.text = "z*sin(θ/2): ${p0?.values?.get(2)}"
                        accuracy.text = "Accuracy: ${p0?.accuracy}"

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                            cosView.visibility = View.VISIBLE
                            estimatedHeadingAccuracyView.visibility = View.VISIBLE
                            cosView.text = "cos(θ/2): ${p0?.values?.get(3)}"
                            estimatedHeadingAccuracyView.text = "Estimated Heading Accuracy: ${p0?.values?.get(4)}"
                        }

                        lastUpdateTime = System.currentTimeMillis()
                    }
                }
            })

        }
    }

    override fun onResume() {
        super.onResume()
        listeners?.forEach { sensorManager.registerListener(it.value, it.key, SensorManager.SENSOR_DELAY_FASTEST) }


    }

    override fun onPause() {
        super.onPause()

        listeners?.forEach { sensorManager.unregisterListener(it.value, it.key) }
    }
}