package com.example.wepapp

import com.example.api.requests.PhrasesApiRequest
import com.example.module.EPSession
import com.example.plugins.*
import com.example.repository.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.http.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.locations.get
import io.ktor.server.locations.post
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*


const val PHRASES="${API_VERSION}/phrases"
@Location(PHRASES)
class Phrases
fun Route.phrasesApi(db: Repository,hashFunction: (String)->String){
    authenticate ("jwt"){
        get<Phrases> {
            val user=call.sessions.get<EPSession>()?.let { db.user(it.userId) }
            val phrases=db.phrase()
            val date=System.currentTimeMillis()
          //  val code=call.securityCode(date,user,hashFunction)
            call.respond(phrases)
           /* if (user==null){
                call.redirect(SingIn())
            }else{

            }*/

            // call.respond(FreeMarkerContent("phrases.ftl", mapOf("phrases" to phrases)))
        }
        post<Phrases> {
            val user= call.apiUser
            try {
                val request=call.receive<PhrasesApiRequest>()
                val phrase=db.add(user!!.userId,request.emoji,request.phrase)
                if (phrase!=null){
                    call.respond(phrase)
                }else{
                    call.respondText("Invalid data received", status = HttpStatusCode.InternalServerError)
                }
            }catch (e:Throwable){
                call.respondText("Invalid data received", status = HttpStatusCode.BadRequest )
            }
        }
    }


}