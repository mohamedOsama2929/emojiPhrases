package com.example.wepapp

import com.example.plugins.*
import com.example.repository.*
import io.ktor.server.locations.*

const val PHRASE_ENDPOINT="$API_VERSION/phrase"
@Location(PHRASE_ENDPOINT)
class Phrase
fun postPhrase(db: Repository){
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