package com.csci448.jhallinan.gymbuddy.plans

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter

// Using FragmentPagerAdapter (stores in memory) because we only have 3 fragments
class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    //Storing list of fragment and name of fragment
    private val menuFragmentList: MutableList<Fragment> = mutableListOf()
    private val menuNameList: MutableList<String> = mutableListOf()

    // will add a new fragment to our list
    public fun addFragment(fragment: Fragment, title: String) {
        menuFragmentList.add(fragment)
        menuNameList.add(title)
    }

    override fun getCount(): Int {
        return menuFragmentList.size
    }

    override fun getItem(position: Int): Fragment? {
        return menuFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return menuNameList[position]
    }


}