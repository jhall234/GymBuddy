package com.csci448.jhallinan.gymbuddy.running


import android.app.NotificationChannel
import android.app.NotificationManager

import android.content.Context
import android.content.Intent

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import com.csci448.jhallinan.gymbuddy.R
import kotlinx.android.synthetic.main.activity_new_run.*

class NewRunActivity: AppCompatActivity(), SensorEventListener {

    private var isClockOn = false

    private var initialSteps: Int = 0
    private var stepsTaken: Int = 0
    private var distanceTraveled: Double = 0.0

    private lateinit var sensorManager: SensorManager
    private lateinit var stepCounter: Sensor

    // notifications
    private lateinit var notificationManager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder

    private fun updateSteps(){
        steps_taken.text = "$stepsTaken"
        distanceTraveled = stepsTaken / 2112.0
        val distanceString = "%.2f".format(distanceTraveled)
        distance_text_view.text = distanceString

        builder.setContentText("${"%.2f".format(distanceTraveled)} miles    $stepsTaken steps taken")
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel(id: String,
                                          name: String,
                                          descriptionText: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_LOW

            val channel = NotificationChannel(id, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_run)

        //Toolbar
        val toolbar = new_run_toolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Workouts"
        toolbar.setNavigationOnClickListener { finish() }

        notificationManager =
                getSystemService(
                    Context.NOTIFICATION_SERVICE ) as NotificationManager

        createNotificationChannel(
            CHANNEL_ID,
            "Run in Progress",
            "$stepsTaken steps taken"
        )

        builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_run_notification)
            .setContentTitle("Run in Progress")
            .setContentText("${"%.2f".format(distanceTraveled)} miles    $stepsTaken steps taken")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOnlyAlertOnce(true)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Wire up buttons
        start_run_button.setOnClickListener {
            isClockOn = !isClockOn
            if (isClockOn){
                start_run_button.isChecked = true
                start_run_button.text = "Pause"
                stop_run_button.visibility = Button.INVISIBLE
                //start the clock
                chronometer.start()
                // create notification
                with (NotificationManagerCompat.from(this)) {
                    builder.setContentText("${"%.2f".format(distanceTraveled)} miles    $stepsTaken steps taken")
                    notify(NOTIFICATION_ID, builder.build())
                }
            }
            else {
                start_run_button.isChecked = false
                start_run_button.text = "Start"
                notificationManager.cancel(NOTIFICATION_ID)
                //stop the clock
                chronometer.stop()
                stop_run_button.visibility = Button.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()

        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_UI)

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (isClockOn) {
            when (event?.sensor?.type) {
                Sensor.TYPE_STEP_COUNTER -> {

                    if (initialSteps < 1){
                        initialSteps = (event.values[0]).toInt()
                    }

                    stepsTaken = (event.values[0]).toInt() - initialSteps
                    updateSteps()
                }

            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}


    companion object {
        private val CHANNEL_ID = "com.csci448.jhallinan.gymbuddy.running.steps"

        private val NOTIFICATION_ID = 1
        //How do I make me?
        fun createIntent(context: Context?) : Intent {
            val intent = Intent(context, NewRunActivity::class.java)
            return intent
        }
    }
}