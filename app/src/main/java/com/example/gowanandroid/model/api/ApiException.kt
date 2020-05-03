package com.example.gowanandroid.model.api

class ApiException(var code: Int, override var message: String) : RuntimeException()