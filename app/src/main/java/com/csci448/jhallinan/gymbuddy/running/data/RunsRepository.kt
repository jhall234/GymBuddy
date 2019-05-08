package com.csci448.jhallinan.gymbuddy.running.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RunsRepository(private val runDao: RunDao) {
    val allRuns: LiveData<List<Run>> = runDao.getRuns()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(run: Run) {
        runDao.insert(run)
    }
}