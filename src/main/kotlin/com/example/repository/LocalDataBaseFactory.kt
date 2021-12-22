package com.example.repository

import com.example.module.EmojiPhrases
import com.example.module.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object LocalDataBaseFactory{

    fun init(){
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(EmojiPhrases)
            SchemaUtils.create(Users)


        }
    }
    private fun hikari():HikariDataSource{
        val config=HikariConfig()
        config.driverClassName ="org.postgresql.Driver"
        config.jdbcUrl=System.getenv("JDBC_DATABASE_URL")
        config.maximumPoolSize=3
        config.isAutoCommit=false
        config.password="1234"
        config.transactionIsolation="TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)

    }
    suspend fun <T> dbQuery(
        block:()->T
    ):T= withContext(Dispatchers.IO){
        transaction { block() }
    }
}