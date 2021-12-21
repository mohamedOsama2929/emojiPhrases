package com.example.wepapp

import com.example.plugins.API_VERSION
import com.example.repository.Repository
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


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