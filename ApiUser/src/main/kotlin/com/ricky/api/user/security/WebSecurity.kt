package com.ricky.api.user.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurity {
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf {
            it.disable()
        }.headers {
            it.frameOptions { frame ->
                frame.disable()
            }
        }.authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers(HttpMethod.POST, "/users")?.permitAll()
                ?.requestMatchers(AntPathRequestMatcher("/h2-console/**"))?.permitAll()
        }.sessionManagement {
            it?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        return http.build()
    }
}