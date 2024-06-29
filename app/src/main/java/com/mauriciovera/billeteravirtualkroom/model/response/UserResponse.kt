package com.mauriciovera.billeteravirtualkroom.model.response

import com.google.gson.annotations.SerializedName
import com.mauriciovera.billeteravirtualkroom.model.Constants

data class UserResponse(
    @SerializedName(Constants.ID_PARAM) val id: Int,
    @SerializedName(Constants.NAME_PARAM) val first_name: String,
    @SerializedName(Constants.LASTNAME_PARAM) val last_name: String,
    @SerializedName(Constants.EMAIL_PARAM) val email: String,
    @SerializedName(Constants.PASSWORD_PARAM) val password: String,
    @SerializedName(Constants.POINTS_PARAM) val points: Double,
    @SerializedName(Constants.ROLE_ID_PARAM) val roleId: Int
)
