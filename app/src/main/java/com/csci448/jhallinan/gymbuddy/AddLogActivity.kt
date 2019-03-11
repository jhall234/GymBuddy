package com.csci448.jhallinan.gymbuddy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

class AddLogActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_LOG_TYPE = "LOG TYPE"

        fun createIntent(context: Context?, log_type: Int?) : Intent {
            val intent = Intent(context, AddLogActivity::class.java)
            intent.putExtra(EXTRA_LOG_TYPE, log_type)
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
        val log_type = intent.getIntExtra(EXTRA_LOG_TYPE, 0)
        return AddLogFragment.createFragment(log_type)
    }
}