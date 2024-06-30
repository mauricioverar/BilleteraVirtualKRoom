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
                        val userInfo = serviceLogin.getInfoMe(token)
                        val nombre = userInfo.first_name
                        val id = userInfo.id

                        if (nombre != null) {
                            Log.d("result User Info", userInfo.toString())
                            Log.d("result nombre", nombre+" "+id.toString())
                            val msj = "Login successful"
                            _loginResult.value =
                                "${msj}|0|${nombre}|${id.toString()}"

                        } else {
                            val msj = "Sin información del usuario"

                            Log.d("result User Info", msj)
                            _loginResult.value =
                                "${msj}|0|0"
                        }
                    } catch (e: Exception) {
                        Log.d("result error userInfo", e.message.toString())
                        if (e is HttpException && e.code() == 401 || e is HttpException && e.code() == 403) {
                            _loginResult.value = "Acceso denegado|0|0"

                        } else {
                            _loginResult.value = "Error email o pass|0"
                        }
                    }

                } else {
                    Log.d("result body", "Respuesta nula")
                }
            } catch (e: Exception) {
                Log.d("result error result", e.message.toString())
                _loginResult.value = "Error email o pass|0|0|0"
            }
        }
    }
}