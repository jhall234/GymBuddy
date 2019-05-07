package com.csci448.jhallinan.gymbuddy.plans.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<Category>

    //To create a plan, need to get id from category name
    @Query("SELECT categoryId FROM categories WHERE category =:name ")
    fun getCategoryId(name: String): Int

    @Insert
    fun insert(category: Category)

    @Query("DELETE FROM categories")
    fun deleteAll()

}