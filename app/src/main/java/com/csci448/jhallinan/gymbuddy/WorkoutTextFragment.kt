package com.csci448.jhallinan.gymbuddy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class WorkoutTextFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workout_text, container, false)
    }


    companion object {
        fun newInstance(workoutViewList: ArrayList<WorkoutViewItem>): WorkoutTextFragment {
            val fragment = WorkoutTextFragment()
            return fragment
        }
    }
}