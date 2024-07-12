package com.ricky.api.account.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController {

    @GetMapping("/status/check")
    fun status():String{
        return "Ok"
    }
}