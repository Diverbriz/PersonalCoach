package com.example.personalcoach.domain.model.user

data class JwtResponse(
    val token: String,
    val type: String,
    val id: Long,
    val username: String,
    val email: String,
    val roles: List<String>
)

