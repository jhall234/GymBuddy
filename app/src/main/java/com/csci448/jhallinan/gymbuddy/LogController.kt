package com.csci448.jhallinan.gymbuddy

import java.util.*

object LogController {
    private val diet_logs : MutableList<DataLog> = mutableListOf()
    private val workout_logs : MutableList<DataLog> = mutableListOf()

    fun getDietLog(id: UUID): DataLog? {
        for(log in diet_logs) {
            if(log.id == id) {
                return log
            }
        }
        return null
    }

    fun getWorkoutLog(id: UUID): DataLog? {
        for(log in workout_logs) {
            if(log.id == id) {
                return log
            }
        }
        return null
    }

    fun getDietLogs() = diet_logs.asReversed()
    fun getWorkoutLogs() = workout_logs.asReversed()

    fun addDietLog(diet_log: DataLog) {
        diet_logs.add(diet_log)
    }

    fun addWorkoutLog(workout_log: DataLog) {
        workout_logs.add(workout_log)
    }
}