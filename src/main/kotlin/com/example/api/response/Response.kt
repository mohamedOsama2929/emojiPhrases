package com.example.api.response

import kotlinx.serialization.Serializable

@Serializable
data class Response<T> (
    val success:Boolean?=null,
    val code:Int?=null,
    val message:String?="",
    val data:T?=null
        ):java.io.Serializable