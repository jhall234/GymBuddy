package com.csci448.jhallinan.gymbuddy.running.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "run_table")
data class Run(
    @PrimaryKey(autoGenerate = true) var runId: Int = 0,
    @ColumnInfo(name = "date") var date: Date?,
    @ColumnInfo(name = "steps") var steps: Int?,
    @ColumnInfo(name = "distance") var distance: Double?,
    @ColumnInfo(name = "time") var time: Long?
)