package com.csci448.jhallinan.gymbuddy.running

import java.util.*

object RunController {

    private val run_logs : MutableList<RunItem> = mutableListOf()

    fun getRunLog(id: UUID): RunItem? {
        for(log in run_logs) {
            if(log.id == id) {
                return log
            }
        }
        return null
    }

    fun getRunLogs() = run_logs.asReversed()


    fun addRunLog(run_log: RunItem) {
        run_logs.add(run_log)
    }
}