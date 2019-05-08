package com.csci448.jhallinan.gymbuddy.plans.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "plans",
        foreignKeys = arrayOf(ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("categoryId"),
            childColumns = arrayOf("category_id"),
            onDelete = CASCADE))
)
class Plan (
    @PrimaryKey var planId: Int,
    @ColumnInfo(name = "plan_name") var planName: String?,
    @ColumnInfo(name = "category_id") var categoryId: Int?,
    @ColumnInfo(name = "image_id") var imageId: Int?,

    @ColumnInfo(name = "list_items")
    var listItems: ArrayList<String>?
)