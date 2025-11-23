package com.example.expensetrackerapp.usecase

import com.example.expensetrackerapp.domain.repository.ExpensesRepository
import com.example.expensetrackerapp.data.model.ExpenseItem
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: ExpensesRepository
) {
    suspend operator fun invoke(expense: ExpenseItem){
        if(expense.amount <= 0){
            throw IllegalArgumentException("Amount must be a positive number.")
        }
        if(expense.description.isBlank()){
            throw IllegalArgumentException("Description can't be blank")
        }
        repository.insertExpense(expense)
    }
}