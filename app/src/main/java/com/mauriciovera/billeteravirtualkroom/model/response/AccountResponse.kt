package com.mauriciovera.billeteravirtualkroom.model.response

import java.util.Date

data class AccountResponse(
    val id: Int? = null,
    val creationDate: Date,
    val money: Double,
    val isBlocked: Boolean? = false,
    val userId: Int
)
