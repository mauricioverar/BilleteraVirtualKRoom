package com.mauriciovera.billeteravirtualkroom.model

data class TransactionModel(
    val id: Int,
    val amount: Double,
    val date: Any?,//String
    val type: String,
    val concept: Any
)
