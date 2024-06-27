package com.mauriciovera.billeteravirtualkroom.model.network

import com.mauriciovera.billeteravirtualkroom.model.Constants
import com.mauriciovera.billeteravirtualkroom.model.UserDetailsModel
import com.mauriciovera.billeteravirtualkroom.model.UserModel
import com.mauriciovera.billeteravirtualkroom.model.response.LoginResponse
import com.mauriciovera.billeteravirtualkroom.model.response.SignupResponse
import com.mauriciovera.billeteravirtualkroom.model.response.TransactionResponse
import retrofit2.Call
import retrofit2.http.Body
//import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
//import retrofit2.http.PATCH
import retrofit2.http.POST
//import retrofit2.http.PUT
//import retrofit2.http.Path

interface ApiService { // consumir 4 endpoint 1-signup 2-login 3-me 4-users

    // * Authentication

    // 1-Login

    @Headers("Content-Type: application/json")
    /// auth + /+ Login ****************************
    @POST(Constants.API_PATH + Constants.LOGIN_PATH)
    fun login(@Body data: UserModel): Call<LoginResponse>//<LoginResponse>

    // 2 -me
    @GET("auth/me") // **************************************
    fun getInfoMe(@Header("Authorization") token: String): Call<UserDetailsModel> //Response<UserModel> ok2

    // 3-signup
    // * Users
    @POST("users") // ***************************************
    fun postUsers(@Body data: UserDetailsModel): Call<SignupResponse>
    //suspend fun postUsers

    // 4-users
    /*@GET("users") // **************************************************
    suspend fun getUsers()*/

    /*@GET("users/{id}") // **************************************************
    suspend fun getUsersId(@Path("id") id: Int)*/

    /*@PUT("users/{id}") // **************************************************
    suspend fun putUsersId(@Path("id") id: Int, @Body data: UserModel)*/

    /*@DELETE("users/{id}")
    suspend fun deleteUsersId(@Path("id") id: Int)*/

    /*@PATCH("users/block/{id}")
    suspend fun patchUsersBlockId(@Path("id") id: Int)*/

    /*@PATCH("users/unblock/{id}")
    suspend fun patchUsersUnblockId(@Path("id") id: Int)*/

    /*@PATCH("users/resetPassword/{id}")
    suspend fun patchUsersResetPasswordId(@Path("id") id: Int)*/

    /*@PATCH("users/product/{productId}")
    suspend fun patchUsersProductId(@Path("id") id: Int)*/

    // * Roles
    /*@POST("roles")
    suspend fun postRoles(@Body data: UserModel)*/

    /*@GET("roles")
    suspend fun getRoles()*/

    /*@GET("roles/{id}")
    suspend fun getRolesId(@Path("id") id: Int)*/

    /*@PUT("roles/{id}")
    suspend fun putRolesId(@Path("id") id: Int, @Body data: UserModel)*/

    /*@DELETE("roles/{id}")
    suspend fun deleteRolesId(@Path("id") id: Int)*/

    // * Accounts
    /*@POST("accounts") // ***************************************
    suspend fun postAccounts(@Body data: UserModel)*/

    /*@GET("accounts") // **************************************
    suspend fun getAccounts()*/

    /*@GET("accounts/me") // **************************************
    suspend fun getAccountsMe()*/

    /*@POST("accounts/{id}")
    suspend fun postAccountsId(@Path("id") id: Int)*/

    /*@GET("accounts/{id}")
    suspend fun getAccountsId(@Path("id") id: Int)*/

    /*@PUT("accounts/{id}")
    suspend fun putAccountsId(@Path("id") id: Int, @Body data: UserModel)*/

    /*@DELETE("accounts/{id}")
    suspend fun deleteAccountsId(@Path("id") id: Int)*/

    // * Transactions
    /*@POST("transactions") // ***************************************
    suspend fun postTransactions(@Body data: UserModel)*/

    @GET("transactions")
    fun getTransactions(@Header("Authorization") token: String): Call<TransactionResponse>
    //suspend

    /*@GET("transactions/{id}")
    suspend fun getTransactionsId(@Path("id") id: Int)*/

    /*@PUT("transactions/{id}")
    suspend fun putTransactionsId(@Path("id") id: Int, @Body data: UserModel)*/

    /*@DELETE("transactions/{id}")
    suspend fun deleteTransactionsId(@Path("id") id: Int)*/

    // * FixedTermDeposits
    /*@POST("fixeddeposits")
    suspend fun postFixedTermDeposits(@Body data: UserModel)*/

    /*@GET("fixeddeposits")
    suspend fun getFixedTermDeposits()*/

    /*@GET("fixeddeposits/{id}")
    suspend fun getFixedTermDepositsId(@Path("id") id: Int)*/

    /*@PUT("fixeddeposits/{id}")
    suspend fun putFixedTermDepositsId(@Path("id") id: Int, @Body data: UserModel)*/

    /*@DELETE("fixeddeposits/{id}")
    suspend fun deleteFixedTermDepositsId(@Path("id") id: Int)*/

}