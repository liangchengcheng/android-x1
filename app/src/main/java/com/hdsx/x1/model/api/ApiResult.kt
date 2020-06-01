package com.hdsx.x1.model.api

/**
 * 返回结果的基本解析类
 */
data class ApiResult<T>(val errorCode: Int, val errorMsg: String, private val data: T) {
    fun apiData(): T {
        if (errorCode == 0) {
            return data
        } else{
            throw ApiException(errorCode, errorMsg)
        }
    }
}