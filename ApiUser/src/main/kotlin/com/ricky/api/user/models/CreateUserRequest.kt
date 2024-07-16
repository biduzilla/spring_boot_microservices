package com.ricky.api.user.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateUserRequest(
    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "First name must not be less than two characters")
    var firstName: String = "",

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, message = "Last name must not be less than two characters")
    var lastName: String = "",

    @NotNull(message = "Password cannot be null")
    @Size(
        min = 8,
        max = 16,
        message = "Password must equal or grater than 8 characters and less than 16 characters and less"
    )
    var password: String = "",

    @NotNull(message = "Email cannot be null")
    @Email
    var email: String = ""
)
