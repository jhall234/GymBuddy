package com.csci448.jhallinan.gymbuddy

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.add_log_fragment.*

class AddLogFragment: Fragment() {
    companion object {
        private const val ARGS_LOG_TYPE = "LOG TYPE KEY"

        fun createFragment(log_type: Int) : AddLogFragment {
            val args = Bundle()
            args.putInt(ARGS_LOG_TYPE, log_type)
            val fragment = AddLogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var log_type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log_type = arguments?.getInt(ARGS_LOG_TYPE, 0)?:0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_log_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        save_button.setOnClickListener {
            if(log_type == 0) {
                val diet_log = DataLog()
                diet_log.details = log_edit_text.text.toString()
                LogController.addDietLog(diet_log)
            } else {
                val workout_log = DataLog()
                workout_log.details = log_edit_text.text.toString()
                LogController.addWorkoutLog(workout_log)
            }

            val data = Intent()
            activity?.setResult(RESULT_OK, data)
            activity?.finish()
        }

        cancel_button.setOnClickListener {
            activity?.finish()
        }
    }
}