package com.example.seeweather.domain

class ResponseException (val errorCode: Int, val description: String): Exception()