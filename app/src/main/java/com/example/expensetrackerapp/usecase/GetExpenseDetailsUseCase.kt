package com.example.expensetrackerapp.usecase

import com.example.expensetrackerapp.domain.repository.ExpensesRepository
import com.example.expensetrackerapp.domain.model.ExpenseItem
import javax.inject.Inject

class GetExpenseDetailsUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    suspend operator fun invoke(id: String): ExpenseItem?{

        return try{
            repository.getExpenseById(id)
        }catch (e: Exception){
            null
        }
    }
}