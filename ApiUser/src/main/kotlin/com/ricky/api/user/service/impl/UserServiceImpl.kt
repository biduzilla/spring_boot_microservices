package com.ricky.api.user.service.impl

import com.ricky.api.user.data.UserEntity
import com.ricky.api.user.dto.UserDTO
import com.ricky.api.user.repository.UserRepository
import com.ricky.api.user.service.UserService
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun createUser(userDTO: UserDTO): UserDTO {
        val user: UserEntity = UserEntity()
        BeanUtils.copyProperties(userDTO, user)

//        val modelMapper = ModelMapper()
//        modelMapper.configuration.setMatchingStrategy(MatchingStrategies.STRICT)
//        val user: UserEntity = modelMapper.map(userDTO, UserEntity::class.java)
        user.encryptedPassword = "teste"

        userRepository.save(user)
        return  userDTO
    }
}