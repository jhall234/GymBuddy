package com.csci448.jhallinan.gymbuddy.plans

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
        val toolbar = workout_details_toolbar as Toolbar
        setSupportActionBar(toolbar)
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