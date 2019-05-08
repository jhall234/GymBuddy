package com.csci448.jhallinan.gymbuddy.running

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.csci448.jhallinan.gymbuddy.R
import com.csci448.jhallinan.gymbuddy.running.data.Run
import kotlinx.android.synthetic.main.activity_run_history.*

class RunHistoryActivity: AppCompatActivity() {

    private lateinit var runViewModel: RunsViewModel
    private var adapter: RecyclerView.Adapter<RunsRVAdapter.ViewHolder>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private fun initializeLayoutManager() {
        layoutManager = LinearLayoutManager(this)
        (run_history_recycler_view as RecyclerView).layoutManager = layoutManager
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Layout
        setContentView(R.layout.activity_run_history)

        //Toolbar
        val toolbar = run_history_toolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Run History"
        toolbar.setNavigationOnClickListener { finish() }

        runViewModel = ViewModelProviders.of(this).get(RunsViewModel::class.java)

        val adapter = RunsRVAdapter({ position ->      })


        (run_history_recycler_view as RecyclerView).adapter = adapter

        initializeLayoutManager()



        runViewModel.allRuns.observe(this, Observer { runs ->
            runs?.let { adapter.setRuns(it) }
        })
    }

    companion object {
        fun createIntent(context: Context?): Intent {
            val intent = Intent(context, RunHistoryActivity::class.java)
            return intent
        }
    }
}