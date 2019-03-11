package com.csci448.jhallinan.gymbuddy

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_workout_details.*

class WorkoutDetailsActivity: AppCompatActivity() {
    private var workoutList: ArrayList<WorkoutViewItem> = ArrayList()



    private fun createFragment(): Fragment {

        return WorkoutDetailsFragment.newInstance(workoutList)
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