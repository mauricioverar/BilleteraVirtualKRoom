package com.mauriciovera.billeteravirtualkroom.model.response

import java.util.Date

data class AccountResponse(
    val id: Int? = null,
    val creationDate: Date,// ?=null
    val money: Int = 150,
    val isBlocked: Boolean? = false,
    val userId: Int
)
