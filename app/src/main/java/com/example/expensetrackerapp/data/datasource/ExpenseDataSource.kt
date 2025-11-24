package com.example.expensetrackerapp.data.datasource

import com.example.expensetrackerapp.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseDataSource {

    fun getAllExpenses(): Flow<List<ExpenseEntity>>
    suspend fun insertExpense(expense: ExpenseEntity)
    suspend fun deleteExpense(expense: ExpenseEntity)
    suspend fun getExpenseById(id: String): ExpenseEntity?
    fun getExpenseByCategory(categoryName: String): Flow<List<ExpenseEntity>>

}