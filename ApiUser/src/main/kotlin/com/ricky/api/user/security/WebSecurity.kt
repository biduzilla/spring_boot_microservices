package com.ricky.api.user.security

import com.ricky.api.user.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurity(
    private val environment: Environment,
    @Lazy private val userService: UserService,
) {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    protected fun configure(http: HttpSecurity): SecurityFilterChain {
        val authenticationManagerBuilder: AuthenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder::class.java)

        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder())

        val authenticationManager: AuthenticationManager = authenticationManagerBuilder.build()

        val authenticationFilter = AuthenticationFilter(
            authenticationManager = authenticationManager,
            userService = userService,
            environment = environment
        )
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"))

        http.csrf {
            it.disable()
        }.headers {
            it.frameOptions { frame ->
                frame.disable()
            }
        }.authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                .requestMatchers( "/users/**").permitAll()
//                .requestMatchers( "/users").permitAll()
                ?.requestMatchers(AntPathRequestMatcher("/h2-console/**"))?.permitAll()
        }.sessionManagement {
            it?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.addFilter(
            authenticationFilter
        )
            .authenticationManager(authenticationManager)

        return http.build()
    }
}