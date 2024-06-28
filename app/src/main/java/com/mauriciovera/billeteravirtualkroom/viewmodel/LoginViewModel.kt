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
import com.mauriciovera.billeteravirtualkroom.model.network.RetrofitHelper
import com.mauriciovera.billeteravirtualkroom.model.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.awaitResponse

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<String>()
    private val _userBalance = MutableLiveData<String>()

    val loginResult: LiveData<String> = _loginResult


    fun login(email: String, password: String) {
        viewModelScope.launch {
            val serviceLogin = RetrofitHelper.getInstance().create(ApiService::class.java)
            Log.d("result viewmodel", email + password)

            try {
                val result = serviceLogin.login(UserModel(email, password))//.awaitResponse() // Usamos awaitResponse()
                //
                if (result != null) { // Verifica si la respuesta no es nula
                    Log.d("result body", result.toString())
                    val token = result.accessToken.toString()
                    Log.d("result token", token)

                    prefs.saveToken(token)//"Bearer $token"

                    try {
                        val userInfo = serviceLogin.getInfoMe("Bearer $token")
                        val nombre = userInfo?.first_name//.toString()

                        if (nombre != null) {
                            Log.d("result User Info", userInfo.toString())
                            //UserDetailsModel(first_name=Soyo, last_name=Molina, email=Soyo_moli.na@hotmail.com, password=$2b$10$/1.yxiDaHDtVVIuayD/0euKgdOyc0FCnFPvgbfKfpVLYByJd8ClTW, points=120.0, roleId=1)
                            // ... resto de tu lógica ...
                            Log.d("result nombre", nombre.toString())
                            val msj = "Login successful"
                            _loginResult.value =
                                "${msj}|0|${nombre.toString()}"

                        } else {
                            // Maneja el caso en que la respuesta de getInfoMe es nula
                            val msj = "Sin información del usuario"

                            Log.d("result User Info", msj)
                            _loginResult.value =
                                "${msj}|0|0"
                        }
                    } catch (e: Exception) {
                        Log.d("result error userInfo", e.message.toString())
                        if (e is HttpException && e.code() == 401) {
                            // Maneja el error 401 Unauthorized
                            // Por ejemplo, redirige al usuario a la pantalla de inicio de sesión
                            //_navigationEvent.value = NavigationEvent.NavigateToLogin
                            _loginResult.value = "Error 401 Unauthorized"

                        } else {
                            // Maneja otros errores
                            //_errorMessage.value = "Error de red: ${e.message}"
                            _loginResult.value = "Error 500 Internal Server|0"
                        }
                    }


                } else {
                    // Maneja el caso en que la respuesta es nula (puede indicar un error)
                    Log.d("result body", "Respuesta nula")
                    // ... resto de tu lógica ...
                }





                /*try {
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
            }*/
            } catch (e: Exception) {
                Log.d("result error result", e.message.toString())//HTTP 401 Unauthorized
                _loginResult.value = "Error 500 Internal Server|0|0|0"
            }

            /*serviceLogin.login(UserModel(email, password))
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
                })*/
        }
    }
}