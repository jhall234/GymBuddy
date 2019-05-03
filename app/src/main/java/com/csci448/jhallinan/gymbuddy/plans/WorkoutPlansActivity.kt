package com.csci448.jhallinan.gymbuddy.plans

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.csci448.jhallinan.gymbuddy.R
import kotlinx.android.synthetic.main.activity_workout_details.*

class WorkoutPlansActivity: AppCompatActivity() {
    private var workoutList: ArrayList<WorkoutViewItem> = ArrayList()

    private fun createFragment(): Fragment {
        return WorkoutPlansFragment.newInstance(workoutList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_details)

        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Workouts"
        toolbar.setNavigationOnClickListener { finish() }

        workoutList = intent.getParcelableArrayListExtra(EXTRA_SECOND_VIEW_LIST)

        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.workout_details_fragment_container, createFragment())
                .commit()
        }
    }

    companion object {
        const val EXTRA_SECOND_VIEW_LIST = "EXTRA_SECOND_VIEW_LIST"
    }
}