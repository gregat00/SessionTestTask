package com.example.sessiontesttask.presentation.viewmodels

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sessiontesttask.data.dao.SessionDao
import com.example.sessiontesttask.data.dao.TimeStampDao
import com.example.sessiontesttask.data.entities.Session
import com.example.sessiontesttask.data.entities.TimeStamp
import com.example.sessiontesttask.presentation.models.SessionScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis

class SessionViewModel(
    private val sessionDao: SessionDao,
    private val timeStampDao: TimeStampDao,
    private val sensorManager: SensorManager,
) : ViewModel() {

    private val gyroscopeSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    private val _sessionFlow: MutableStateFlow<SessionScreenState> =
        MutableStateFlow(SessionScreenState())
    val sessionFlow: StateFlow<SessionScreenState> = _sessionFlow.asStateFlow()

    private var rotationZ: Float = 0.0f
    private var timestamp: Long = 0

    init {
        viewModelScope.launch {
            sessionDao.getSession().collect { session ->
                if (session == null) {
                    sessionDao.insertSession(Session(count = 1))
                } else {
                    _sessionFlow.update { it.copy(sessionCount = session.count) }
                }
            }
        }
    }

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
                if (timestamp != 0L) {
                    val deltaTime = (event.timestamp - timestamp) * NS2S
                    rotationZ += event.values[2] * deltaTime
                    updateTextSize(Math.toDegrees(rotationZ.toDouble()).toFloat())
                }
                timestamp = event.timestamp
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    fun updateSession() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTime = currentTimeMillis()
            val timeStamp = timeStampDao.getTimeStamp()

            if (timeStamp == null) {
                saveOutTime()
            } else if (currentTime - timeStamp.time > SESSION_DURATION) {
                sessionDao.incrementSessionCount()
            }
        }
    }

    fun saveOutTime() {
        viewModelScope.launch(Dispatchers.IO) {
            timeStampDao.insertTimeStamp(TimeStamp(time = currentTimeMillis()))
        }
    }

    fun registerSensorListener() {
        gyroscopeSensor?.let {
            sensorManager.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun unregisterSensorListener() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    private fun updateTextSize(rotationDegrees: Float) {
        val textSize = when {
            rotationDegrees < -30 -> 20.sp
            rotationDegrees > 30 -> 12.sp
            else -> 16.sp
        }
        _sessionFlow.update {
            it.copy(textSize = textSize)
        }
    }

    private companion object {
        const val SESSION_DURATION = 10 * 60 * 1000
        const val NS2S = 1.0f / 1000000000.0f
    }
}