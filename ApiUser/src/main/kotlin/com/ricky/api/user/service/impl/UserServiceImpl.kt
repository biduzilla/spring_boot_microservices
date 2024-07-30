package com.ricky.api.user.service.impl

import com.ricky.api.user.data.UserEntity
import com.ricky.api.user.dto.UserDTO
import com.ricky.api.user.repository.UserRepository
import com.ricky.api.user.service.UserService
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.beans.BeanUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {
    override fun createUser(userDTO: UserDTO): UserDTO {
        val user: UserEntity = UserEntity()
        BeanUtils.copyProperties(userDTO, user)

//        val modelMapper = ModelMapper()
//        modelMapper.configuration.setMatchingStrategy(MatchingStrategies.STRICT)
//        val user: UserEntity = modelMapper.map(userDTO, UserEntity::class.java)
        user.encryptedPassword = bCryptPasswordEncoder.encode(userDTO.password)

        userRepository.save(user)
        return userDTO
    }

    override fun getUserDetailsByEmail(email: String): UserDTO {
        val user = userRepository.findByUserEmail(email)
            .orElseThrow { UsernameNotFoundException("User not found with email: $email") }

        val userDTO: UserDTO = UserDTO()
        BeanUtils.copyProperties(user, userDTO)

        return userDTO
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrBlank()) {
            throw UsernameNotFoundException("Username must not be null or empty")
        }

        val user = userRepository.findByUserEmail(username)
            .orElseThrow { UsernameNotFoundException("User not found with email: $username") }

        return User(
            user.email,
            user.encryptedPassword,
            true,
            true,
            true,
            true,
            emptyList()
        )
    }
}