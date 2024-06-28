package com.mauriciovera.billeteravirtualkroom.model.network

import com.mauriciovera.billeteravirtualkroom.model.Constants
import com.mauriciovera.billeteravirtualkroom.model.UserAccountModel
import com.mauriciovera.billeteravirtualkroom.model.UserDetailsModel
import com.mauriciovera.billeteravirtualkroom.model.UserModel
import com.mauriciovera.billeteravirtualkroom.model.response.AccountResponse
import com.mauriciovera.billeteravirtualkroom.model.response.LoginResponse
import com.mauriciovera.billeteravirtualkroom.model.response.SignupResponse
import com.mauriciovera.billeteravirtualkroom.model.response.TransactionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

// consumir endpoint 1-login 2-me 3-signup 4-users 5- accounts 6-transactions
interface ApiService {

    @Headers("Content-Type: application/json")
    /// Login
    @POST(Constants.API_PATH + Constants.LOGIN_PATH)
    fun login(@Body data: UserModel): Call<LoginResponse>//<LoginResponse>

    // me
    @GET("auth/me")
    fun getInfoMe(@Header("Authorization") token: String): Call<UserDetailsModel> //Response<UserModel> ok2

    // signup
    // * Users
    @POST("users")
    fun postUsers(@Body data: UserDetailsModel): Call<SignupResponse>

    // * Accounts
    @GET("accounts/me")
    fun getAccountsMe(@Header("Authorization") token: String): Call<List<UserAccountModel>>//Call<List<AccountResponse>>

    // * Transactions
    @GET("transactions")
    fun getTransactions(@Header("Authorization") token: String): Call<TransactionResponse>

}