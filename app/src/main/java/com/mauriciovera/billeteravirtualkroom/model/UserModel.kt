package com.mauriciovera.billeteravirtualkroom.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName(Constants.EMAIL_PARAM) val email:String,
    @SerializedName(Constants.PASSWORD_PARAM) val password:String
)
