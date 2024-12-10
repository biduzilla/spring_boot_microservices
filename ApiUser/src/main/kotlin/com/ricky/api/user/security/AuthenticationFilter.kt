package com.ricky.api.user.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.ricky.api.user.models.LoginRequest
import com.ricky.api.user.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*


class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val environment: Environment
) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        try {
            val creds = ObjectMapper().readValue(request?.inputStream, LoginRequest::class.java)

            return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    creds.email,
                    creds.password
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException("Authentication failed: Unable to parse login request", e)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        try {
            val tokenSecret = environment.getProperty("token.secret")
            val time = environment.getProperty("token.expiration_time")
            val userName = (authResult?.principal as User).username
            val userDetails = userService.getUserDetailsByEmail(userName)
            val key = Keys.hmacShaKeyFor(tokenSecret?.toByteArray(StandardCharsets.UTF_8))

            time?.let {
                val token = Jwts.builder()
                    .subject(userDetails.userId)
                    .expiration(Date.from(Instant.now().plusMillis(time.toLong())))
                .issuedAt(Date.from(Instant.now()))
                .signWith(key)
                .compact()

                response?.addHeader("token", token)
                response?.addHeader("userId", userDetails.userId)
            }

        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}