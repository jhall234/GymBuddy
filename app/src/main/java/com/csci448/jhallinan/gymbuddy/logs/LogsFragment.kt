package com.csci448.jhallinan.gymbuddy.logs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.csci448.jhallinan.gymbuddy.R
import kotlinx.android.synthetic.main.fragment_logs.*

class LogsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         diet_button.setOnClickListener {
             val intent = LogsListActivity.createIntent(context, 0)
             startActivity(intent)
         }

        workout_button.setOnClickListener {
            val intent = LogsListActivity.createIntent(context, 1)
            startActivity(intent)
        }
    }

}