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

class WorkoutDetailsFragment: Fragment() {
    private var workoutViewList: ArrayList<WorkoutViewItem> = ArrayList()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<WorkoutsRecyclerViewAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutViewList = arguments?.getParcelableArrayList<WorkoutViewItem>(WorkoutDetailsActivity.EXTRA_SECOND_VIEW_LIST) ?: ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.workouts_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        layoutManager = LinearLayoutManager(context)
        workouts_fragment_recycler_view.layoutManager = layoutManager

        adapter = WorkoutsRecyclerViewAdapter(workoutViewList){ position ->
            //Need to swap out fragment
        }
        workouts_fragment_recycler_view.adapter = adapter


    }



    companion object {

        fun newInstance(workoutViewList: ArrayList<WorkoutViewItem>): WorkoutDetailsFragment {
            val arguments = Bundle()
            arguments.putParcelableArrayList(WorkoutDetailsActivity.EXTRA_SECOND_VIEW_LIST, workoutViewList)
            val fragment = WorkoutDetailsFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}