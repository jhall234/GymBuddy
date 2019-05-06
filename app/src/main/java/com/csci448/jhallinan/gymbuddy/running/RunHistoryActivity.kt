package com.csci448.jhallinan.gymbuddy.running

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.csci448.jhallinan.gymbuddy.R

class RunHistoryActivity: AppCompatActivity() {



//    log_list_recycler_view.layoutManager = LinearLayoutManager(activity)
//    update()
//        running_fragment_fab.setOnClickListener {
//            val intent = AddRunActivity.createIntent(context)
//            startActivityForResult(intent,
//                REQUEST_CODE_ADD_RUNNING_FRAGMENT
//            )
//        }
//}


//   private class RunHolder(val fragment: RunningFragment, val view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(run: RunItem) {
//            view.list_item_run_date_text_view.text = DateFormat.format("EEE. MMM, dd", run.date)
//            view.list_item_run_distance_text_view.text = run.distance.toString()
//        }
//    }
//
//    private class RunsListAdapter(val fragment: RunningFragment, val runs: List<RunItem>) :
//     RecyclerView.Adapter<RunHolder>() {
//        override fun getItemCount(): Int {
//            return runs.size
//        }
//
//        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RunHolder {
//            val layoutInflater = LayoutInflater.from(fragment.context)
//            val view = layoutInflater.inflate(R.layout.list_item_run, p0, false)
//            view.list_item_run_distance_text_view.movementMethod = ScrollingMovementMethod()
//            return RunHolder(fragment, view)
//        }
//
//        override fun onBindViewHolder(p0: RunHolder, p1: Int) {
//            p0.bind(runs[p1])
//        }
//    }


    companion object {
        fun createIntent(context: Context?): Intent {
            val intent = Intent(context, RunHistoryActivity::class.java)
            return intent
        }
    }
}