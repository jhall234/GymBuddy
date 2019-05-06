package com.csci448.jhallinan.gymbuddy.running

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.format.DateFormat
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.csci448.jhallinan.gymbuddy.R
import kotlinx.android.synthetic.main.activity_main_pager.*
import kotlinx.android.synthetic.main.activity_new_run.*
import kotlinx.android.synthetic.main.fragment_log_list.*

import kotlinx.android.synthetic.main.list_item_run.view.*
import kotlinx.android.synthetic.main.fragment_running.*

class RunningFragment : Fragment() {

   //private lateinit var adapter: RunsListAdapter

//    fun update() {
//        if (::adapter.isInitialized) {
//            adapter.notifyDataSetChanged()
//        } else {
//            adapter =
//                RunsListAdapter(this, RunController.getRunLogs())
//            log_list_recycler_view.adapter = adapter
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_ADD_RUNNING_FRAGMENT) {
            if (data == null) {
                return
            }
            //update()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_running, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        new_run_button.setOnClickListener {
            //start run activity
            val intent = NewRunActivity.createIntent(context)
            startActivity(intent)
        }

        view_runs_button.setOnClickListener {
            val intent = RunHistoryActivity.createIntent(context)
            startActivity(intent)
        }
    }



    companion object {
        private const val LOG_TAG = "448.RunningFragment"
        private const val ARGS_RUNNING_TYPE = "RUNNING_TYPE_KEY"
        private const val REQUEST_CODE_ADD_RUNNING_FRAGMENT = 1

        fun createFragment(): RunningFragment {
            return RunningFragment()
        }
    }
}