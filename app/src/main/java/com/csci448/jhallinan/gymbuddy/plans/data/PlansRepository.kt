package com.csci448.jhallinan.gymbuddy.plans.data

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PlansRepository(
    private val categoryDao: CategoryDao,
    private val planDao: PlanDao
) {


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllCategories() = withContext(Dispatchers.IO){
        Log.d("PlansRepository", "getAllCategoriesCalled")
        categoryDao.getAllCategories()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getSelectedPlans(categoryId: Int): List<Plan>{
        return planDao.getPlansInCategory(categoryId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPlan(planId: Int): Plan {
        return planDao.getPlan(planId)
    }


}