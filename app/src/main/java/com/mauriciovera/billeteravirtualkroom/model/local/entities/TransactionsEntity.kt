package com.mauriciovera.billeteravirtualkroom.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_list_table")
data class TransactionsEntity(
    @PrimaryKey
    val id: Int,
    val amount: Double,
    val concept: String,
    val date: java.util.Date,//String Date
    val type: String
)
/*
"amount": 500,
"concept": "Pago de honorarios",
"date": "2022-10-26 15:00:00",
"type": "topup|payment",
"accountId": 1,
"userId": 4,
"to_account_id": 5*/
