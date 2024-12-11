package com.ricky.api.user.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateUserRequest(
    @field:NotNull(message = "First name cannot be null")
    @field:Size(min = 2, message = "First name must not be less than two characters")
//    @Size(min = 2, message = "First name must not be less than two characters")
    var firstName: String = "",

    @field:NotNull(message = "Last name cannot be null")
    @field:Size(min = 2, message = "Last name must not be less than two characters")
    var lastName: String = "",

    @field:NotNull(message = "Password cannot be null")
    @field:Size(
        min = 8,
        max = 16,
        message = "Password must equal or grater than 8 characters and less than 16 characters and less"
    )
    var password: String = "",

    @field:NotNull(message = "Email cannot be null")
    @field:Email
    var email: String = ""
)
