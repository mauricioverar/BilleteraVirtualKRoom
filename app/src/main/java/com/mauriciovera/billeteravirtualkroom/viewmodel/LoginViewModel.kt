package com.mauriciovera.billeteravirtualkroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    //LiveData público que expone el resultado del login a la vista
    val loginResult: LiveData<String> = _loginResult

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
                            Log.d("result token", result?.accessToken.toString()) //accessToken // token
                            //_loginResult.value = "${Constants.TOKEN_PROPERTY}: ${result?.token}" //accessToken
                            _loginResult.value = "Login successful"
                        }
                        400 -> {
                            _loginResult.value = "Error 400" // O un mensaje más específico
                        }
                        else -> {
                            _loginResult.value = "Error 500 Internal Server" // O un mensaje más específico
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("result err82", t.message.toString())
                    _loginResult.value = "Error de conexión" // O un mensaje más específico
                }
            })
        }
    }
}