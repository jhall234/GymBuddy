package com.csci448.jhallinan.gymbuddy.plans

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.csci448.jhallinan.gymbuddy.R
import kotlinx.android.synthetic.main.fragment_workouts.*

class WorkoutCategoriesFragment: Fragment() {

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
        return inflater.inflate(R.layout.fragment_workouts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        layoutManager = LinearLayoutManager(context)
        workouts_fragment_recycler_view.layoutManager = layoutManager

        adapter =
            WorkoutsRecyclerViewAdapter(FIRST_VIEW_LIST) { position ->
                val intent = Intent(context, WorkoutPlansActivity::class.java)
                intent.putParcelableArrayListExtra(
                    WorkoutPlansActivity.EXTRA_SECOND_VIEW_LIST,
                    SECOND_VIEW_LIST
                )
                startActivity(intent)
            }
        workouts_fragment_recycler_view.adapter = adapter
    }

    companion object {

        val FIRST_VIEW_LIST = arrayListOf<WorkoutViewItem>(
            WorkoutViewItem(
                "Weight Lifting",
                R.drawable.dumbells
            ),
            WorkoutViewItem(
                "Calithenics",
                R.drawable.workout_frag_calisthenics
            ),
            WorkoutViewItem(
                "Cardio",
                R.drawable.workouts_frag_cardio
            ),
            WorkoutViewItem(
                "Diet",
                R.drawable.workout_frag_diet
            ),
            WorkoutViewItem(
                "Fitness Challenges",
                R.drawable.workout_frag_challenge
            )
        )

        val SECOND_VIEW_LIST = arrayListOf<WorkoutViewItem>(
            WorkoutViewItem(
                "Weight Lifting",
                R.drawable.dumbells
            ),
            WorkoutViewItem(
                "Chest and Tricpes",
                R.drawable.workouts_weights_arms
            ),
            WorkoutViewItem(
                "Back and Bicepts",
                R.drawable.workouts_weights_back
            ),
            WorkoutViewItem(
                "Legs",
                R.drawable.workouts_weights_squats
            ),
            WorkoutViewItem(
                "Core/Abs",
                R.drawable.workouts_weights_abs
            ),
            WorkoutViewItem(
                "Full Body",
                R.drawable.workouts_weights_fullbody
            )
        )
    }
}
