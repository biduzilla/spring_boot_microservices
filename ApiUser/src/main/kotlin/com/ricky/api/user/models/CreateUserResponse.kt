package com.ricky.api.user.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateUserResponse(
    var firstName: String = "",
    var lastName: String = "",
    var userId: String = "",
    var email: String = ""
)
