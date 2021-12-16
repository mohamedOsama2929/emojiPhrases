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
import kotlin.math.sign

const val SIGN_OUT_ENDPOINT="$API_VERSION/signout"
@Location(SIGN_OUT_ENDPOINT)
class SignOut

fun Route.signOut(){
    get<SignOut> {
       call.sessions.clear<EPSession>()
    }
}