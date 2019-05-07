package com.csci448.jhallinan.gymbuddy.plans.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.csci448.jhallinan.gymbuddy.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Category::class, Plan::class, PlanItem::class], version = 2)
@TypeConverters(StringConverters::class)
abstract class PlansDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun planDao(): PlanDao

    companion object {

        private var INSTANCE: PlansDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): PlansDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlansDatabase::class.java,
                    "plans.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(PlansDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance

                instance
            }
        }


        private class PlansDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase){
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(
                            database.categoryDao(),
                            database.planDao()
                        )
                    }
                }

            }
        }

        fun populateDatabase(
            categoryDao: CategoryDao,
            planDao: PlanDao
        ) {
            planDao.deleteAll()
            categoryDao.deleteAll()


            val categories = arrayListOf<Category>(
                Category(1,"Weight Lifting",R.drawable.dumbells),
                Category(2, "Calisthenics", R.drawable.calisthenics),
                Category(3, "Cardio", R.drawable.cardio),
                Category(4, "Diet", R.drawable.diet),
                Category(5,"Fitness Challenges", R.drawable.challenge)
            )

            categories.forEach {
                categoryDao.insert(it)
            }

            // Plans
            val plans = arrayListOf<Plan>(
                Plan(
                    1, "Chest and Triceps",
                    categoryDao.getCategoryId("Weight Lifting"),
                    R.drawable.arms,
                    arrayListOf(
                        "4x 8-12 dumbbell chest press",
                        "3x 8-12 Incline dumbbell bench press ",
                        "3x 8-12 Dumbbell skull crushers",
                        "5x 10 Push-Ups")
                ),
                Plan(
                    2, "Back and Biceps",
                    categoryDao.getCategoryId("Weight Lifting"),
                    R.drawable.back,
                    arrayListOf(
                        "3x 10 Pull-Ups",
                        "4x 8 Bent-Over Barbell Rows",
                        "4x 8 Lat Pull-Down",
                        "4x 10-12 Preacher Curls",
                        "4x 10 Alternating Dumbbell Curls"

                    )
                ),
                Plan(
                    3, "Legs",
                    categoryDao.getCategoryId("Weight Lifting"),
                    R.drawable.squats,
                    arrayListOf(
                        "4x 4-6 Barbell Squat",
                        "4x 12 Dumbbell Lunges (each leg)",
                        "3x 12-15 Leg Press",
                        "3x 12 Lying Leg Curls",
                        "3x 20 Leg Extensions",
                        "4x 12 Standing Calf Raises"
                    )
                ),
                Plan(
                    4, "Core/Abs",
                    categoryDao.getCategoryId("Weight Lifting"),
                    R.drawable.abs,
                    arrayListOf<String>(
                        "3x 15-20 Sit-Up",
                        "3x 15-20 Lying Leg Raises",
                        "3x 15-20 Jackknife Sit-Up",
                        "3x 15-20 Leg Pull-In",
                        "3x 15-20 Toe Touches",
                        "15-20 Crunches"
                    )
                ),
                Plan(
                    5, "Full Body",
                    categoryDao.getCategoryId("Weight Lifting"),
                    R.drawable.fullbody,
                    arrayListOf<String>(
                        "3x 20 Burpees",
                        "3 min Jump Rope",
                        "100 Jumping Jacks",
                        "20 Tire Flips"
                    )
                )
            )

            plans.forEach {
                planDao.insert(it)
            }


        }
    }






}