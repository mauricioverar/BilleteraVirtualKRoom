package com.mauriciovera.billeteravirtualkroom.model.network

import com.mauriciovera.billeteravirtualkroom.model.Constants
import com.mauriciovera.billeteravirtualkroom.model.UserAccountModel
import com.mauriciovera.billeteravirtualkroom.model.UserDetailsModel
import com.mauriciovera.billeteravirtualkroom.model.UserModel
import com.mauriciovera.billeteravirtualkroom.model.response.AccountResponse
import com.mauriciovera.billeteravirtualkroom.model.response.LoginResponse
import com.mauriciovera.billeteravirtualkroom.model.response.SignupResponse
import com.mauriciovera.billeteravirtualkroom.model.response.TransactionResponse
import com.mauriciovera.billeteravirtualkroom.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

// consumir endpoints - login - signup - users - accounts - me - transactions
interface ApiService {

    @Headers("Content-Type: application/json")
    /// Login
    @POST(Constants.API_PATH + Constants.LOGIN_PATH)
    suspend fun login(@Body data: UserModel): LoginResponse//Call<LoginResponse>

    // signup
    // * Users
    @POST("users")
    fun postUsers(@Body data: UserDetailsModel): Call<SignupResponse>

    // * Accounts
    @POST("accounts")
    suspend fun postAccounts(@Header("Authorization") token: String, @Body data: Int, id: Int): AccountResponse//UserAccountModel//data: Int, id: Int

    // * Accounts
    @GET("accounts/me")
    fun getAccountsMe(@Header("Authorization") token: String): Call<List<UserAccountModel>>

    // me
    @GET("auth/me")
    suspend fun getInfoMe(@Header("Authorization") token: String): UserResponse//UserDetailsModel?//id

    // * Transactions
    @GET("transactions")
    fun getTransactions(@Header("Authorization") token: String): Call<TransactionResponse>

}