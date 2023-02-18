package com.lampotrias.seeweather.domain

class ResponseException (val errorCode: Int, val description: String): Exception()