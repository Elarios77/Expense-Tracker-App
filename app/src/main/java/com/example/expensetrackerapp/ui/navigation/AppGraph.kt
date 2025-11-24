package com.example.expensetrackerapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.ui.screens.ExpenseListScreen
import com.example.expensetrackerapp.ui.screens.HomeScreen
import com.example.expensetrackerapp.ui.screens.TotalScreen

enum class ExpenseNavigation(@StringRes val title: Int) {
    Main(title = R.string.app_name),
    ExpenseList(title = R.string.expensesScreens),
    Total(title = R.string.total)
}

@Composable
fun AppGraph(
    navController: NavHostController = rememberNavController()
) {
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ExpenseNavigation.Main.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ExpenseNavigation.Main.name) {
                HomeScreen(
                    onCategoryClick = { route ->
                        navController.navigate(route)
                    }
                )
            }
            val detailsRoute = ExpenseNavigation.ExpenseList.name
            composable(
                route = "$detailsRoute/{category}",
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("category")
                if (categoryName != null) {
                    ExpenseListScreen(onBackClick = {
                        navController.popBackStack()
                    })
                } else {
                    Text("Error...Category not found")
                }
            }
            composable(route = ExpenseNavigation.Total.name) {
                TotalScreen(onBackClick = {
                    navController.popBackStack()
                })
            }
        }
    }
}