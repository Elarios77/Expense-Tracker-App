package com.example.expensetrackerapp.usecase

import com.example.expensetrackerapp.domain.repository.ExpensesRepository
import com.example.expensetrackerapp.data.model.ExpenseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetExpenseCategoryUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    operator fun invoke(categoryName: String): Flow<List<ExpenseItem>>{
        return try{
            repository.getExpenseByCategory(categoryName)
        }catch(e: Exception){
            emptyFlow()
        }
    }
}