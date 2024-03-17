package ru.mirea.plugins

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*

const val KeyCloakAuth = "keycloak-auth"

private const val KEYCLOAK_URL = "http://localhost:18080"

fun Application.configureSecurity() {
    authentication {
        oauth(KeyCloakAuth) {
            urlProvider = { "http://localhost:8080/oauth-redirect" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "keycloak",
                    authorizeUrl = "$KEYCLOAK_URL/realms/test_realm/protocol/openid-connect/auth",
                    accessTokenUrl = "$KEYCLOAK_URL/test_realm/protocol/openid-connect/token",
                    requestMethod = HttpMethod.Post,
                    clientId = "test-web-app",
                    clientSecret = "lR5FQ1z8fjFel20QDFf2PxKzxenmamiS",
                    defaultScopes = listOf("roles")
                )
            }
            client = HttpClient(Apache)
        }
    }
}
