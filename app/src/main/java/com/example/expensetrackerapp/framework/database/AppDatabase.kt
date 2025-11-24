package com.example.expensetrackerapp.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensetrackerapp.framework.database.dao.ExpenseDao
import com.example.expensetrackerapp.data.entity.ExpenseEntity


@Database(
    entities = [ExpenseEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
}