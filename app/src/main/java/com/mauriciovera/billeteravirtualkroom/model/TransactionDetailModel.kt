package com.mauriciovera.billeteravirtualkroom.model

import java.util.Date

data class TransactionDetailModel(
    val id: Int,
    val amount: Double,
    val concept: String,
    val date: Date,//String
    val type: String,
    val accountId: Int,
    val userId: Int,
    val to_account_id: Int
)
