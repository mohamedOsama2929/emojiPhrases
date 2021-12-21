package com.example.module

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class User(val userId: String, val displayName: String, val email: String, val passwordHash: String) :
    java.io.Serializable, Principal

object Users : Table() {
    val id = varchar("id", 20)
    val email = varchar("email", 128).uniqueIndex()
    val displayName = varchar("display_name", 256)
    val passwordHash = varchar("password_hash", 64)
}