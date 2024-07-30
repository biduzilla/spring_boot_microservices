package com.ricky.api.user.models

data class LoginRequest(
    var email: String = "",
    var password: String = ""
)
