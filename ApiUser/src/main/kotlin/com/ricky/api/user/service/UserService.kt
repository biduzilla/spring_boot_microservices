package com.ricky.api.user.service

import com.ricky.api.user.dto.UserDTO

interface UserService {
    fun createUser(user:UserDTO ): UserDTO
}