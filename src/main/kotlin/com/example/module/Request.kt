package com.example.module
import kotlinx.serialization.*

@Serializable
data class Request (val emoji:String,val phrase:String)