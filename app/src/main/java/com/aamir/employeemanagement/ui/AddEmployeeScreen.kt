package com.aamir.employeemanagement.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aamir.employeemanagement.networkUtils.requestModels.EmployeeRequest
import com.aamir.employeemanagement.observeInComposition
import com.aamir.employeemanagement.viewModels.EmployeeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEmployeeScreen(
    viewModel: EmployeeViewModel,
    navController: NavController,
    employeeId: String? = null
) {
    var name by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var btnText by remember { mutableStateOf("Add") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    viewModel.addEmployee.observeInComposition {
        if (it != null) {
           if(btnText=="Update"){
               name = it.name
               salary = it.salary
               age = it.age
               successMessage = "Employee Update Successfully!"
               errorMessage = ""
           }else{
               name = ""
               salary = ""
               age = ""
               successMessage = "Employee Added Successfully!"
               errorMessage = ""
           }
        }
    }
    viewModel.employee.observeInComposition {
        if (it != null) {
            name = it.employee_name
            salary = it.employee_salary
            age = it.employee_age
            successMessage = ""
            errorMessage = ""
        }
    }
    if (employeeId != null) {
        btnText = "Update"
          viewModel.getEmployee(employeeId)
    } else {
        btnText = "Add"
    }
    viewModel.error.observeInComposition {
        errorMessage = it
    }
    viewModel.delete.observeInComposition {
        if(employeeId==it){
            errorMessage = "Deleted SuccessFully"
            name = ""
            salary = ""
            age = ""
            successMessage = ""
            navController.popBackStack()
        }
    }
    viewModel.loading.observeInComposition {
        isLoading = it
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                },
                title = { Text(text = "$btnText Employee") },
                actions = {
                    if(employeeId!=null){
                        IconButton(onClick = { viewModel.deleteEmployee(employeeId) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }
                    Button(onClick = {
                        if (name.isNotEmpty() && salary.isNotEmpty() && age.isNotEmpty()) {
                            if (age.toInt() > 18) {
                                val data = EmployeeRequest(age = age, name = name, salary = salary)
                                if(btnText=="Update"){
                                    viewModel.updateEmployee(data,employeeId)
                                }else{
                                    viewModel.addEmployee(data)
                                }
                            } else {
                                errorMessage = "Age Should be 18+!"
                            }
                        } else {
                            errorMessage = "Please Fill All The Fields!"
                        }
                    }) {
                        Text(text = btnText)
                    }

                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(66.dp))

                errorMessage?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
                successMessage?.let {
                    Text(text = it, color = Color.Green)
                }

                if (isLoading)
                    CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                // Name TextField
                OutlinedTextField(
                    value = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    onValueChange = {
                        name = it
                    },
                    label = { Text("Name") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Salary TextField
                OutlinedTextField(
                    value = salary,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { salary = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    label = { Text("Enter Salary") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Age TextField
                OutlinedTextField(
                    value = age,
                    onValueChange = {
                        age = it
                    },
                    label = { Text("Enter Age") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    )

}


