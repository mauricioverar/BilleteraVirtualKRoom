package com.mauriciovera.billeteravirtualkroom.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
@Entity(tableName = "transaction_detail_table")
data class TransactionsDetailEntity(
    @PrimaryKey
    val id: Int,
    val amount: Double,
    val concept: String,
    val date: Date,//String
    val type: String,
    val accountId: Int,
    val userId: Int,
    val to_account_id: Int
)
