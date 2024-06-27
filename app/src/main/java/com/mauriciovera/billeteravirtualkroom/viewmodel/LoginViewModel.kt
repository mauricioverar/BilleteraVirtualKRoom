package com.mauriciovera.billeteravirtualkroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauriciovera.billeteravirtualkroom.model.UserApplication.Companion.prefs
import com.mauriciovera.billeteravirtualkroom.model.UserDetailsModel
import com.mauriciovera.billeteravirtualkroom.model.UserModel
import com.mauriciovera.billeteravirtualkroom.model.network.ApiService
import com.mauriciovera.billeteravirtualkroom.model.network.RetrofitHelp
import com.mauriciovera.billeteravirtualkroom.model.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<String>()
    private val _userBalance = MutableLiveData<String>()

    val loginResult: LiveData<String> = _loginResult


    fun login(email: String, password: String) {
        viewModelScope.launch {
            val serviceLogin = RetrofitHelp.getInstance().create(ApiService::class.java)
            Log.d("result viewmodel", email + password)

            serviceLogin.login(UserModel(email, password))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        when (response.code()) {
                            200 -> {
                                val result = response.body()
                                Log.d("result body", result.toString())
                                val token = result?.accessToken.toString()
                                Log.d("result token", token)

                                prefs.saveToken(token)//"Bearer $token"

                                try {
                                    Log.d("result try Bearer ", token)
                                    serviceLogin.getInfoMe("Bearer $token")
                                        .enqueue(object : Callback<UserDetailsModel> {
                                            override fun onResponse(
                                                call: Call<UserDetailsModel>,
                                                respuesta: Response<UserDetailsModel>
                                            ) {
                                                if (respuesta.isSuccessful) {
                                                    val datos = respuesta.body()
                                                    Log.d("result body", datos.toString())
                                                    Log.d(
                                                        "result body",
                                                        datos?.first_name.toString() + datos?.points.toString()
                                                    )

                                                    try {
                                                        Log.d("result try Bearer ", token)
                                                        serviceLogin.getInfoMe("Bearer $token")
                                                            .enqueue(object : Callback<UserDetailsModel> {
                                                                override fun onResponse(
                                                                    call: Call<UserDetailsModel>,
                                                                    respuesta: Response<UserDetailsModel>
                                                                ) {
                                                                    if (respuesta.isSuccessful) {
                                                                        val datos = respuesta.body()
                                                                        Log.d("result body", datos.toString())
                                                                        Log.d(
                                                                            "result body",
                                                                            datos?.first_name.toString() + datos?.points.toString()
                                                                        )

                                                                        val monto = datos?.points?.toInt()
                                                                        _userBalance.value = monto.toString()
                                                                        Log.d("result monto", monto.toString())
                                                                        val msj = "Login successful"
                                                                        _loginResult.value =
                                                                            "${msj}|${_userBalance.value}|${datos?.first_name.toString()}"
                                                                    } else {
                                                                        Log.d(
                                                                            "result error",
                                                                            respuesta.errorBody().toString()
                                                                        )
                                                                    }
                                                                }

                                                                override fun onFailure(
                                                                    p0: Call<UserDetailsModel>,
                                                                    p1: Throwable
                                                                ) {
                                                                    Log.d("result error", p1.message.toString())
                                                                }
                                                            })
                                                    } catch (e: Exception) {
                                                        Log.d("result error", e.message.toString())
                                                    }

                                                    val monto = datos?.points?.toInt()
                                                    _userBalance.value = monto.toString()
                                                    Log.d("result monto", monto.toString())
                                                    val msj = "Login successful"
                                                    _loginResult.value =
                                                        "${msj}|${_userBalance.value}|${datos?.first_name.toString()}"
                                                } else {
                                                    Log.d(
                                                        "result error",
                                                        respuesta.errorBody().toString()
                                                    )
                                                }
                                            }

                                            override fun onFailure(
                                                p0: Call<UserDetailsModel>,
                                                p1: Throwable
                                            ) {
                                                Log.d("result error", p1.message.toString())
                                            }
                                        })
                                } catch (e: Exception) {
                                    Log.d("result error", e.message.toString())
                                }


                            }

                            400 -> {
                                _loginResult.value = "Error 400|0"
                            }

                            else -> {
                                _loginResult.value = "Error 500 Internal Server|0"
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e("result err82", t.message.toString())
                        _loginResult.value = "Error de conexión|0"
                    }
                })
        }
    }
}