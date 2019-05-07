package com.csci448.jhallinan.gymbuddy.plans

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csci448.jhallinan.gymbuddy.R
import com.csci448.jhallinan.gymbuddy.plans.data.Plan
import com.csci448.jhallinan.gymbuddy.plans.data.PlanItem
import kotlinx.android.synthetic.main.activity_plans_detail.*

class PlansDetailActivity: AppCompatActivity() {
    private var plansList: List<Plan> = emptyList()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<PlansRVAdapter.ViewHolder>? = null

    private var categoryId: Int = 1
    private var categoryName: String = "Plan Type"

    private lateinit var plansViewModel: PlansViewModel

    private fun initializeLayoutManager() {
        layoutManager = LinearLayoutManager(this)
        (plans_detail_recycler_view as RecyclerView).layoutManager = layoutManager
    }

    private fun initializeAdapter() {
        adapter = PlansRVAdapter(plansList) { position ->

            val intent = PlansTextActivity.createIntent(
                this,
                plansList[position].planId,
                plansList[position]?.planName ?: "Selected Plan"
            )
            startActivity(intent)
            Log.d("448.PlansDetailActivity", "intent sent")
        }

        (plans_detail_recycler_view as RecyclerView).adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set Layout
        setContentView(R.layout.activity_plans_detail)

        // Set Toolbar
        val toolbar = plans_detail_toolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = categoryName
        toolbar.setNavigationOnClickListener { finish() }

        // get list off intent
        categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, 1)
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME)

        //set new title
        supportActionBar?.title = categoryName

        //get view model
        plansViewModel = ViewModelProviders.of(this).get(PlansViewModel::class.java)

        //get the list of items from database
        plansList = plansViewModel.getSelectedPlans(categoryId)

        initializeLayoutManager()
        initializeAdapter()
    }

    companion object {
        const val EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID"
        const val EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME"

        fun createIntent(context: Context, categoryId: Int, categoryName: String): Intent {
            val intent = Intent(context, PlansDetailActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY_ID, categoryId)
            intent.putExtra(EXTRA_CATEGORY_NAME, categoryName)
            return intent
        }

    }
}