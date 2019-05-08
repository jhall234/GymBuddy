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

@Database(entities = [Category::class, Plan::class], version = 6)
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
            override fun onOpen(db: SupportSQLiteDatabase){
                super.onOpen(db)
                Log.d("PlansDatabase", "callback on created")
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        Log.d("PlansDatabase", "DB population started")
                        populateDatabase(
                            database.categoryDao(),
                            database.planDao()
                        )
                        Log.d("PlansDatabase", "DB is populated")
                    }
                }

            }
        }

        fun populateDatabase(
            categoryDao: CategoryDao,
            planDao: PlanDao
        )
        {
            planDao.deleteAll()
            categoryDao.deleteAll()


            val categories = arrayListOf<Category>(
                Category(1,"Weight Lifting", R.drawable.dumbells),
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
                    4,"Core/Abs",
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
                ),
                Plan(
                    6, "Chest and Triceps",
                    categoryDao.getCategoryId("Calisthenics"),
                    R.drawable.arms,
                    arrayListOf(
                        "12 Dips",
                        "10 Pushups ",
                        "10 Diamond Pushups",
                        "10 Wide Grip Pushups",
                        "10 Decline Pushups",
                        "10 Decline Wide Grip Pushups",
                        "10 Decline Diamond Pushups",
                        "10 Incline Pushups",
                        "10 Incline Wide Grip Pushups",
                        "10 Incline Diamond Pushups",
                        "10 Pushups"
                    )
                ),
                Plan(
                    7, "Back and Biceps",
                    categoryDao.getCategoryId("Calisthenics"),
                    R.drawable.back,
                    arrayListOf(
                        "10 Pull-Ups",
                        "5 Wide Grip Pull-Ups",
                        "5 Close Grip Pull-Ups",
                        "10 Austrailian/row Pull-Ups",
                        "20 Bent-Over Barbell Rows",
                        "20 Bar Curls",
                        "30sec Superman",
                        "30sec Bridge",
                        "30sec Low Plank",
                        "30sec High Plank",
                        "30sec Low Plank",
                        "MAX Pushups"
                    )
                ),
                Plan(
                    8, "Legs",
                    categoryDao.getCategoryId("Calisthenics"),
                    R.drawable.squats,
                    arrayListOf(
                        "10 Box jumps",
                        "20 Jumping squats",
                        "20 Bodyweight squats",
                        "10 Pistol squats (each leg)",
                        "60sec Wall sit",
                        "20 Box step ups",
                        "20 Box lunges (each leg)",
                        "MAX Calf raises",
                        "30sec Crab walk",
                        "10 Bodyweight lunges (each leg)",
                        "10 Switching lunges (each leg)",
                        "60sec Mountain climbers"
                        )
                ),
                Plan(
                    9, "Core/Abs",
                    categoryDao.getCategoryId("Calisthenics"),
                    R.drawable.abs,
                    arrayListOf<String>(
                        "45sec Knee slappers",
                        "45sec Russian twists",
                        "45sec Leg lifts",
                        "45sec Flutter kicks",
                        "45sec Mountain climbers",
                        "45sec Wall crunches",
                        "45sec Medicine ball toss",
                        "45sec Knee to elbow planks",
                        "45sec Twisting planks",
                        "45sec Side planks"
                        )
                ),
                Plan(
                    10, "Full Body",
                    categoryDao.getCategoryId("Calisthenics"),
                    R.drawable.fullbody,
                    arrayListOf<String>(
                        "3x 20 Burpees",
                        "3 min Jump Rope",
                        "100 Jumping Jacks",
                        "20 Tire Flips"
                    )
                ),

                Plan(
                    11, "Upper Body",
                    categoryDao.getCategoryId("Cardio"),
                    R.drawable.arms,
                    arrayListOf(
                        "MAX Pushups",
                        "MAX Dips",
                        "MAX Pullups",
                        "MAX Battle Rope"
                        )
                ),
                Plan(
                    12, "Lower Body",
                    categoryDao.getCategoryId("Cardio"),
                    R.drawable.back,
                    arrayListOf(
                        "Treadmill (alternate speed/slope)",
                        "MAX Suidcide sprints",
                        "MAX Wind sprints",
                        "50 Jumping squats",
                        "100 Jump rope",
                        "MAX Switching lunges"
                        )
                ),
                Plan(
                    13, "Mid/Full Body",
                    categoryDao.getCategoryId("Cardio"),
                    R.drawable.squats,
                    arrayListOf(
                        "MAX Burpees",
                        "MAX Mountain climbers",
                        "MAX Walking planks",
                        "MAX Knee to elbow twist planks"
                        )
                ),
                Plan(
                    14, "Core/Abs",
                    categoryDao.getCategoryId("Cardio"),
                    R.drawable.abs,
                    arrayListOf<String>(
                        "45sec Knee slappers",
                        "45sec Russian twists",
                        "45sec Leg lifts",
                        "45sec Flutter kicks",
                        "45sec Mountain climbers",
                        "45sec Wall crunches",
                        "45sec Medicine ball toss",
                        "45sec Knee to elbow planks",
                        "45sec Twisting planks",
                        "45sec Side planks"
                        )
                ),

                Plan(
                    15, "Upper Body",
                    categoryDao.getCategoryId("Fitness Challenges"),
                    R.drawable.arms,
                    arrayListOf(
                        "200 Pushups",
                        "100 Pull ups",
                        "200 Dips"
                        )
                ),
                Plan(
                    16, "Lower Body",
                    categoryDao.getCategoryId("Fitness Challenges"),
                    R.drawable.back,
                    arrayListOf(
                        "5 minute mile",
                        "500 Body Weight Squats",
                        "200 Stepper Floors Climbed"
                        )
                ),
                Plan(
                    17, "Mid/Full Body",
                    categoryDao.getCategoryId("Fitness Challenges"),
                    R.drawable.squats,
                    arrayListOf(
                        "500 situps",
                        "Swim a mile",
                        "100 leg lifts",
                        "5 minte plank"
                        )
                ),
                Plan(
                    18, "Rest Day",
                    categoryDao.getCategoryId("Diet"),
                    R.drawable.arms,
                    arrayListOf(
                        "Spinach",
                        "Asparagus",
                        "Greek Yogurt",
                        "Oatmeal",
                        "Black beans",
                        "Rice"
                        )
                ),
                Plan(
                    19, "Heavy Lift Day",
                    categoryDao.getCategoryId("Diet"),
                    R.drawable.back,
                    arrayListOf(
                        "Chicken",
                        "Fish",
                        "Bananas",
                        "Sweet potatoe",
                        "Beats"
                        )
                ),
                Plan(
                    20, "Cardio/Calisthenics",
                    categoryDao.getCategoryId("Diet"),
                    R.drawable.squats,
                    arrayListOf(
                        "Berries(pre workout)",
                        "Pineapple(pre workout)",
                        "Fast**"
                    )
                )
            )

            plans.forEach {
                planDao.insert(it)
            }


        }
    }






}