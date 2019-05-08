package com.csci448.jhallinan.gymbuddy.running

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.csci448.jhallinan.gymbuddy.R
import com.csci448.jhallinan.gymbuddy.running.data.Run
import kotlinx.android.synthetic.main.fragment_run_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class RunsRVAdapter(val listener: (Int) -> Unit):
    RecyclerView.Adapter<RunsRVAdapter.ViewHolder>() {

    private var runs = emptyList<Run>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val date: TextView = itemView.run_item_date_text_view
        val steps: TextView = itemView.run_item_steps_text_view
        val distance: TextView = itemView.run_item_distance_text_view
        val time: TextView = itemView.run_item_time_text_view
        fun bind(item: Run, listener: (Int) -> Unit) = with(itemView) {
            date.text = String.format(resources.getString(R.string.run_item_date), item.date)
            steps.text = String.format(resources.getString(R.string.run_item_steps), item.steps)
            distance.text = String.format(resources.getString(R.string.run_item_distance), item.distance)

            val dateTemp = Date(item.time!!)
            val format = SimpleDateFormat("mm:ss")
            val timeFormatted = format.format(dateTemp)
            time.text = String.format(resources.getString(R.string.run_item_time), timeFormatted )

            setOnClickListener {
                listener(layoutPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return runs.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_run_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int ) {
        viewHolder.bind(runs[position], listener)
    }

    internal fun setRuns(runs: List<Run>) {
        this.runs = runs
        notifyDataSetChanged()
    }

    companion object {
        const val LOG_TAG = "RunsRVAdapter"
    }
}
