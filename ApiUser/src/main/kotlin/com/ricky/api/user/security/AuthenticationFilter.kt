package com.ricky.api.user.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.ricky.api.user.models.LoginRequest
import com.ricky.api.user.service.UserService
import io.jsonwebtoken.Jwts
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
import java.security.Key
import java.time.Instant
import java.util.*
import javax.crypto.spec.SecretKeySpec

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val environment: Environment
) :
    UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        try {
            request?.inputStream?.let {
                val creds = ObjectMapper().readValue(it, LoginRequest::class.java)

                return authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        creds.email,
                        creds.password
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return super.attemptAuthentication(request, response)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val userName = (authResult?.principal as User).username
        val userDetails = userService.getUserDetailsByEmail(userName)
        val time = environment.getProperty("token.expiration_time")
        val secretToken = environment.getProperty("token.secret")
        val key: Key = SecretKeySpec(secretToken?.toByteArray(), "HmacSHA512")

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
    }
}