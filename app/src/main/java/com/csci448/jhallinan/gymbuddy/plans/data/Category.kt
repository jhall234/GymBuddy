package com.csci448.jhallinan.gymbuddy.plans.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) var categoryId: Int = 0,
    @ColumnInfo(name = "category") var category: String?,
    @ColumnInfo(name = "image_id") var imageId: Int?
)