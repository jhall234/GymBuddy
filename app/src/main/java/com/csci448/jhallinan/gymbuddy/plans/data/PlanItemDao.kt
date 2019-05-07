package com.csci448.jhallinan.gymbuddy.plans.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlanItemDao {
    //For repository insert
    @Query("SELECT * FROM plan_items")
    fun getAllPlanItems(): List<PlanItem>

    //for plan details
    @Query("SELECT * FROM plan_items WHERE plan_id=:planId")
    fun getPlanItemsInPlan(planId: Int): List<PlanItem>

    @Insert
    fun insert(planItem: PlanItem)

    @Query("DELETE FROM plan_items")
    fun deleteAll()
}