package com.csci448.jhallinan.gymbuddy

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.workouts_fragment.*

class WorkoutsFragment: Fragment() {

    // Will turn true if on the second list, letting
    // us know we can redirect to the workout text
    var isTextViewReady = false

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<WorkoutsRecyclerViewAdapter.ViewHolder>? = null

    // TODO: This function will need to create a new array of WorkoutViewItems to return and create & set new adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.workouts_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        layoutManager = LinearLayoutManager(context)
        workouts_fragment_recycler_view.layoutManager = layoutManager

        adapter = WorkoutsRecyclerViewAdapter(FIRST_VIEW_LIST){ position ->
            val intent = Intent(context, WorkoutDetailsActivity::class.java)
            intent.putParcelableArrayListExtra(WorkoutDetailsActivity.EXTRA_SECOND_VIEW_LIST, SECOND_VIEW_LIST)
            startActivity(intent)
        }
        workouts_fragment_recycler_view.adapter = adapter


    }

    companion object {

        val FIRST_VIEW_LIST = arrayListOf<WorkoutCardItem>(WorkoutCardItem("default title", R.drawable.dumbells),
                                                            WorkoutCardItem("default title", R.drawable.dumbells),
                                                            WorkoutCardItem("default title", R.drawable.dumbells),
                                                            WorkoutCardItem("default title", R.drawable.dumbells),
                                                            WorkoutCardItem("default title", R.drawable.dumbells))

        val SECOND_VIEW_LIST = arrayListOf<WorkoutCardItem>(WorkoutCardItem("default title", R.drawable.upper_body),
            WorkoutCardItem("default title", R.drawable.upper_body),
            WorkoutCardItem("default title", R.drawable.upper_body),
            WorkoutCardItem("default title", R.drawable.upper_body))
    }
}
