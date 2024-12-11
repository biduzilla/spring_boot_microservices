package com.ricky.api.user.controllers

import com.ricky.api.user.dto.UserDTO
import com.ricky.api.user.models.CreateUserRequest
import com.ricky.api.user.models.CreateUserResponse
import com.ricky.api.user.service.UserService
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.beans.BeanUtils
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/users")
class UserController(private val env: Environment, private val userService: UserService) {

    @GetMapping("/status/check")
    fun status(): String {
        return "Working port number " + env.getProperty("local.server.port")
    }

    @PostMapping
    fun createUser(@RequestBody @Valid createUserRequest: CreateUserRequest): ResponseEntity<CreateUserResponse> {
        val modelMapper = ModelMapper()
        modelMapper.configuration.setMatchingStrategy(MatchingStrategies.STRICT)
        val userDTO = modelMapper.map(createUserRequest, UserDTO::class.java)
        val userSave = userService.createUser(userDTO)
        val userResponse = modelMapper.map(userSave, CreateUserResponse::class.java)
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse)
    }
}