package com.example.wepapp

import com.example.module.EmojiPhrases.id
import com.example.plugins.API_VERSION
import com.example.repository.Repository
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.idParam


const val REMOVE_PHRASES="$API_VERSION/remove/phrase"

@Location(REMOVE_PHRASES)
class RemovePhrase
fun Route.removePhrase(db: Repository){
    post {
        val params=call.receiveParameters()
        val id= params["id"]
       // db.remove(id.toString())
        call.respond(db.remove(id.toString()))
        // call.respond(FreeMarkerContent("phrases.ftl", mapOf("phrases" to phrases)))
    }

}