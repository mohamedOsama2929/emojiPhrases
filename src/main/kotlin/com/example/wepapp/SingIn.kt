package com.example.wepapp

import com.example.MIN_PASSWORD_LENGTH
import com.example.MIN_USER_ID_LENGTH
import com.example.module.EPSession
import com.example.module.User
import com.example.module.Users.displayName
import com.example.module.Users.email
import com.example.plugins.API_VERSION
import com.example.plugins.redirect
import com.example.repository.Repository
import com.example.userNameValid
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.locations.post


const val SIGN_IN_ENDPOINT="$API_VERSION/signin"

@Location(SIGN_IN_ENDPOINT)
data class SingIn(val userId:String="",val error:String="")

fun Route.signIn(db:Repository,hashFunction: (String)->String){
   post<SingIn> {
       val singInParameters=call.receive<Parameters>()
       val userId= singInParameters["userId"] ?:return@post call.redirect(it)
       val password=singInParameters["password"]?:return@post call.redirect(it)
       val signInError=SingIn(userId)
      val singin= when{
           password.length < MIN_PASSWORD_LENGTH -> null
           userId.length < MIN_USER_ID_LENGTH ->null
           else ->{
             db.user(userId,hashFunction(password))
           }
       }
       if (singin==null){
           call.redirect(signInError.copy(error = "Invalid username or password"))
       }else{
           call.sessions.set(EPSession(singin.userId))
           call.redirect(Phrases())
       }


   }

    get<SingIn> {
        val user=call.sessions.get<EPSession>()?.let {
            db.user(it.userId)
        }
        if (user!=null){
            call.redirect(it)
        }else{
            call.respond("osos")
        }

    }
}