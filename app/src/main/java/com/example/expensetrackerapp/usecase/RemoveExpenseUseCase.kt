package com.example.expensetrackerapp.usecase

import com.example.expensetrackerapp.domain.repository.ExpensesRepository
import com.example.expensetrackerapp.data.model.ExpenseItem
import javax.inject.Inject

class RemoveExpenseUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    suspend operator fun invoke(expense: ExpenseItem){
        repository.deleteExpense(expense)
    }
}