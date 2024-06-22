package com.mauriciovera.billeteravirtualkroom.model.response

data class SignupResponse(
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val roleId: Int,
    val points: Double
)