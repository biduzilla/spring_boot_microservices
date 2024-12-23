package com.ricky.api.gateway.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


@Component
class AuthorizationHeaderFilter(private val env: Environment) :
    AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>() {
    class Config {

    }

    override fun apply(config: Config?): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request

            if (!request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return@GatewayFilter onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED)
            }

            val authorizationHeader = request.headers[HttpHeaders.AUTHORIZATION]?.get(0)
            val jwt = authorizationHeader?.replace("Bearer", "")

            if (!isJwtValid(jwt)) {
                return@GatewayFilter onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED)
            }

            chain.filter(exchange)
        }
    }

    private fun onError(
        exchange: ServerWebExchange,
        err: String,
        httpStatus: HttpStatus
    ): Mono<Void> {
        val response = exchange.response
        response.statusCode = httpStatus
        response.headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        val errorMessage = """{"error": "$err"}"""
        val buffer: DataBuffer = response.bufferFactory().wrap(errorMessage.toByteArray())
        return response.setComplete()
    }

    private fun isJwtValid(jwt: String?): Boolean {
        if (jwt == null) {
            return false
        }

        val tokenSecret = env.getProperty("token.secret")
        val key = Keys.hmacShaKeyFor(tokenSecret?.toByteArray(StandardCharsets.UTF_8))
        val claims: Claims = Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(jwt)
            .payload

        claims.subject?.let {
            return false
        }
        return true
    }

    private fun getSignInKey(): SecretKey {
        val tokenSecret = env.getProperty("token.secret")
        val bytes: ByteArray = Base64.getDecoder()
            .decode(tokenSecret?.toByteArray(StandardCharsets.UTF_8))
        return SecretKeySpec(bytes, "HmacSHA256")
    }
}