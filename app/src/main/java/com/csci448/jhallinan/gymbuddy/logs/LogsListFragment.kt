package com.csci448.jhallinan.gymbuddy.logs

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
import kotlinx.android.synthetic.main.list_item_log.view.*
import kotlinx.android.synthetic.main.fragment_log_list.*

class LogsListFragment: Fragment() {
    companion object {
        private const val LOG_TAG = "448.LogListFrag"
        private const val ARGS_LOG_TYPE = "LOG TYPE KEY"
        private const val REQUEST_CODE_ADD_LOG_FRAGMENT = 0
        private const val REQUEST_CODE_EDIT_LOG_FRAGMENT = 1

        fun createFragment(log_type: Int) : LogsListFragment {
            val args = Bundle()
            args.putInt(ARGS_LOG_TYPE, log_type)
            val fragment = LogsListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var adapter: LogsListAdapter

    var log_type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log_type = arguments?.getInt(ARGS_LOG_TYPE, 0)?:0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_log_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        log_list_recycler_view.layoutManager = LinearLayoutManager(activity)
        update()

        if(log_type == 0) {
            logs_history_label_text_view.setText(R.string.label_diet_history)
        } else {
            logs_history_label_text_view.setText(R.string.label_workout_history)
        }

        add_log_floating_action_button.setOnClickListener {
            val intent = AddLogActivity.createIntent(context, log_type, 0, 0)
            startActivityForResult(intent,
                REQUEST_CODE_ADD_LOG_FRAGMENT
            )
        }
    }

    private class LogHolder(val fragment: LogsListFragment, val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(log: DataLog) {
            view.list_item_log_date_text_view.text = DateFormat.format("EEE. MMM, dd", log.date)
            view.list_item_log_details_text_view.text = log.details
            val position = LogController.getPosition(log)
            view.list_item_log_details_text_view.setOnClickListener {
                if(position != -1) {
                    val intent = AddLogActivity.createIntent(
                        fragment.context,
                        fragment.log_type,
                        1,
                        position
                    )
                    fragment.startActivityForResult(intent,
                        REQUEST_CODE_EDIT_LOG_FRAGMENT
                    )
                }
            }
            view.setOnClickListener {
                if(position != -1) {
                    val intent = AddLogActivity.createIntent(
                        fragment.context,
                        fragment.log_type,
                        1,
                        position
                    )
                    fragment.startActivityForResult(intent,
                        REQUEST_CODE_EDIT_LOG_FRAGMENT
                    )
                }
            }
        }
    }

    private class LogsListAdapter(val fragment: LogsListFragment, val logs: List<DataLog>) : RecyclerView.Adapter<LogHolder>() {
        override fun getItemCount(): Int {
            return logs.size
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LogHolder {
            val layoutInflater = LayoutInflater.from(fragment.context)
            val view = layoutInflater.inflate(R.layout.list_item_log, p0, false)
            view.list_item_log_details_text_view.movementMethod = ScrollingMovementMethod()
            return LogHolder(fragment, view)
        }

        override fun onBindViewHolder(p0: LogHolder, p1: Int) {
            p0.bind(logs[p1])
        }
    }

    fun update() {
        if(::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        } else {
            if(log_type == 0) {
                adapter = LogsListAdapter(
                    this,
                    LogController.getDietLogs()
                )
            } else {
                adapter = LogsListAdapter(
                    this,
                    LogController.getWorkoutLogs()
                )
            }
            log_list_recycler_view.adapter = adapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != RESULT_OK) {
            return
        }
        if(requestCode == REQUEST_CODE_ADD_LOG_FRAGMENT || requestCode == REQUEST_CODE_EDIT_LOG_FRAGMENT) {
            if (data == null) {
                return
            }
            update()
        }
    }
}