package com.csci448.jhallinan.gymbuddy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.logs_fragment.*

class LogsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.logs_fragment, container, false)
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