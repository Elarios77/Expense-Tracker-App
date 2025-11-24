package com.example.expensetrackerapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerapp.usecase.GetExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TotalScreenViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TotalScreenUiState>(TotalScreenUiState.Loading)
    val uiState: StateFlow<TotalScreenUiState> = _uiState.asStateFlow()

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            getExpensesUseCase()
                .catch { exception ->
                    _uiState.value = TotalScreenUiState.Error(exception.message ?: "Error")
                }
                .collect { expenses ->
                    val total = expenses.sumOf { it.amount }

                    val categoryMap = expenses
                        .groupBy { it.category }
                        .mapValues { entry ->
                            entry.value.sumOf { it.amount }
                        }

                    _uiState.value = TotalScreenUiState.Success(
                        totalAmount = total,
                        categoryTotals = categoryMap
                    )
                }
        }
    }
}

sealed class TotalScreenUiState {
    object Loading : TotalScreenUiState()
    data class Error(val message: String) : TotalScreenUiState()
    data class Success(
        val totalAmount: Double,
        val categoryTotals: Map<String, Double>
    ) : TotalScreenUiState()
}