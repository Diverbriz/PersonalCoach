package com.example.personalcoach.domain.model.user

data class SignupRequest (
    val username: String? = null,
    val email: String? = null,
    val roles: Set<String>? = null,
    val password: String? = null
)