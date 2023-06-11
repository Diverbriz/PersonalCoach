package com.example.personalcoach.domain.model.user

class JwtResponse(
    var token: String,
    var id: Long,
    var username: String,
    var email: String,
    var roles: List<String>
) {
    var type = "Bearer"

}

