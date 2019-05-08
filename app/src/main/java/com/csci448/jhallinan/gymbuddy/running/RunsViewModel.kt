package com.csci448.jhallinan.gymbuddy.running

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.csci448.jhallinan.gymbuddy.running.data.Run
import com.csci448.jhallinan.gymbuddy.running.data.RunsDatabase
import com.csci448.jhallinan.gymbuddy.running.data.RunsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RunsViewModel(application: Application): AndroidViewModel(application) {

    private var parentJob = Job()
    // By default all the coroutines launched in this scope should be using the Main dispatcher
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: RunsRepository

    val allRuns: LiveData<List<Run>>

    init {
        val runDao = RunsDatabase.getDatabase(application, scope).runDao()
        repository = RunsRepository(runDao)
        allRuns = repository.allRuns
    }

    fun insert(run: Run) = scope.launch(Dispatchers.IO){
        repository.insert(run)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}