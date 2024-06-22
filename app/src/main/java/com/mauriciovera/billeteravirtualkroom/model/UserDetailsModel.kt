package com.mauriciovera.billeteravirtualkroom.model

import com.google.gson.annotations.SerializedName

data class UserDetailsModel(
    @SerializedName(Constants.NAME_PARAM) val first_name: String,
    @SerializedName(Constants.LASTNAME_PARAM) val last_name: String,
    @SerializedName(Constants.EMAIL_PARAM) val email: String,
    @SerializedName(Constants.PASSWORD_PARAM) val password: String,
    @SerializedName(Constants.POINTS_PARAM) val points: Double,
    @SerializedName(Constants.ROLE_ID_PARAM) val roleId: Int
)
