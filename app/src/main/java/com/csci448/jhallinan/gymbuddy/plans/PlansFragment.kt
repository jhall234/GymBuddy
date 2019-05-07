package com.csci448.jhallinan.gymbuddy.plans


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.csci448.jhallinan.gymbuddy.R
import kotlinx.android.synthetic.main.fragment_plans.*



class PlansFragment: Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CategoriesRVAdapter.ViewHolder>? = null

    private lateinit var plansViewModel: PlansViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("PlansFragment", "onCreate()")

        activity?.let {
            plansViewModel = ViewModelProviders.of(it).get(PlansViewModel::class.java)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_plans, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        layoutManager = LinearLayoutManager(context)
        (plans_recycler_view as RecyclerView).layoutManager = layoutManager

        adapter =
            CategoriesRVAdapter(plansViewModel.categories) { position ->
                Log.d("PlansFragment", "${plansViewModel.categories[position].categoryId} : id")

                // start new intent
                val intent  = PlansDetailActivity.createIntent(
                    context!!,
                    plansViewModel.categories[position].categoryId,
                    plansViewModel.categories[position].category ?: "Plan Item"
                )
                startActivity(intent)

            }
        (plans_recycler_view as RecyclerView).adapter = adapter

    }


    companion object {

//        val FIRST_VIEW_LIST = arrayListOf<Category>(
//            Category(
//                "Weight Lifting",
//                R.drawable.dumbells
//            ),
//            Category(
//                "Calithenics",
//                R.drawable.calisthenics
//            ),
//            Category(
//                "Cardio",
//                R.drawable.cardio
//            ),
//            Category(
//                "Diet",
//                R.drawable.diet
//            ),
//            Category(
//                "Fitness Challenges",
//                R.drawable.challenge
//            )
//        )
//
//        val SECOND_VIEW_LIST = arrayListOf<Category>(
//            Category(
//                "Weight Lifting",
//                R.drawable.dumbells
//            ),
//            Category(
//                "Chest and Tricpes",
//                R.drawable.arms
//            ),
//            Category(
//                "Back and Bicepts",
//                R.drawable.back
//            ),
//            Category(
//                "Legs",
//                R.drawable.weights_squats
//            ),
//            Category(
//                "Core/Abs",
//                R.drawable.abs
//            ),
//            Category(
//                "Full Body",
//                R.drawable.fullbody
//            )
//        )
    }
}
