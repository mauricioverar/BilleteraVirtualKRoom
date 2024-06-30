package com.mauriciovera.billeteravirtualkroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauriciovera.billeteravirtualkroom.model.UserApplication.Companion.prefs
import com.mauriciovera.billeteravirtualkroom.model.UserModel
import com.mauriciovera.billeteravirtualkroom.model.network.ApiService
import com.mauriciovera.billeteravirtualkroom.model.network.RetrofitHelper
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<String>()

    val loginResult: LiveData<String> = _loginResult


    fun login(email: String, password: String) {
        viewModelScope.launch {
            val serviceLogin = RetrofitHelper.getInstance().create(ApiService::class.java)
            Log.d("result viewmodel", email + password)

            try {
                val result = serviceLogin.login(UserModel(email, password))
                //
                if (result != null) {
                    Log.d("result body", result.toString())
                    val token = "Bearer ${result.accessToken}"
                    Log.d("result token", token)

                    prefs.saveToken(token)

                    try {
                        val userInfo = serviceLogin.getInfoMe(token) // **** nombre id
                        val nombre = userInfo.first_name//.toString()
                        val id = userInfo.id

                        if (nombre != null) {
                            Log.d("result User Info", userInfo.toString())
                            //UserDetailsModel(first_name=Soyo, last_name=Molina, email=Soyo_moli.na@hotmail.com, password=$2b$10$/1.yxiDaHDtVVIuayD/0euKgdOyc0FCnFPvgbfKfpVLYByJd8ClTW, points=120.0, roleId=1)
                            // ... resto de tu lógica ...
                            Log.d("result nombre", nombre+" "+id.toString())
                            val msj = "Login successful"
                            _loginResult.value =
                                "${msj}|0|${nombre}|${id.toString()}"

                        } else {
                            // Maneja el caso en que la respuesta de getInfoMe es nula
                            val msj = "Sin información del usuario"

                            Log.d("result User Info", msj)
                            _loginResult.value =
                                "${msj}|0|0"
                        }
                    } catch (e: Exception) {
                        Log.d("result error userInfo", e.message.toString())
                        if (e is HttpException && e.code() == 401 || e is HttpException && e.code() == 403) {
                            // Por ejemplo, redirige al usuario a la pantalla de inicio de sesión
                            //_navigationEvent.value = NavigationEvent.NavigateToLogin
                            _loginResult.value = "Acceso denegado|0|0"

                        } else {
                            // Maneja otros errores
                            //_errorMessage.value = "Error de red: ${e.message}"
                            _loginResult.value = "Error email o pass|0"
                        }
                    }


                } else {
                    // Maneja el caso en que la respuesta es nula (puede indicar un error)
                    Log.d("result body", "Respuesta nula")
                    // ... resto de tu lógica ...
                }
            } catch (e: Exception) {
                Log.d("result error result", e.message.toString())//HTTP 401 Unauthorized
                _loginResult.value = "Error email o pass|0|0|0"
            }
        }
    }
}