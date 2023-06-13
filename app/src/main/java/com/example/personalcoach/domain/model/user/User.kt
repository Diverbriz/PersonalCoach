package com.example.personalcoach.domain.model.user

data class User (
    val id: Long? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,

    val roles: Set<Role> = HashSet()
)

data class Role(
    val id: Long? =null,
    val name: ERole? =null
)

enum class Gender {
    FEMALE, MALE
}

enum class ERole {
    ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN
}



