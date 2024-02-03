package com.aamir.employeemanagement.networkUtils

import com.google.gson.annotations.SerializedName


class ApiResponse<T> {
    @SerializedName("success",alternate = ["statusCode","status"])
    val status: String? = null
    @SerializedName("message",alternate = ["msg"])
    val msg: String? = null
    val data: T? = null
}