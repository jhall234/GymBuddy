package com.csci448.jhallinan.gymbuddy.main_pager

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.csci448.jhallinan.gymbuddy.*
import com.csci448.jhallinan.gymbuddy.logs.LogsFragment
import com.csci448.jhallinan.gymbuddy.plans.PlansFragment
import com.csci448.jhallinan.gymbuddy.running.RunningFragment
import kotlinx.android.synthetic.main.activity_main_pager.*


class MainActivity : AppCompatActivity(){

    private fun setUpViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(LogsFragment(), "Logs")
        adapter.addFragment(RunningFragment(), "Running")
        adapter.addFragment(PlansFragment(), "Plans")
        viewPager.adapter = adapter
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pager)

        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpViewPager(view_pager)
        tab_layout.setupWithViewPager(view_pager)

        val notificationManager =
            getSystemService(
                Context.NOTIFICATION_SERVICE ) as NotificationManager

        notificationManager.cancelAll()
    }

}
