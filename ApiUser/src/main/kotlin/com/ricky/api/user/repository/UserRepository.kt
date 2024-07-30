package com.ricky.api.user.repository

import com.ricky.api.user.data.UserEntity
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UserRepository : CrudRepository<UserEntity, Long> {

    fun findByUserEmail(email: String): Optional<UserEntity>
}