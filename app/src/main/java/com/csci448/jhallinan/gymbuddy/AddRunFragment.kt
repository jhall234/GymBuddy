package com.csci448.jhallinan.gymbuddy

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.add_log_fragment.*
import kotlinx.android.synthetic.main.add_run_fragment.*

class AddRunFragment : Fragment() {

    private var run_item: RunItem = RunItem()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_run_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_run_button.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                start_run_button.text = "Stop"
                run_item = RunItem()
                //TODO: start timer/tracker
                Toast.makeText(context, "Tracker Started!", Toast.LENGTH_SHORT).show()
            }
            else {
                RunController.addRunLog(run_item)
                val data = Intent()
                activity?.setResult(RESULT_OK, data)
                activity?.finish()
            }

        }

    }

    companion object {
        fun createFragment(): AddRunFragment {
            val fragment = AddRunFragment()
            return fragment
        }
    }
}