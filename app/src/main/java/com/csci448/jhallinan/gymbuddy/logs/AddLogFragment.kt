package com.csci448.jhallinan.gymbuddy.logs

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.csci448.jhallinan.gymbuddy.R
import kotlinx.android.synthetic.main.fragment_add_log.*

class AddLogFragment: Fragment() {
    companion object {
        private const val ARGS_LOG_TYPE = "LOG TYPE KEY"
        private const val ARGS_EDIT_TYPE = "EDIT TYPE KEY"
        private const val ARGS_LOG_POSITION = "LOG POSITION KEY"

        fun createFragment(log_type: Int, edit_type: Int, position: Int) : AddLogFragment {
            val args = Bundle()
            args.putInt(ARGS_LOG_TYPE, log_type)
            args.putInt(ARGS_EDIT_TYPE, edit_type)
            args.putInt(ARGS_LOG_POSITION, position)
            val fragment = AddLogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var log_type = 0
    var edit_type = 0
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log_type = arguments?.getInt(ARGS_LOG_TYPE, 0)?:0
        edit_type = arguments?.getInt(ARGS_EDIT_TYPE, 0)?:0
        position = arguments?.getInt(ARGS_LOG_POSITION, 0)?:0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(edit_type == 1) {
            if(log_type == 0) {
                log_edit_text?.setText(LogController.getDietLogAt(position)?.details)
            } else {
                log_edit_text?.setText(LogController.getWorkoutLogAt(position)?.details)
            }
        }

        save_button.setOnClickListener {
            if(edit_type == 1) {
                if(log_type == 0) {
                    LogController.getDietLogAt(position)?.details = log_edit_text.text.toString()
                } else {
                    LogController.getWorkoutLogAt(position)?.details = log_edit_text.text.toString()
                }
            } else {
                if (log_type == 0) {
                    val diet_log = DataLog()
                    diet_log.details = log_edit_text.text.toString()
                    LogController.addDietLog(diet_log)
                } else {
                    val workout_log = DataLog()
                    workout_log.details = log_edit_text.text.toString()
                    LogController.addWorkoutLog(workout_log)
                }
            }

            val data = Intent()
            activity?.setResult(RESULT_OK, data)
            activity?.finish()
        }

        if(edit_type == 1) {
            cancel_button.setText(R.string.button_delete)
            cancel_button.setBackgroundColor(Color.parseColor("#FF0000"))
        }

        cancel_button.setOnClickListener {
            if(edit_type == 1) {
                if(log_type == 0) {
                    LogController.deleteDietLog(LogController.getDietLogAt(position))
                } else {
                    LogController.deleteWorkoutLog(LogController.getWorkoutLogAt(position))
                }
            }
            val data = Intent()
            activity?.setResult(RESULT_OK, data)
            activity?.finish()
        }
    }
}