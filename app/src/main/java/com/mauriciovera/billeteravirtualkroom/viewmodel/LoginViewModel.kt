package com.mauriciovera.billeteravirtualkroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    //resultado del login (éxito o error).
    private val _loginResult = MutableLiveData<String>()
    private val _userBalance = MutableLiveData<String>()

    //LiveData público que expone el resultado del login a la vista
    val loginResult: LiveData<String> = _loginResult
    val userBalance: LiveData<String> = _userBalance


    fun login(email: String, password: String) {
        //corrutina en el viewModelScope.
        viewModelScope.launch {
            //instancia de LoginService utilizando RetrofitClient.
            val serviceLogin = RetrofitHelp.getInstance().create(ApiService::class.java)
            //llamada a la función login de LoginService.
            Log.d("result viewmodel", email.toString() + password.toString())//ok

            serviceLogin.login(UserModel(email, password)).enqueue(object : Callback<LoginResponse> {
                //implementación de Callback para manejar la respuesta de la llamada a la API.
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    when (response.code()) {
                        //Si el código de respuesta es 200, actualiza _loginResult con el token.
                        200 -> {
                            val result = response.body()
                            Log.d("result body", result.toString())
                            val token = result?.accessToken.toString()
                            Log.d("result token", token) //accessToken // token
                            //_loginResult.value = "${Constants.TOKEN_PROPERTY}: ${result?.token}" //accessToken

                            try {
                                Log.d("result try Bearer ${token}", token)
                                val respuesta = serviceLogin.getInfoMe("Bearer $token").enqueue(object : Callback<UserDetailsModel> {
                                    override fun onResponse(
                                        call: Call<UserDetailsModel>,
                                        respuesta: Response<UserDetailsModel>
                                    ) {
                                        if (respuesta.isSuccessful) {
                                            val datos = respuesta.body()
                                            // Procesa los datos obtenidos
                                            Log.d("result body", datos.toString())
                                            //UserDetailsModel(first_name=Soyo, last_name=Molina, email=Soyo_moli.na@hotmail.com, password=$2b$10$/1.yxiDaHDtVVIuayD/0euKgdOyc0FCnFPvgbfKfpVLYByJd8ClTW, points=120.0, roleId=1)
                                            Log.d("result body", datos?.first_name.toString() + datos?.points.toString())

                                            val monto = datos?.points?.toInt()
                                            _userBalance.value = monto.toString()
                                            Log.d("result monto", monto.toString())
                                            val msj = "Login successful"
                                            _loginResult.value = "${msj}|${_userBalance.value}|${datos?.first_name.toString()}"
                                        } else {
                                            // Maneja el error
                                            Log.d("result error", respuesta.errorBody().toString())
                                        }
                                    }

                                    override fun onFailure(
                                        p0: Call<UserDetailsModel>,
                                        p1: Throwable
                                    ) {
                                        Log.d("result error", p1.message.toString())
                                    }
                                })
                                /*Log.d("result respuesta", respuesta.toString())
                                if (respuesta.isSuccessful) {
                                    val datos = respuesta.body()
                                    // Procesa los datos obtenidos
                                    Log.d("result body", datos.toString())

                                    _userBalance.value = 140.toString()
                                    val msj = "Login successful"
                                    _loginResult.value = "${msj}|${_userBalance.value}"
                                } else {
                                    // Maneja el error
                                    Log.d("result error", respuesta.errorBody().toString())
                                }*/
                            } catch (e: Exception) {
                                // Maneja el error de red
                                Log.d("result error", e.message.toString())
                            }



                        }
                        400 -> {
                            _loginResult.value = "Error 400|0" // O un mensaje más específico
                        }
                        else -> {
                            _loginResult.value = "Error 500 Internal Server|0" // O un mensaje más específico
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("result err82", t.message.toString())
                    _loginResult.value = "Error de conexión|0" // O un mensaje más específico
                }
            })
        }
    }
}