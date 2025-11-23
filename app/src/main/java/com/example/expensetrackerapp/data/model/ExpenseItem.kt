package com.example.expensetrackerapp.data.model

import java.util.UUID

data class ExpenseItem(
    val id: String = UUID.randomUUID().toString(),
    val category: String,
    val description: String,
    val amount: Double,
    val date: Long
)