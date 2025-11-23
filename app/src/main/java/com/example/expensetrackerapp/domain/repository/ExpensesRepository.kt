package com.example.expensetrackerapp.domain.repository

import com.example.expensetrackerapp.data.model.ExpenseItem
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {

    suspend fun insertExpense(expense: ExpenseItem)
    suspend fun deleteExpense(expense: ExpenseItem)
    suspend fun getExpenseById(id: String): ExpenseItem?
    fun getAllExpenses(): Flow<List<ExpenseItem>>

    fun getExpenseByCategory(categoryName: String): Flow<List<ExpenseItem>>

}