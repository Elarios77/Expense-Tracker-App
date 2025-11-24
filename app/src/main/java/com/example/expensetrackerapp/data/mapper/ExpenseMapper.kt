package com.example.expensetrackerapp.data.mapper

import com.example.expensetrackerapp.data.entity.ExpenseEntity
import com.example.expensetrackerapp.domain.model.ExpenseItem
import javax.inject.Inject

class ExpenseMapper @Inject constructor() {

    //List mapping
    operator fun invoke(entities: List<ExpenseEntity>): List<ExpenseItem> {
        return entities.mapNotNull { invoke(it) }
    }

    operator fun invoke(entity: ExpenseEntity?): ExpenseItem? {
        if (entity == null) return null
        return ExpenseItem(
            id = entity.id,
            category = entity.category,
            description = entity.description,
            amount = entity.amount,
            date = entity.date
        )
    }

    //Reverse Mapping
    operator fun invoke(item: ExpenseItem): ExpenseEntity {
        return ExpenseEntity(
            id = item.id,
            category = item.category,
            description = item.description,
            amount = item.amount,
            date = item.date
        )
    }
}