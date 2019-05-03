package com.csci448.jhallinan.gymbuddy.running

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.csci448.jhallinan.gymbuddy.R

import kotlinx.android.synthetic.main.list_item_run.view.*
import kotlinx.android.synthetic.main.fragment_running.*

class RunningFragment : Fragment() {

    private lateinit var adapter: RunsListAdapter

    fun update() {
        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        } else {
            adapter =
                RunsListAdapter(this, RunController.getRunLogs())
            log_list_recycler_view.adapter = adapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_ADD_RUNNING_FRAGMENT) {
            if (data == null) {
                return
            }
            update()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_running, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log_list_recycler_view.layoutManager = LinearLayoutManager(activity)
        update()
        running_fragment_fab.setOnClickListener {
            val intent = AddRunActivity.createIntent(context)
            startActivityForResult(intent,
                REQUEST_CODE_ADD_RUNNING_FRAGMENT
            )
        }
    }


    private class RunHolder(val fragment: RunningFragment, val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(run: RunItem) {
            view.list_item_run_date_text_view.text = DateFormat.format("EEE. MMM, dd", run.date)
            view.list_item_run_distance_text_view.text = run.distance.toString()
        }
    }

    private class RunsListAdapter(val fragment: RunningFragment, val runs: List<RunItem>) :
        RecyclerView.Adapter<RunHolder>() {
        override fun getItemCount(): Int {
            return runs.size
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RunHolder {
            val layoutInflater = LayoutInflater.from(fragment.context)
            val view = layoutInflater.inflate(R.layout.list_item_run, p0, false)
            view.list_item_run_distance_text_view.movementMethod = ScrollingMovementMethod()
            return RunHolder(fragment, view)
        }

        override fun onBindViewHolder(p0: RunHolder, p1: Int) {
            p0.bind(runs[p1])
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