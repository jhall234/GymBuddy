package com.csci448.jhallinan.gymbuddy

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.workouts_fragment_list_item.view.*


class WorkoutsRecyclerViewAdapter(val items: List<WorkoutCardItem>, val listener: (Int) -> Unit):
    RecyclerView.Adapter<WorkoutsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemTitle: TextView = itemView.workouts_fragment_list_item_title
        val itemImage:ImageView = itemView.workouts_fragment_list_item_background_image_view

        fun bind(item: WorkoutCardItem, listener: (Int) -> Unit) = with(itemView) {
            itemTitle.text = item.title
            itemImage.setImageResource(item.imageResource)

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
                        .inflate(R.layout.workouts_fragment_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int ) {
        viewHolder.bind(items[position], listener)
    }

    companion object {
        const val LOG_TAG = "WorkoutsRecyclerViewAdapter"
    }
}
