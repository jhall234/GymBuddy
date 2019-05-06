package com.csci448.jhallinan.gymbuddy.main_pager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import com.csci448.jhallinan.gymbuddy.*
import com.csci448.jhallinan.gymbuddy.logs.LogsFragment
import com.csci448.jhallinan.gymbuddy.plans.ViewPagerAdapter
import com.csci448.jhallinan.gymbuddy.plans.WorkoutCategoriesFragment
import com.csci448.jhallinan.gymbuddy.running.RunningFragment
import kotlinx.android.synthetic.main.activity_main_pager.*
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.ViewCompat.setBackgroundTintList
import android.view.View
import android.widget.Toast


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
        setContentView(R.layout.activity_main_pager)

        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpViewPager(view_pager)
        tab_layout.setupWithViewPager(view_pager)
    }

}
