package com.example.expensetrackerapp.framework.datasource

import com.example.expensetrackerapp.data.datasource.ExpenseDataSource
import com.example.expensetrackerapp.framework.database.dao.ExpenseDao
import com.example.expensetrackerapp.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseDataSourceImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseDataSource {
    override fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return expenseDao.getAllExpenses()
    }

    override suspend fun getExpenseById(id: String): ExpenseEntity? {
        return expenseDao.getExpenseById(id)
    }

    override fun getExpenseByCategory(categoryName: String): Flow<List<ExpenseEntity>> {
        return expenseDao.getExpenseByCategory(categoryName)
    }

    override suspend fun deleteExpense(expense: ExpenseEntity) {
        expenseDao.deleteExpense(expense)
    }

    override suspend fun insertExpense(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }
}