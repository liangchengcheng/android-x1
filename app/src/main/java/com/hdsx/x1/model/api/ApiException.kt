package com.hdsx.x1.model.api

class ApiException(var code: Int, override var message: String) : RuntimeException()