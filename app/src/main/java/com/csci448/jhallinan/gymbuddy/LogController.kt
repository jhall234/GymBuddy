package com.csci448.jhallinan.gymbuddy

import android.provider.ContactsContract
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

    fun getDietLogAt(position: Int): DataLog? {
        return diet_logs.get(position)
    }

    fun getWorkoutLogAt(position: Int): DataLog? {
        return workout_logs.get(position)
    }

    fun deleteDietLog(log: DataLog?) {
        diet_logs.remove(log)
    }

    fun deleteWorkoutLog(log: DataLog?) {
        workout_logs.remove(log)
    }

    fun getPosition(log: DataLog?): Int {
        if(diet_logs.contains(log)) {
            return diet_logs.indexOf(log)
        } else if(workout_logs.contains(log)) {
            return workout_logs.indexOf(log)
        } else {
            return -1
        }
    }
}