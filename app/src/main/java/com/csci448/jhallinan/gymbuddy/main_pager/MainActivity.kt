package com.csci448.jhallinan.gymbuddy.main_pager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.csci448.jhallinan.gymbuddy.*
import com.csci448.jhallinan.gymbuddy.logs.LogsFragment
import com.csci448.jhallinan.gymbuddy.plans.ViewPagerAdapter
import com.csci448.jhallinan.gymbuddy.plans.WorkoutCategoriesFragment
import com.csci448.jhallinan.gymbuddy.running.RunningFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){




    private fun setUpViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(LogsFragment(), "Logs")
        adapter.addFragment(RunningFragment(), "Running")
        adapter.addFragment(WorkoutCategoriesFragment(), "Workouts")
        viewPager.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpViewPager(view_pager)
        tab_layout.setupWithViewPager(view_pager)
    }

}
