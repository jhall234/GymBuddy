package com.csci448.jhallinan.gymbuddy.running.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Run::class], version = 4)
@TypeConverters(DateConverter::class)
abstract class RunsDatabase : RoomDatabase() {
    abstract fun runDao(): RunDao

    companion object {

        private var INSTANCE: RunsDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): RunsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RunsDatabase::class.java,
                    "runs.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(RunsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class RunsDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase){
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(
                            database.runDao()
                        )
                    }
                }

            }
        }

        fun populateDatabase(runDao: RunDao){
            runDao.deleteAll()
        }
    }
}
