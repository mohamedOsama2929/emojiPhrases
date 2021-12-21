package com.example.api.response

data class Response<T> (
    val success:Boolean?=null,
    val code:Int?=null,
    val message:String?="",
    val data:T?=null
        )