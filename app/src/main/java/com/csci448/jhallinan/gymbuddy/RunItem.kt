package com.csci448.jhallinan.gymbuddy

import java.util.*

class RunItem {
    var id : UUID
        private set

    var date: Date = Date()
    var distance: Double = 0.0
    var time: Long = 0 //Time in milliseconds
    var details : String = ""

    init {
        id = UUID.randomUUID()
    }
}