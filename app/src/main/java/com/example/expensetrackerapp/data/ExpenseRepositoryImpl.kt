package com.example.expensetrackerapp.data

import com.example.expensetrackerapp.data.mapper.ExpenseMapper
import com.example.expensetrackerapp.domain.repository.ExpensesRepository
import com.example.expensetrackerapp.framework.datasource.ExpenseDataSourceImpl
import com.example.expensetrackerapp.domain.model.ExpenseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDataSource: ExpenseDataSourceImpl,
    private val mapper: ExpenseMapper
) : ExpensesRepository {

    override suspend fun insertExpense(expense: ExpenseItem) {
        val entity = mapper(expense)
        expenseDataSource.insertExpense(entity)
    }

    override suspend fun deleteExpense(expense: ExpenseItem) {
        val entity = mapper(expense)
        expenseDataSource.deleteExpense(entity)
    }

    override suspend fun getExpenseById(id: String): ExpenseItem? {
        val entity = expenseDataSource.getExpenseById(id)
        return mapper(entity)
    }

    override fun getAllExpenses(): Flow<List<ExpenseItem>> {
        return expenseDataSource.getAllExpenses().map { entities ->
            entities.mapNotNull { entity ->
                mapper(entity)
            }
        }
    }

    override fun getExpenseByCategory(categoryName: String): Flow<List<ExpenseItem>> {
        return expenseDataSource.getExpenseByCategory(categoryName).map { entities ->
            entities.mapNotNull { entity ->
                mapper(entity)
            }
        }
    }

}