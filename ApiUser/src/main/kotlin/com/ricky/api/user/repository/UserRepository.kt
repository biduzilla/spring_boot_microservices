package com.ricky.api.user.repository

import com.ricky.api.user.data.UserEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<UserEntity, Long> {

    fun findByEmail(email: String): Optional<UserEntity>
}