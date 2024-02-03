package com.aamir.employeemanagement.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamir.employeemanagement.networkUtils.responseModels.Employee
import com.aamir.employeemanagement.networkUtils.EmployeeApi
import com.aamir.employeemanagement.networkUtils.requestModels.EmployeeRequest
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmployeeViewModel@Inject constructor(private val api: EmployeeApi) : ViewModel() {
    val loading by lazy { MutableLiveData<Boolean>() }
    val employees by lazy { MutableLiveData<List<Employee>>() }
    val employee by lazy { MutableLiveData<Employee>() }
    val addEmployee by lazy { MutableLiveData<EmployeeRequest>() }
    val delete by lazy { MutableLiveData<String>() }
    val selectedEmployee by lazy { MutableLiveData<Employee>() }
    val error by lazy { MutableLiveData<String>() }

    fun fetchEmployees() {
        loading.value=true
        viewModelScope.launch {
            try {
                val response = api.getEmployees()
                loading.value=false
                if(response.isSuccessful){
                    employees.value = response.body()?.data
                }else{
                    if(response.code()==429){
                        error.value="Too Many Requests!"
                    }else{
                        error.value=response.message()
                    }
                }
            } catch (e: Exception) {
                loading.value=false
                error.value=e.message
            }
        }
    }
    fun addEmployee(data:EmployeeRequest) {
        loading.value=true
        viewModelScope.launch {
            try {
                val response = api.addEmployee(data)
                loading.value=false
                if(response.isSuccessful){
                    addEmployee.value = response.body()?.data
                }else{
                    if(response.code()==429){
                        error.value="Too Many Requests!"
                    }else{
                        error.value=response.message()
                    }
                }
            } catch (e: Exception) {
                loading.value=false
                error.value=e.message
            }
        }
    }

    fun getEmployee(employeeId: String) {
        loading.value=true
        viewModelScope.launch {
            try {
                val response = api.getEmployee(employeeId)
                loading.value=false
                if(response.isSuccessful){
                    employee.value = response.body()?.data
                }else{
                    if(response.code()==429){
                        error.value="Too Many Requests!"
                    }else{
                        error.value=response.message()
                    }
                }
            } catch (e: Exception) {
                loading.value=false
                error.value=e.message
            }
        }
    }

    fun updateEmployee(data: EmployeeRequest, employeeId: String?) {
        loading.value=true
        viewModelScope.launch {
            try {
                val response = api.updateEmployee(employeeId?:"",data)
                loading.value=false
                if(response.isSuccessful){
                    addEmployee.value = response.body()?.data
                }else{
                    if(response.code()==429){
                        error.value="Too Many Requests!"
                    }else{
                        error.value=response.message()
                    }
                }
            } catch (e: Exception) {
                loading.value=false
                error.value=e.message
            }
        }
    }

    fun deleteEmployee(employeeId: String) {
        loading.value=true
        viewModelScope.launch {
            try {
                val response = api.deleteEmployee(employeeId)
                loading.value=false
                if(response.isSuccessful){
                    delete.value = response.body()?.data
                }else{
                    if(response.code()==429){
                        error.value="Too Many Requests!"
                    }else{
                        error.value=response.message()
                    }
                }
            } catch (e: Exception) {
                loading.value=false
                error.value=e.message
            }
        }
    }
}
