package com.aamir.employeemanagement.di

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aamir.employeemanagement.ui.AddEmployeeScreen
import com.aamir.employeemanagement.ui.EmployeeListScreen
import com.aamir.employeemanagement.viewModels.EmployeeViewModel


fun NavGraphBuilder.addEmployeeScreen(viewModel: EmployeeViewModel, navController: NavController) {
    composable(
        "addEmployeeScreen/{employeeId}",
        arguments = listOf(navArgument("employeeId") { type = NavType.StringType })
    ) {
        val employeeId = it.arguments?.getString("employeeId")
        // Argument is passed, you can use the employeeId
        AddEmployeeScreen(viewModel, navController, employeeId)
    }
    composable(
        "addEmployeeScreen",
    ) {
        // Argument is not passed, handle accordingly (e.g., show an error message)
        AddEmployeeScreen(viewModel, navController)
    }
}

fun NavGraphBuilder.employeeListScreen(
    viewModel: EmployeeViewModel,
    navController: NavHostController,
) {
    composable(
        "employeeListScreen"
    ) {
        EmployeeListScreen(viewModel, navController)
    }
}
