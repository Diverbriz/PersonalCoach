package com.example.personalcoach.data.network

import com.example.personalcoach.domain.model.user.JwtResponse
import com.example.personalcoach.domain.model.user.LoginRequest
import com.example.personalcoach.domain.model.user.SignupRequest
import com.example.personalcoach.domain.model.user.SignupResponse
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/auth/signin")
    suspend fun signIn(@Body user: LoginRequest): JwtResponse

    @POST("api/auth/signup")
    suspend fun signUp(@Body request: SignupRequest): SignupResponse

}