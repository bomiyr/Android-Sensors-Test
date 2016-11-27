package com.bomiyr.sensors


import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

fun startSettingsActivity(context: Context) {
    val intent = Intent(context, SettingsActivity::class.java)
    context.startActivity(intent)
}

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val accelerometerView = findViewById(R.id.accelerometer) as CustomToggle
        val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometerSensor == null) {
            accelerometerView.enabled = false
        }

        val ambientTemperatureView = findViewById(R.id.ambient_temperature) as CustomToggle
        val ambientTemperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (ambientTemperatureSensor == null) {
            ambientTemperatureView.enabled = false
        }

        val gameRotationVectorView = findViewById(R.id.game_rotation_vector) as CustomToggle
        val gyroscopeUncalibratedView = findViewById(R.id.gyroscope_uncalibrated) as CustomToggle
        val magneticFieldUncalibratedView = findViewById(R.id.magnetic_field_uncalibrated) as CustomToggle
        val significantMotionView = findViewById(R.id.significant_motion) as CustomToggle

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val gameRotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)
            if (gameRotationVectorSensor == null) {
                gameRotationVectorView.enabled = false
            }

            val gyroscopeUncalibratedSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED)
            if (gyroscopeUncalibratedSensor == null) {
                gyroscopeUncalibratedView.enabled = false
            }

            val magneticFieldUncalibratedSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED)
            if (magneticFieldUncalibratedSensor == null) {
                magneticFieldUncalibratedView.enabled = false
            }

            val significantMotionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION)
            if (significantMotionSensor == null) {
                significantMotionView.enabled = false
            }
        } else {
            gameRotationVectorView.enabled = false
            gyroscopeUncalibratedView.enabled = false
            magneticFieldUncalibratedView.enabled = false
            significantMotionView.enabled = false
        }

        val geomagneticRotationVectorView = findViewById(R.id.geomagnetic_rotation_vector) as CustomToggle
        val stepCounterView = findViewById(R.id.step_counter) as CustomToggle
        val stepDetectorView = findViewById(R.id.step_detector) as CustomToggle

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val geomagneticRotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
            if (geomagneticRotationVectorSensor == null) {
                geomagneticRotationVectorView.enabled = false
            }

            val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            if (stepCounterSensor == null) {
                stepCounterView.enabled = false
            }

            val stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
            if (stepDetectorSensor == null) {
                stepDetectorView.enabled = false
            }
        } else {
            gameRotationVectorView.enabled = false
            stepCounterView.enabled = false
            stepDetectorView.enabled = false
        }

        val gravityView = findViewById(R.id.gravity) as CustomToggle
        val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        if (gravitySensor == null) {
            gravityView.enabled = false
        }

        val gyroscopeView = findViewById(R.id.gyroscope) as CustomToggle
        val gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyroscopeSensor == null) {
            gyroscopeView.enabled = false
        }

        val heartBeatView = findViewById(R.id.heart_beat) as CustomToggle
        val motionDetectView = findViewById(R.id.motion_detect) as CustomToggle
        val pose6dofView = findViewById(R.id.pose_6dof) as CustomToggle
        val stationaryDetectView = findViewById(R.id.stationary_detect) as CustomToggle

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val heartBeatSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT)
            if (heartBeatSensor == null) {
                heartBeatView.enabled = false
            }

            val motionDetectSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT)
            if (motionDetectSensor == null) {
                motionDetectView.enabled = false
            }

            val pose6dofSensor = sensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF)
            if (pose6dofSensor == null) {
                pose6dofView.enabled = false
            }

            val stationaryDetectSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STATIONARY_DETECT)
            if (stationaryDetectSensor == null) {
                stationaryDetectView.enabled = false
            }
        } else {
            heartBeatView.enabled = false
            motionDetectView.enabled = false
            pose6dofView.enabled = false
            stationaryDetectView.enabled = false
        }

        val heartRateView = findViewById(R.id.heart_rate) as CustomToggle
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            val heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
            if (heartRateSensor == null) {
                heartRateView.enabled = false
            }
        } else {
            heartRateView.enabled = false
        }

        val lightView = findViewById(R.id.light) as CustomToggle
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (lightSensor == null) {
            lightView.enabled = false
        }

        val linearAccelerationView = findViewById(R.id.linear_acceleration) as CustomToggle
        val linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        if (linearAccelerationSensor == null) {
            linearAccelerationView.enabled = false
        }

        val magneticFieldView = findViewById(R.id.magnetic_field) as CustomToggle
        val magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (magneticFieldSensor == null) {
            magneticFieldView.enabled = false
        }


        val pressureView = findViewById(R.id.pressure) as CustomToggle
        val pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if (pressureSensor == null) {
            pressureView.enabled = false
        }

        val proximityView = findViewById(R.id.proximity) as CustomToggle
        val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if (proximitySensor == null) {
            proximityView.enabled = false
        }

        val relativeHumidityView = findViewById(R.id.relative_humidity) as CustomToggle
        val relativeHumiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        if (relativeHumiditySensor == null) {
            relativeHumidityView.enabled = false
        }

        val rotationVectorView = findViewById(R.id.rotation_vector) as CustomToggle
        val rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        if (rotationVectorSensor == null) {
            rotationVectorView.enabled = false
        }
    }
}
