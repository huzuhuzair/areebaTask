package com.aamir.employeemanagement.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aamir.employeemanagement.networkUtils.EmployeeApi
import com.aamir.employeemanagement.viewModels.EmployeeViewModel
import javax.inject.Inject

class EmployeeViewModelFactory @Inject constructor(private val api: EmployeeApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
