package com.csci448.jhallinan.gymbuddy.plans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.csci448.jhallinan.gymbuddy.R
import com.csci448.jhallinan.gymbuddy.plans.data.Plan
import kotlinx.android.synthetic.main.fragment_workouts_list_item.view.*

class PlansRVAdapter(val items: List<Plan>, val listener: (Int) -> Unit):
    RecyclerView.Adapter<PlansRVAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemTitle: TextView = itemView.workouts_fragment_list_item_title
        val itemImage: ImageView = itemView.workouts_fragment_list_item_background_image_view

        fun bind(item: Plan, listener: (Int) -> Unit) = with(itemView) {
            itemTitle.text = item.planName
            itemImage.setImageResource(item.imageId ?: R.drawable.dumbells)

            setOnClickListener {
                listener(layoutPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_workouts_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int ) {
        viewHolder.bind(items[position], listener)
    }

    companion object {
        const val LOG_TAG = "PlansRVAdapter"
    }
}
