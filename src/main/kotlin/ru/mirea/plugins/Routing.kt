package ru.mirea.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.h1

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondHtml {
                body {
                    h1 {
                        + "A test Web client for KeyCloak"
                    }
                    a(href = "oauth") {
                        + "Log in via KeyCloak"
                    }
                }
            }
        }

        authenticate(KeyCloakAuth) {
            get("/oauth") {
                val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()

                call.respondText("Access Token = ${principal?.accessToken}")
            }
        }

        get("/oauth-redirect") {
            call.respondHtml {
                body {
                    h1 {
                        + "Authentication successful"
                    }
                }
            }
        }
    }
}
