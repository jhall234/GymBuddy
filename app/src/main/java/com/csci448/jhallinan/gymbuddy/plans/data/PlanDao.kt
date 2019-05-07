package com.csci448.jhallinan.gymbuddy.plans.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.csci448.jhallinan.gymbuddy.plans.data.Plan

@Dao
interface PlanDao {
    //For repository insert
    @Query("SELECT * FROM plans")
    fun getAllPlans(): List<Plan>

    //PlanItem need to know plan id to be created
    @Query("SELECT planId FROM plans WHERE plan_name =:name ")
    fun getPlanId(name: String): Int

    // for recycler view
    @Query("SELECT * FROM plans WHERE category_id = :id")
    fun getPlansInCategory(id: Int): List<Plan>

    @Query("SELECT * FROM plans WHERE planId = :id")
    fun getPlan(id: Int): Plan

    @Insert
     fun insert(plan: Plan)

    @Query("DELETE FROM plans")
    fun deleteAll()
}