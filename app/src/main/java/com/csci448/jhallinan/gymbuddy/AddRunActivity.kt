package com.csci448.jhallinan.gymbuddy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

class AddRunActivity: AppCompatActivity() {
    companion object {
        fun createIntent(context: Context?) : Intent {
            val intent = Intent(context, AddRunActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_container_layout)
        var fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(fragment == null) {
            fragment = createFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }

    fun createFragment() : Fragment {
        return AddRunFragment.createFragment()
    }
}