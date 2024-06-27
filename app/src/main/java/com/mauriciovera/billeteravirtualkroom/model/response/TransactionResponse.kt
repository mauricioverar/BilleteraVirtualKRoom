package com.mauriciovera.billeteravirtualkroom.model.response

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
import java.util.Date

data class TransactionResponse(
    val previousPage: String?,
    val nextPage: String?,
    val data: List<Transaction<Any?>>//MutableList<TransactionResponse>
) // ok

data class Transaction<Date>(
    val id: Int? = null,
    val amount: Double,
    val concept: String,
    val date: java.util.Date = Date(),
    val type: TransactionType,
    val accountId: Int,
    @SerializedName("to_account_id")
    val accountDestinationId: Int,
    val userId: Int,
    val createdAt: Date? = null,
    val updatedAt: Date? = null
)

data class TransactionView(
    @DrawableRes val type: Int,
    val amount: String,
    val name: String,
    val date: String
)

enum class TransactionType {
    @SerializedName("payment")
    PAYMENT,

    @SerializedName("topup")
    TOP_UP
}

