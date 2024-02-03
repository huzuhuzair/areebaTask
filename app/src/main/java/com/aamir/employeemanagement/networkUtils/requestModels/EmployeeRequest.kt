package com.aamir.employeemanagement.networkUtils.requestModels

data class EmployeeRequest(
    var id: Int?=null,
    val age: String,
    val name: String,
    val salary: String
)