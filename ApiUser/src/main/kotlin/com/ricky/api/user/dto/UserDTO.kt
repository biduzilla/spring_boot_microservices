package com.ricky.api.user.dto

import java.io.Serializable
import java.util.*

data class UserDTO(
    var userId: String = UUID.randomUUID().toString(),
    var encryptedPassword: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var password: String = "",
    var email: String = ""
) : Serializable
