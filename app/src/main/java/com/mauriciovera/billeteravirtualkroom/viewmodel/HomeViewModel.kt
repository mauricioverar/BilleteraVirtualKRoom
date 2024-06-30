package com.mauriciovera.billeteravirtualkroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.mauriciovera.billeteravirtualkroom.model.TransactionModel
import com.mauriciovera.billeteravirtualkroom.model.UserAccountModel
import com.mauriciovera.billeteravirtualkroom.model.UserApplication.Companion.prefs
import com.mauriciovera.billeteravirtualkroom.model.network.ApiService
import com.mauriciovera.billeteravirtualkroom.model.network.RetrofitHelper
import com.mauriciovera.billeteravirtualkroom.model.response.AccountResponse
import com.mauriciovera.billeteravirtualkroom.model.response.TransactionResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.awaitResponse

class HomeViewModel : ViewModel() {

    private val _accounts = MutableLiveData<List<UserAccountModel>>()
    val accounts: LiveData<List<UserAccountModel>> = _accounts

    private val _postAccountResult = MutableLiveData<Resource<AccountResponse>>()
    val postAccountResult: LiveData<Resource<AccountResponse>> = _postAccountResult

    private val _homeResult = MutableLiveData<List<TransactionModel>>()
    val homeResult: LiveData<List<TransactionModel>> = _homeResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val token = prefs.getToken().toString()


    fun transactions(id: Int) {

        if (token != null) {
            Log.d("result H token string", token)

            viewModelScope.launch {

                val serviceAccounts = RetrofitHelper.getInstance().create(ApiService::class.java)
                try {
                    val response = serviceAccounts.getAccountsMe(token).awaitResponse()
                    if (response.isSuccessful) {
                        Log.d("result Home**", response.body().toString())
                        val firstMoneyValue = response.body()?.firstOrNull()?.money
                        Log.d("result moneyValues**", firstMoneyValue.toString())

                        _accounts.value =
                            response.body() ?: emptyList()

                        getTransactions(firstMoneyValue.toString())
                    } else {
                        try {
                            val money = 150

                            val newAccount = serviceAccounts.postAccounts(token, money, id )

                            if (newAccount.money != null) {
                                Log.d("result H**", money.toString())
                                if (newAccount.money == 0) {
                                    Log.d("result H**", "No se pudo crear la cuenta")
                                    _errorMessage.value = "No se pudo crear la cuenta"
                                }

                                getTransactions(newAccount.money.toString())

                            } else {
                                Log.d("result H**", "No se pudo crear la cuenta")
                            }
                        } catch (e: Exception) {
                            Log.d("result H**", e.message.toString())
                            if (e is HttpException && e.code() == 401 || e is HttpException && e.code() == 403) {

                                Log.d("result H**", "Token no disponible")
                                _errorMessage.value = "Token no disponible"

                            }
                        }

                    }
                } catch (e: Exception) {
                    _errorMessage.value = "Error de red: ${e.message}"
                }
            }
        } else {
            Log.d("result H error tk", token)
            _errorMessage.value = "Token no disponible"
        }
    }

    private fun getTransactions(balance: String) {
        val serviceTransactions = RetrofitHelper.getInstance().create(ApiService::class.java)
        Log.d("result Home tk*string", token) //ok

        serviceTransactions.getTransactions(token)
            .enqueue(object : Callback<TransactionResponse> {
                override fun onResponse(
                    call: Call<TransactionResponse>,
                    respuesta: Response<TransactionResponse>
                ) {
                    Log.d(
                        "result respuesta",
                        respuesta.toString()
                    )
                    if (respuesta.isSuccessful) {
                        val datos = respuesta.body()
                        Log.d(
                            "result body Home",
                            datos.toString()
                        )

                        if (datos?.data?.size == 0) {
                            Log.d("result body Home[]", "No hay transacciones")
                        }

                        val msj = "result successful"
                        val monto = datos?.data?.firstOrNull()?.amount?.toInt()

                        _homeResult.value = datos?.data?.map { transaction ->
                            TransactionModel(
                                id = transaction.id,
                                amount = transaction.amount,
                                concept = transaction.concept,
                                date = transaction.date,
                                type = transaction.type?.toString() ?: "Desconocido"
                            )
                        } ?: emptyList()

                    } else {
                        Log.d(
                            "result error",
                            respuesta.errorBody().toString()
                        )
                    }
                }

                override fun onFailure(p0: Call<TransactionResponse>, p1: Throwable) {
                    Log.d("result error", p1.message.toString())
                }
            })
    }

}