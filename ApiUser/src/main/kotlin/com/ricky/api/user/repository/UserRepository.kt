package com.ricky.api.user.repository

import com.ricky.api.user.data.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Long> {
}