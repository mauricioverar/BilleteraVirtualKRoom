package com.mauriciovera.billeteravirtualkroom.model

import com.google.gson.annotations.SerializedName

data class UserDetailsModel(
    val first_name: String,
    val last_name: String,
    @SerializedName(Constants.EMAIL_PARAM) val email: String,
    @SerializedName(Constants.PASSWORD_PARAM) val password: String,
    val points: Double
)
