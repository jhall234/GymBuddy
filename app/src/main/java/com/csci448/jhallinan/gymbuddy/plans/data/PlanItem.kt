package com.csci448.jhallinan.gymbuddy.plans.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.csci448.jhallinan.gymbuddy.plans.data.Plan

@Entity( tableName = "plan_items",
    foreignKeys = arrayOf(
    ForeignKey(
        entity = Plan::class,
        parentColumns = arrayOf("planId"),
        childColumns = arrayOf("plan_id"),
        onDelete = ForeignKey.CASCADE))
)
data class PlanItem(
    @PrimaryKey(autoGenerate = true) var itemId: Int = 0,
    @ColumnInfo(name = "plan_item") var planItem: String?,
    @ColumnInfo(name = "plan_id") var planId: Int?,
    @ColumnInfo(name = "video_link") var videoLink: String?
)