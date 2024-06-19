package com.mauriciovera.billeteravirtualkroom.model.response

data class LoginResponse(var accessToken: String) : SuccessResponse(accessToken)//token //accessToken
