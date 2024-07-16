package com.ricky.api.user.data

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "users")
data class UserEntity(

    @Id
    @GeneratedValue
    var id: Long = 0,

    @Column(nullable = false, length = 50)
    var firstName: String = "",

    @Column(nullable = false, length = 50)
    var lastName: String = "",

    @Column(nullable = false, length = 120, unique = true)
    var email: String = "",

    @Column(nullable = false, unique = true)
    var userId: String = "",

    @Column(nullable = false, unique = true)
    var encryptedPassword: String = "",
) : Serializable
