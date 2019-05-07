package com.csci448.jhallinan.gymbuddy.plans

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.csci448.jhallinan.gymbuddy.plans.data.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PlansViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    // By default all the coroutines launched in this scope should be using the Main dispatcher
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: PlansRepository

    var categories: List<Category> = emptyList()

    var planListQuery: List<Plan> = emptyList()


    private lateinit var planQuery: Plan

    init {
        val categoryDao = PlansDatabase.getDatabase(application, scope).categoryDao()
        val planDao = PlansDatabase.getDatabase(application, scope).planDao()

        repository = PlansRepository(categoryDao, planDao)

        runBlocking(Dispatchers.IO) {
            categories = repository.getAllCategories()
        }
    }

    fun getSelectedPlans(categoryId: Int): List<Plan> {

        runBlocking(Dispatchers.IO) {
            val plansAsync = withContext(Dispatchers.IO) {
                repository.getSelectedPlans(categoryId)
            }
            planListQuery = plansAsync
        }
        return planListQuery
    }

    fun getSelectedPlan(planId: Int): Plan {

        runBlocking(Dispatchers.IO) {
            val plansAsync = withContext(Dispatchers.IO) {
                repository.getPlan(planId)

            }
            planQuery = plansAsync
        }
        return planQuery
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}