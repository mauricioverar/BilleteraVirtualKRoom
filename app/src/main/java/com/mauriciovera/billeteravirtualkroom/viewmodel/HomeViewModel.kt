package com.mauriciovera.billeteravirtualkroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
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

    private val _accounts = MutableLiveData<List<UserAccountModel>>() // Lista de AccountResponse
    val accounts: LiveData<List<UserAccountModel>> = _accounts

    private val _postAccountResult = MutableLiveData<Resource<AccountResponse>>()
    val postAccountResult: LiveData<Resource<AccountResponse>> = _postAccountResult

    private val _homeResult = MutableLiveData<String>()
    val homeResult: LiveData<String> = _homeResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val token = prefs.getToken().toString()


    fun transactions(id: Int) {

        if (token != null) {
            Log.d("result H token string", token)//sui

            //Log.d("result Home ** token", token) //ok tk
            viewModelScope.launch {

                val serviceAccounts = RetrofitHelper.getInstance().create(ApiService::class.java)
                try {
                    val response = serviceAccounts.getAccountsMe(token).awaitResponse()
                    if (response.isSuccessful) {  // si tiene? o no?***
                        Log.d("result Home**", response.body().toString())//ok
                        //val moneyValues = response.body()?.map { it.money }
                        val firstMoneyValue = response.body()?.firstOrNull()?.money
                        Log.d("result moneyValues**", firstMoneyValue.toString())//ok 150 si es [] se cae despues


                        _accounts.value =
                            response.body() ?: emptyList() // Actualiza la lista de AccountResponse

                        getTransactions(firstMoneyValue.toString())
                    } else {
                        // crear account ****************************
                        try {
                            val money = 150

                            val newAccount = serviceAccounts.postAccounts(token, money, id )//accountData

                            if (newAccount.money != null) {
                                Log.d("result H**", money.toString())//result successful|null|TransactionResponse(previousPage=null, nextPage=null, data=[])|null
                                if (newAccount.money == 0) {
                                    Log.d("result H**", "No se pudo crear la cuenta")
                                    _errorMessage.value = "No se pudo crear la cuenta"
                                }

                                // se cae ************ es []

                                getTransactions(newAccount.money.toString())

                                /*_postAccountResult.value = Resource.Success(newAccount)
                                _accounts.value = listOf(newAccount) // Actualiza la lista de AccountResponse*/
                            } else {
                                Log.d("result H**", "No se pudo crear la cuenta")
                            }
                            //_postAccountResult.value = newAccount.money // Éxito
                        } catch (e: Exception) {
                            Log.d("result H**", e.message.toString())
                            if (e is HttpException && e.code() == 401 || e is HttpException && e.code() == 403) {

                                Log.d("result H**", "Token no disponible")
                                _errorMessage.value = "Token no disponible"

                            }
                            //_postAccountResult.value = Resource.Error(e.message ?: "Error desconocido") // Error
                        }

                        //_errorMessage.value = "Error al obtener cuentas: ${response.code()}"
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
                    )//Response{protocol=http/1.1, code=200, message=OK, url=http://wallet-main.eba-ccwdurgr.us-east-1.elasticbeanstalk.com/transactions}
                    if (respuesta.isSuccessful) { // si tiene? o no?***
                        val datos = respuesta.body()
                        Log.d(
                            "result body Home",
                            datos.toString()
                        ) // ok ********************************************************************************************************
                        Log.d("result body size", datos?.data?.size.toString())
                        Log.d("result body data", datos?.data.toString())

                        Log.d("result body Home", datos?.data?.firstOrNull()?.amount.toString())
                        Log.d("result body Home", datos?.data?.firstOrNull()?.concept.toString())
                        Log.d("result body Home", datos?.data?.firstOrNull()?.date.toString())
                        Log.d("result body Home", datos?.data?.firstOrNull()?.type.toString())
                        Log.d("result body Home", datos?.data?.firstOrNull()?.accountId.toString())
                        Log.d("result body Home", datos?.data?.firstOrNull()?.accountDestinationId.toString())
                        Log.d("result body Home", datos?.data?.firstOrNull()?.userId.toString())
                        Log.d("result body Home", datos?.data?.firstOrNull()?.createdAt.toString())
                        Log.d("result body Home", datos?.data?.firstOrNull()?.updatedAt.toString())
                        Log.d("result body Home", datos?.data?.firstOrNull()?.id.toString())//***************************************


                        if (datos?.data?.size == 0) {
                            Log.d("result body Home[]", "No hay transacciones")
                        }

                        // mostrar en recyclerview los datos
                        // TransactionResponse(previousPage=null, nextPage=null, data=[Transaction(id=6573, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=1, accountDestinationId=5, userId=3646, createdAt=2024-06-25T16:45:42.000Z, updatedAt=2024-06-25T16:45:42.000Z), Transaction(id=6576, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=1, accountDestinationId=6, userId=3646, createdAt=2024-06-25T16:50:34.000Z, updatedAt=2024-06-25T16:50:34.000Z), Transaction(id=6577, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=1, accountDestinationId=4, userId=3646, createdAt=2024-06-25T16:52:16.000Z, updatedAt=2024-06-25T16:52:16.000Z), Transaction(id=6578, amount=500.0, concept=Pago de honorarios, date=Wed Oct 26 15:00:00 GMT 2022, type=null, accountId=2, accountDestinationId=4, userId=3646, createdAt=2024-06-25T16:53:39.000Z, updatedAt=2024-06-25T16:53:39.000Z)])


                        val msj = "result successful"
                        val monto = datos?.data?.firstOrNull()?.amount?.toInt()
                        //Log.d("result monto", monto.toString())
                        /*_homeResult.value =
                            "${msj}|${balance}|${datos?.toString()}|${monto}"*/
                        _homeResult.value =
                            "${msj}|${balance}|${datos?.data.toString()}|${monto}"
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