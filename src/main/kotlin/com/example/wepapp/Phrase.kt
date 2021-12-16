package com.example.wepapp

import com.example.api.requests.PhrasesApiRequest
import com.example.module.*
import com.example.plugins.*
import com.example.repository.*
import com.example.wepapp.SingIn
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.Route
import io.ktor.sessions.*

const val PHRASE_ENDPOINT="$API_VERSION/phrase"
@Location(PHRASE_ENDPOINT)
class Phrase
fun Route.postPhrase(db:Repository){
     /*   post<Phrase> {
            val request=call.receive<PhrasesApiRequest>()
            val phrase=db.add("",request.emoji,request.phrase)
            call.respond(phrase)
           *//* val user=call.sessions.get<EPSession>()?.let { db.user(it.userId) }
            if (user==null){
                call.redirect(SingIn())
            }else{

            }*//*

        }*/

}