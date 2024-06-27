package com.mauriciovera.billeteravirtualkroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauriciovera.billeteravirtualkroom.model.UserApplication.Companion.prefs
import com.mauriciovera.billeteravirtualkroom.model.network.ApiService
import com.mauriciovera.billeteravirtualkroom.model.network.RetrofitHelp
import com.mauriciovera.billeteravirtualkroom.model.response.TransactionResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private val _homeResult = MutableLiveData<String>()

    val homeResult: LiveData<String> = _homeResult

    fun transactions() {//(token: String)
        val token = prefs.getToken().toString()

        if (token != null) {
            Log.d("result Home ** token string", token)//sui

        //Log.d("result Home ** token", token) //ok tk
        viewModelScope.launch {
            val serviceTransactions = RetrofitHelp.getInstance().create(ApiService::class.java)
            Log.d("result Home tk*string", token) //ok

            serviceTransactions.getTransactions("Bearer $token")//("Bearer $token")
                .enqueue(object : Callback<TransactionResponse> {
                    override fun onResponse(
                        call: Call<TransactionResponse>,
                        respuesta: Response<TransactionResponse>
                    ) {
                        Log.d("result respuesta", respuesta.toString())
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
                })
        }
    }
    }
}