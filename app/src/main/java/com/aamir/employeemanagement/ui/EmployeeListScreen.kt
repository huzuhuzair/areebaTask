package com.aamir.employeemanagement.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aamir.employeemanagement.networkUtils.responseModels.Employee
import com.aamir.employeemanagement.R
import com.aamir.employeemanagement.observeInComposition
import com.aamir.employeemanagement.viewModels.EmployeeViewModel
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeListScreen(viewModel: EmployeeViewModel, navController: NavHostController) {
    var allEmployees by remember { mutableStateOf<List<Employee>>(emptyList()) }
    var employees by remember { mutableStateOf<List<Employee>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        viewModel.fetchEmployees()
    }
    viewModel.employees.observeInComposition { fetchedEmployees ->
        // Update the local state with the fetched employees
        allEmployees = fetchedEmployees
        employees = allEmployees
        errorMessage = ""
    }
    viewModel.error.observeInComposition {
        errorMessage = it
        employees= emptyList()
    }
    viewModel.loading.observeInComposition {
        isLoading = it
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Employees") },
                actions = {
                    IconButton(onClick = { viewModel.fetchEmployees() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    }
                    IconButton(onClick = { navController.navigate("addEmployeeScreen") }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(66.dp))
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                       employees= allEmployees.filter { it.employee_name.lowercase().contains(searchText.lowercase()) }
                    },
                    label = { Text("Search") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                if (isLoading)
                    CircularProgressIndicator()
                errorMessage?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
                EmployeeList(employees = employees, onItemClick = {
                    navController.navigate("addEmployeeScreen/${it.id}")
                })
            }
        }
    )

}

@Composable
fun EmployeeList(
    employees: List<Employee>,
    onItemClick: (Employee) -> Unit
) {
    LazyColumn {
        items(employees.size) { i ->
            EmployeeItem(employee = employees[i], onItemClick = onItemClick)
        }
    }
}

@Composable
fun EmployeeItem(employee: Employee, onItemClick: (Employee) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(employee) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Employee Picture
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Employee Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = employee.employee_name
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${employee.employee_salary}"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Age: ${employee.employee_age}"
                )
            }
        }
    }
}