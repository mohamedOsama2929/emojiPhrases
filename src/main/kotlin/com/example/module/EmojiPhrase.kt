package com.example.module

import kotlinx.serialization.*
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

@Serializable
data class EmojiPhrase(
    val id: Int,
    val userId: String,
    val emoji: String,
    val phrase: String
) : java.io.Serializable

object EmojiPhrases : IntIdTable() {
    val user:Column<String> =varchar("user",20).index()
    val emoji: Column<String> = varchar("emoji", 255)
    val phrase: Column<String> = varchar("phrase", 255)

}