package com.example.personalcoach.domain.model.user

data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val roles: List<Role>
)

data class Role(
    val id: Long,
    val name: String
)