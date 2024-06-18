package com.mauriciovera.billeteravirtualkroom.model.response

data class LoginResponse(var token: String) : SuccessResponse(token)//token //accessToken
