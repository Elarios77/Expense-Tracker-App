package com.example.expensetrackerapp.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerapp.domain.model.ExpenseItem
import com.example.expensetrackerapp.usecase.AddExpenseUseCase
import com.example.expensetrackerapp.usecase.GetExpenseCategoryUseCase
import com.example.expensetrackerapp.usecase.RemoveExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val getExpenseCategoryUseCase: GetExpenseCategoryUseCase,
    private val removeExpenseUseCase: RemoveExpenseUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesListUiState>(ExpensesListUiState.Loading)
    val uiState: StateFlow<ExpensesListUiState> = _uiState.asStateFlow()

    val categoryName: String = savedStateHandle.get<String>("category") ?: "Unknown category"

    private val _amount = MutableStateFlow("")
    val amountInput: StateFlow<String> = _amount.asStateFlow()

    private val _description = MutableStateFlow("")
    val descriptionInput: StateFlow<String> = _description.asStateFlow()

    init {
        loadExpenses(categoryName)
    }

    private fun loadExpenses(categoryName: String) {

        if (categoryName == "Unknown category") {
            _uiState.value = ExpensesListUiState.Error("Category not found")
            return
        }
        viewModelScope.launch {
            getExpenseCategoryUseCase(categoryName)
                .catch { exception ->
                    _uiState.value = ExpensesListUiState.Error(exception.message ?: "Error")
                }
                .collect { expenseItems ->
                    _uiState.value = ExpensesListUiState.Success(expenseItems)
                }
        }
    }

    fun onAmountChanged(newAmount: String) {
        if (newAmount.length > 9) return
        _amount.value = newAmount
    }

    fun onDescriptionChanged(newDescription: String) {
        _description.value = newDescription
    }

    fun onSaveClick() {
        viewModelScope.launch {
            try {
                val amount = _amount.value.toDoubleOrNull()
                val description = _description.value

                val newExpense = ExpenseItem(
                    category = categoryName,
                    description = description,
                    amount = amount ?: 0.0,
                    date = System.currentTimeMillis()
                )

                addExpenseUseCase(newExpense)
                _amount.value = ""
                _description.value = ""
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onDeleteClick(expense: ExpenseItem) {
        viewModelScope.launch {
            try {
                removeExpenseUseCase(expense)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

sealed class ExpensesListUiState {
    object Loading : ExpensesListUiState()
    data class Error(val message: String) : ExpensesListUiState()
    data class Success(val expenses: List<ExpenseItem>) : ExpensesListUiState()
}
