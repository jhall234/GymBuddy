package com.csci448.jhallinan.gymbuddy

import java.util.*

class DataLog {
    var id : UUID
        private set

    var date: Date = Date()
    var details : String = ""

    init {
        id = UUID.randomUUID()
    }
}