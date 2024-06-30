package com.mauriciovera.billeteravirtualkroom.model

data class TransactionModel(
    val id: Int,
    val amount: Double,
    val date: Any?,
    val type: String,
    val concept: Any
)
