package com.aamir.employeemanagement.networkUtils

import com.aamir.employeemanagement.networkUtils.EndPoints.Companion.ADD_EMPLOYEE
import com.aamir.employeemanagement.networkUtils.EndPoints.Companion.DELETE_EMPLOYEE
import com.aamir.employeemanagement.networkUtils.EndPoints.Companion.EMPLOYEE
import com.aamir.employeemanagement.networkUtils.responseModels.Employee
import com.aamir.employeemanagement.networkUtils.EndPoints.Companion.EMPLOYEES
import com.aamir.employeemanagement.networkUtils.EndPoints.Companion.UPDATE_EMPLOYEE
import com.aamir.employeemanagement.networkUtils.requestModels.EmployeeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EmployeeApi {
    @GET(EMPLOYEES)
    suspend fun getEmployees(): Response<ApiResponse<List<Employee>>>

    @GET("$EMPLOYEE/{id}")
    suspend fun getEmployee(@Path("id") id:String): Response<ApiResponse<Employee>>

    @POST(ADD_EMPLOYEE)
    suspend fun addEmployee(@Body data:EmployeeRequest): Response<ApiResponse<EmployeeRequest>>

    @PUT(UPDATE_EMPLOYEE)
    suspend fun updateEmployee(@Path("id")id:String,@Body data:EmployeeRequest): Response<ApiResponse<EmployeeRequest>>

    @DELETE(DELETE_EMPLOYEE)
    suspend fun deleteEmployee(@Path("id")id:String): Response<ApiResponse<String>>
}