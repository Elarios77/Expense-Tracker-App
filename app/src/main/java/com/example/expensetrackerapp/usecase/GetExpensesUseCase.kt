package com.example.expensetrackerapp.usecase

import com.example.expensetrackerapp.domain.repository.ExpensesRepository
import com.example.expensetrackerapp.domain.model.ExpenseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
     operator fun invoke(): Flow<List<ExpenseItem>>{
        return try{
            repository.getAllExpenses()
        }catch (e: Exception){
            emptyFlow()
        }
    }
}