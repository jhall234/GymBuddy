package com.csci448.jhallinan.gymbuddy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.workouts_fragment.*

class WorkoutPlansFragment: Fragment() {
    private var workoutViewList: ArrayList<WorkoutViewItem> = ArrayList()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<WorkoutsRecyclerViewAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutViewList = arguments?.getParcelableArrayList<WorkoutViewItem>(WorkoutPlansActivity.EXTRA_SECOND_VIEW_LIST) ?: ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.workouts_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        layoutManager = LinearLayoutManager(context)
        workouts_fragment_recycler_view.layoutManager = layoutManager

        adapter = WorkoutsRecyclerViewAdapter(workoutViewList){ position ->
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.workout_details_fragment_container, WorkoutTextFragment())
                ?.commit()
        }
        workouts_fragment_recycler_view.adapter = adapter
    }



    companion object {

        fun newInstance(workoutViewList: ArrayList<WorkoutViewItem>): WorkoutPlansFragment {
            val arguments = Bundle()
            arguments.putParcelableArrayList(WorkoutPlansActivity.EXTRA_SECOND_VIEW_LIST, workoutViewList)
            val fragment = WorkoutPlansFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}