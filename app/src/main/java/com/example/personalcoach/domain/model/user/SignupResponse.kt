package com.example.personalcoach.domain.model.user

data class SignupResponse(
    val id: Long,
    val username: String,
    val email: String,
    val password: String,
    val roles: Set<Role>
)
