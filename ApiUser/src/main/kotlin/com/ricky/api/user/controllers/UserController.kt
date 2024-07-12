package com.ricky.api.user.controllers

import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/users")
class UserController(private val env:Environment) {

    @GetMapping("/status/check")
    fun status():String{
        return "Working port number " + env.getProperty("local.server.port")
    }
}