package com.ricky.api.user.models

data class CreateUserResponse(
    var firstName: String = "",
    var lastName: String = "",
    var userId: String = "",
    var email: String = ""
)
