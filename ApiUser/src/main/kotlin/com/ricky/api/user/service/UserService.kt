package com.ricky.api.user.service

import com.ricky.api.user.dto.UserDTO
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun createUser(userDTO: UserDTO): UserDTO
    fun getUserDetailsByEmail(email: String): UserDTO
}