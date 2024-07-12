package com.ricky.api.user.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/users")
class UserController {

    @GetMapping("/status/check")
    fun status():String{
        return "Ok"
    }
}