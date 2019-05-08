package com.csci448.jhallinan.gymbuddy.running


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder

import android.content.Context
import android.content.Intent

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.csci448.jhallinan.gymbuddy.R
import com.csci448.jhallinan.gymbuddy.plans.PlansViewModel
import com.csci448.jhallinan.gymbuddy.running.data.Run
import kotlinx.android.synthetic.main.activity_new_run.*
import java.util.*

class NewRunActivity: AppCompatActivity(), SensorEventListener {

    private var isClockOn = false
    private var isPaused = false
    private var timeWhenPaused: Long = 0

    private var initialSteps: Int = 0
    private var stepsTaken: Int = 0
    private var distanceTraveled: Double = 0.0
    private var baseTime: Long = 0

    private lateinit var sensorManager: SensorManager
    private lateinit var stepCounter: Sensor

    // notifications
    private lateinit var notificationManager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder

    private lateinit var resultIntent: Intent
    private lateinit var resultPendingIntent: PendingIntent


    private lateinit var runsViewModel: RunsViewModel


    private fun updateSteps(){
        steps_taken.text = "$stepsTaken"
        distanceTraveled = stepsTaken / 2112.0
        distance_text_view.text = "%.2f".format(distanceTraveled)
    }

    private fun updateNotification() {
        builder.setContentText("${"%.2f".format(distanceTraveled)} miles    $stepsTaken steps taken")

        resultIntent.putExtra(EXTRA_IS_CLOCK_ON, isClockOn)
        resultIntent.putExtra(EXTRA_DISTANCE, distanceTraveled)
        resultIntent.putExtra(EXTRA_STEPS_TAKEN, stepsTaken)
        resultIntent.putExtra(EXTRA_INITIAL_STEPS, initialSteps)
        resultIntent.putExtra(EXTRA_BASE_TIME, chronometer.base)

        resultPendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

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
        Log.d(LOG_TAG, "onCreate")

        setContentView(R.layout.activity_new_run)

        // Intent values
        isClockOn = intent.getBooleanExtra(EXTRA_IS_CLOCK_ON, false)
        distanceTraveled = intent.getDoubleExtra(EXTRA_DISTANCE, 0.0)
        stepsTaken = intent.getIntExtra(EXTRA_STEPS_TAKEN, 0)
        initialSteps = intent.getIntExtra(EXTRA_INITIAL_STEPS, 0)
        updateSteps()


        baseTime = intent.getLongExtra(EXTRA_BASE_TIME, SystemClock.elapsedRealtime())

        // If coming back from notification
        if (isClockOn) {
            chronometer.base  = baseTime
            chronometer.start()

            // Reveal stop & pause buttons
            start_run_button.visibility = Button.INVISIBLE
            stop_run_button.visibility = Button.VISIBLE
            pause_run_button.visibility = Button.VISIBLE

        }

        //Toolbar
        val toolbar = new_run_toolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Workouts"
        toolbar.setNavigationOnClickListener { finish() }

        // View Model

        runsViewModel = ViewModelProviders.of(this).get(RunsViewModel::class.java)

        // Notifications
        notificationManager =
                getSystemService(
                    Context.NOTIFICATION_SERVICE ) as NotificationManager

        createNotificationChannel(
            CHANNEL_ID,
            "Run in Progress",
            "$stepsTaken steps taken"
        )

        resultIntent = Intent(this, NewRunActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        resultPendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_run_notification)
            setContentTitle("Run in Progress")
            setContentText("${"%.2f".format(distanceTraveled)} miles    $stepsTaken steps taken")
            priority = (NotificationCompat.PRIORITY_DEFAULT)
            setContentIntent(resultPendingIntent)
            setOnlyAlertOnce(true)
        }

        // Step Counter Sensor
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Wire up buttons
        start_run_button.setOnClickListener {
            isClockOn = true

            if (isPaused) {
                isPaused = false
                chronometer.base = SystemClock.elapsedRealtime() + timeWhenPaused
            }
            else {
                timeWhenPaused = 0
                chronometer.base = baseTime
            }

            //start the clock
            chronometer.start()

            start_run_button.visibility = Button.INVISIBLE
            stop_run_button.visibility = Button.VISIBLE
            pause_run_button.visibility = Button.VISIBLE



            // create notification
            with (NotificationManagerCompat.from(this)) {
                    builder.setContentText("${"%.2f".format(distanceTraveled)} miles    $stepsTaken steps taken")
                    notify(NOTIFICATION_ID, builder.build())
                }
        }

        pause_run_button.setOnClickListener {
            isPaused = true
            notificationManager.cancel(NOTIFICATION_ID)
            start_run_button.visibility = Button.VISIBLE
            stop_run_button.visibility = Button.INVISIBLE
            pause_run_button.visibility = Button.INVISIBLE

            timeWhenPaused = chronometer.base - SystemClock.elapsedRealtime()
            chronometer.stop()
        }

        stop_run_button.setOnClickListener {

            val date  = Calendar.getInstance().time
            val elapsedTime = SystemClock.elapsedRealtime() - chronometer.base
            val steps = stepsTaken
            val distance = distanceTraveled

            val newRun = Run(0, date, steps, distance, elapsedTime)
            chronometer.stop()
            runsViewModel.insert(newRun)

            finish()

        }

    }

    override fun onResume() {
        super.onResume()

        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_UI)

    }

    override fun onPause() {
        updateNotification()
        sensorManager.unregisterListener(this)
        super.onPause()
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
                    updateNotification()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                notificationManager.cancel(NOTIFICATION_ID)
                chronometer.stop()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    companion object {
        private val LOG_TAG = "448.NewRunActivity"

        // Notification Variables
        private val CHANNEL_ID = "com.csci448.jhallinan.gymbuddy.running.steps"
        private val NOTIFICATION_ID = 1

        // State Variables
        private val EXTRA_IS_CLOCK_ON = "EXTRA_IS_CLOCK_ON"
        private val EXTRA_DISTANCE = "EXTRA_DISTANCE"
        private val EXTRA_STEPS_TAKEN = "EXTRA_STEPS_TAKEN"
        private val EXTRA_ELAPSED_TIME = "EXTRA_ELAPSED_TIME"
        private val EXTRA_INITIAL_STEPS = "EXTRA_INITIAL_STEPS"
        private val EXTRA_BASE_TIME = "EXTRA_BASE_TIME"

        //How do I make me?
        fun createIntent(context: Context?) : Intent {
            val intent = Intent(context, NewRunActivity::class.java)
            return intent
        }
    }
}