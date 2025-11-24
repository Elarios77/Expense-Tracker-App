package com.example.expensetrackerapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val category: String,
    val description: String,
    val amount: Double,
    val date: Long
)