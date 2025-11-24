package com.example.expensetrackerapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.ui.navigation.ExpenseNavigation

data class CategoryMenuItem (
    @StringRes val title: Int,
    @DrawableRes val iconResId: Int,
    val navigationRoute: String
)

val categories = listOf(
    CategoryMenuItem(R.string.food,R.drawable.food,"${ExpenseNavigation.ExpenseList.name}/Food"),
    CategoryMenuItem(R.string.transportation,R.drawable.transportation,"${ExpenseNavigation.ExpenseList.name}/Transportation"),
    CategoryMenuItem(R.string.home,R.drawable.home,"${ExpenseNavigation.ExpenseList.name}/Home"),
    CategoryMenuItem(R.string.shopping,R.drawable.shopping,"${ExpenseNavigation.ExpenseList.name}/Shopping"),
    CategoryMenuItem(R.string.health,R.drawable.health,"${ExpenseNavigation.ExpenseList.name}/Health"),
    CategoryMenuItem(R.string.entertainment,R.drawable.entertainment,"${ExpenseNavigation.ExpenseList.name}/Entertainment"),
    CategoryMenuItem(R.string.total,R.drawable.total, ExpenseNavigation.Total.name)
)