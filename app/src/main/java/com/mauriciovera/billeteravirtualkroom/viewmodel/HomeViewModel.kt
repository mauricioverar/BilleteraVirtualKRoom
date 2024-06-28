package com.mauriciovera.billeteravirtualkroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauriciovera.billeteravirtualkroom.model.UserAccountModel
import com.mauriciovera.billeteravirtualkroom.model.UserApplication.Companion.prefs
import com.mauriciovera.billeteravirtualkroom.model.network.ApiService
import com.mauriciovera.billeteravirtualkroom.model.network.RetrofitHelp
import com.mauriciovera.billeteravirtualkroom.model.response.AccountResponse
import com.mauriciovera.billeteravirtualkroom.model.response.TransactionResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class HomeViewModel: ViewModel() {

    private val _accounts = MutableLiveData<List<UserAccountModel>>() // Lista de AccountResponse
    val accounts: LiveData<List<UserAccountModel>> = _accounts

    private val _homeResult = MutableLiveData<String>()
    val homeResult: LiveData<String> = _homeResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val token = prefs.getToken().toString()


    fun transactions() {//(token: String)
        //val token = prefs.getToken().toString()

        if (token != null) {
            Log.d("result H token string", token)//sui

        //Log.d("result Home ** token", token) //ok tk
        viewModelScope.launch {

            val serviceAccounts = RetrofitHelp.getInstance().create(ApiService::class.java)
            try {
                val response = serviceAccounts.getAccountsMe("Bearer $token").awaitResponse()
                if (response.isSuccessful) {
                    Log.d("result Home**", response.body().toString())//ok
                    //val moneyValues = response.body()?.map { it.money }
                    val firstMoneyValue = response.body()?.firstOrNull()?.money
                    Log.d("result moneyValues**", firstMoneyValue.toString())//ok 150


                    _accounts.value = response.body() ?: emptyList() // Actualiza la lista de AccountResponse

                    getTransactions(firstMoneyValue.toString())
                } else {
                    _errorMessage.value = "Error al obtener cuentas: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
            }
            /*val serviceAccounts = RetrofitHelp.getInstance().create(ApiService::class.java)
            serviceAccounts.getAccountsMe("Bearer $token")
                .enqueue(object : Callback<AccountResponse>{
                    override fun onResponse(
                        p0: Call<AccountResponse>,
                        p1: Response<AccountResponse>
                    ) {
                        Log.d("result Home*", p1.toString())
                        if (p1.isSuccessful) {
                            val datos = p1.body()
                            Log.d("result Home**", datos.toString())
                            //_homeResult.value = "result successful"
                            getTransactions(token)
                        }
                    }

                    override fun onFailure(p0: Call<AccountResponse>, p1: Throwable) {
                        Log.d("result Homfail", p1.message.toString())
                    }
                })*/

            /*val serviceTransactions = RetrofitHelp.getInstance().create(ApiService::class.java)
            Log.d("result Home tk*string", token) //ok

            serviceTransactions.getTransactions("Bearer $token")//("Bearer $token")
                .enqueue(object : Callback<TransactionResponse> {
                    override fun onResponse(
                        call: Call<TransactionResponse>,
                        respuesta: Response<TransactionResponse>
                    ) {
                        Log.d("result respuesta", respuesta.toString())//Response{protocol=http/1.1, code=200, message=OK, url=http://wallet-main.eba-ccwdurgr.us-east-1.elasticbeanstalk.com/transactions}
                        if (respuesta.isSuccessful) {
                            val datos = respuesta.body()
                            Log.d(
                                "result body Home",
                                datos.toString()
                            ) // ok *******************************

                            // mostrar en recyclerview los datos
                            // TransactionResponse(previousPage=null, nextPage=null, data=[Transaction(id=6573, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=1, accountDestinationId=5, userId=3646, createdAt=2024-06-25T16:45:42.000Z, updatedAt=2024-06-25T16:45:42.000Z), Transaction(id=6576, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=1, accountDestinationId=6, userId=3646, createdAt=2024-06-25T16:50:34.000Z, updatedAt=2024-06-25T16:50:34.000Z), Transaction(id=6577, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=1, accountDestinationId=4, userId=3646, createdAt=2024-06-25T16:52:16.000Z, updatedAt=2024-06-25T16:52:16.000Z), Transaction(id=6578, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=2, accountDestinationId=4, userId=3646, createdAt=2024-06-25T16:53:39.000Z, updatedAt=2024-06-25T16:53:39.000Z)])


                            *//*Log.d(
                                "result body",
                                datos?.first_name.toString() + datos?.points.toString() + token
                            )

                            val monto = datos?.points?.toInt()
                            _userBalance.value = monto.toString()
                            Log.d("result monto", monto.toString())
                            val msj = "Login successful"
                            _loginResult.value =
                                "${msj}|${_userBalance.value}|${datos?.first_name.toString()}|${token}"*//*
                            _homeResult.value = "result successful"
                        } else {
                            Log.d(
                                "result error",
                                respuesta.errorBody().toString()
                            )
                            _homeResult.value = "Error de conexión"
                        }
                    }

                    override fun onFailure(p0: Call<TransactionResponse>, p1: Throwable) {
                        Log.d("result error", p1.message.toString())
                        _homeResult.value = "Error de conexión|0"
                    }
                })*/
        }
    }
    }

    private fun getTransactions(balance: String) {
        val serviceTransactions = RetrofitHelp.getInstance().create(ApiService::class.java)
        Log.d("result Home tk*string", token) //ok

        serviceTransactions.getTransactions("Bearer $token")//("Bearer $token")
            .enqueue(object : Callback<TransactionResponse> {
                override fun onResponse(
                    call: Call<TransactionResponse>,
                    respuesta: Response<TransactionResponse>
                ) {
                    Log.d("result respuesta", respuesta.toString())//Response{protocol=http/1.1, code=200, message=OK, url=http://wallet-main.eba-ccwdurgr.us-east-1.elasticbeanstalk.com/transactions}
                    if (respuesta.isSuccessful) {
                        val datos = respuesta.body()
                        Log.d(
                            "result body Home",
                            datos.toString()
                        ) // ok *******************************

                        // mostrar en recyclerview los datos
                        // TransactionResponse(previousPage=null, nextPage=null, data=[Transaction(id=6573, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=1, accountDestinationId=5, userId=3646, createdAt=2024-06-25T16:45:42.000Z, updatedAt=2024-06-25T16:45:42.000Z), Transaction(id=6576, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=1, accountDestinationId=6, userId=3646, createdAt=2024-06-25T16:50:34.000Z, updatedAt=2024-06-25T16:50:34.000Z), Transaction(id=6577, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=1, accountDestinationId=4, userId=3646, createdAt=2024-06-25T16:52:16.000Z, updatedAt=2024-06-25T16:52:16.000Z), Transaction(id=6578, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=2, accountDestinationId=4, userId=3646, createdAt=2024-06-25T16:53:39.000Z, updatedAt=2024-06-25T16:53:39.000Z)])


                        /*Log.d(
                            "result body",
                            datos?.first_name.toString() + datos?.points.toString() + token
                        )

                        val monto = datos?.points?.toInt()
                        _userBalance.value = monto.toString()
                        Log.d("result monto", monto.toString())
                        val msj = "Login successful"
                        _loginResult.value =
                            "${msj}|${_userBalance.value}|${datos?.first_name.toString()}|${token}"*/

                        val msj = "result successful"
                        val monto = datos?.data?.firstOrNull()?.amount?.toInt()
                        //Log.d("result monto", monto.toString())
                        _homeResult.value =
                            "${msj}|${balance}|${datos?.toString()}|${monto}"
                    } else {
                        Log.d(
                            "result error",
                            respuesta.errorBody().toString()
                        )
                        _homeResult.value = "Error de conexión"
                    }
                }

                override fun onFailure(p0: Call<TransactionResponse>, p1: Throwable) {
                    Log.d("result error", p1.message.toString())
                    _homeResult.value = "Error de conexión|0"
                }
            })
    }

}