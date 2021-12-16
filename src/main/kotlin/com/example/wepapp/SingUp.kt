package com.example.wepapp

import com.example.MIN_PASSWORD_LENGTH
import com.example.MIN_USER_ID_LENGTH
import com.example.module.EPSession
import com.example.module.User
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
import kotlinx.serialization.Serializable


const val SIGN_UP_ENDPOINT = "$API_VERSION/signup"

@Location(SIGN_UP_ENDPOINT)
@Serializable
data class SingUp(val userId: String = "", val displayName: String = "", val email: String = "", val error: String = ""):java.io.Serializable

fun Route.signUp(db: Repository, hashFunction: (String) -> String) {
   post<SingUp> {
       val user=call.sessions.get<EPSession>()?.let {
           db.user(it.userId)
       }
       if (user!=null)return@post call.redirect(Phrase())
       val signUpParameters=call.receive<Parameters>()
       val userId= signUpParameters["userId"] ?:return@post call.redirect(it)
       val password=signUpParameters["password"]?:return@post call.redirect(it)
       val displayName=signUpParameters["displayName"]?:return@post call.redirect(it)
       val email=signUpParameters["email"]?:return@post call.redirect(it)
       val signUpError=SingUp(userId,displayName,email)
       when{
           password.length < MIN_PASSWORD_LENGTH->{
               call.redirect(signUpError.copy(error = "Password should be at least $MIN_PASSWORD_LENGTH characters long"))
           }
           userId.length < MIN_USER_ID_LENGTH->{
               call.redirect(signUpError.copy(error = "Username should be at least $MIN_USER_ID_LENGTH characters long"))
           }
           !userNameValid(userId)->{
               call.redirect(signUpError.copy(error = "Username should consist of digits, letters, dots or underscores"))
           }
           db.user(userId)!=null->{
               call.redirect(signUpError.copy(error = "User with the following username is already registered"))
           }
           else ->{
               val hash=hashFunction(password)
               val newUser=User(userId,email,displayName,hash)
               try {
                   db.createUser(newUser)
               }catch (e:Throwable){
                   when{
                       db.user(userId)!=null->{
                           signUpError.copy(error = "User with the following username is already registered")
                       }
                       db.userByEmail(email)!=null->{
                          signUpError.copy(error = "User with the following email is already registered")
                       }
                       else->{
                           application.log.error("Failed to register user",e)
                           call.respond(signUpError.error)
                       //    call.redirect(signUpError.copy(error ="Failed to register user" ))
                       }
                   }
               }
              // call.respond(signUpError.error)
               call.sessions.set(EPSession(newUser.userId))
               call.respond(newUser.userId)
               //call.redirect(Phrases())
           }

       }
   }
    /*get<SingUp> {
        val user=call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        if (user !=null){
            call.redirect({Phrase()})
        }else{
            call.respond("osos")
        }
    }*/
}