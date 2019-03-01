package com.csci448.jhallinan.gymbuddy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpViewPager(view_pager)
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun setUpViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(LogsFragment(), "Logs")
        adapter.addFragment(RunningFragment(), "Running")
        adapter.addFragment(WorkoutsFragment(), "Workouts")
        viewPager.adapter = adapter
    }


}
