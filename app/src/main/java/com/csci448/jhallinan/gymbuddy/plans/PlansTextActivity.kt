package com.csci448.jhallinan.gymbuddy.plans

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.csci448.jhallinan.gymbuddy.R
import com.csci448.jhallinan.gymbuddy.plans.PlansDetailActivity.Companion.EXTRA_CATEGORY_ID
import com.csci448.jhallinan.gymbuddy.plans.PlansDetailActivity.Companion.EXTRA_CATEGORY_NAME
import com.csci448.jhallinan.gymbuddy.plans.data.Plan
import kotlinx.android.synthetic.main.activity_plans_text.*

class PlansTextActivity: AppCompatActivity() {

    private var planId: Int = 1

    private lateinit var plansViewModel: PlansViewModel

    private lateinit var planName: String
    private lateinit var selectedPlan: Plan



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(LOG_TAG, "onCreate")
        // Set Layout
        setContentView(R.layout.activity_plans_text)

        // Set Toolbar
        val toolbar = plans_text_toolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Plan Text"
        toolbar.setNavigationOnClickListener { finish() }

        Log.d(LOG_TAG, "Toolbar Set")

        // get list off intent
        planId = intent.getIntExtra(EXTRA_PLAN_ID, 1)
        planName = intent.getStringExtra(EXTRA_PLAN_NAME)

        //set new title
        supportActionBar?.title = planName


        //get view model
        plansViewModel = ViewModelProviders.of(this).get(PlansViewModel::class.java)

        //get the list of items from database
        selectedPlan = plansViewModel.getSelectedPlan(planId)

        Log.d(LOG_TAG, "got plan!")

        createBulletedList(selectedPlan.listItems)
    }

    fun createBulletedList(list: ArrayList<String>?){
        var formattedString = ""


        list?.forEach {
            formattedString += "\u2022 ${it}\n\n"
        }


        plans_text_text_view.text = formattedString
    }

    companion object {
        private const val LOG_TAG = "448.PlansTextActivity"

        const val EXTRA_PLAN_ID = "EXTRA_PLAN_ID"
        const val EXTRA_PLAN_NAME = "EXTRA_PLAN_NAME"

        fun createIntent(context: Context, planId: Int, planName: String): Intent {
            val intent = Intent(context, PlansTextActivity::class.java)
            intent.putExtra(EXTRA_PLAN_ID, planId)
            intent.putExtra(EXTRA_PLAN_NAME, planName)
            return intent
        }
    }
}