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
//import com.mauriciovera.billeteravirtualkroom.model.response.LoginResponse
import com.mauriciovera.billeteravirtualkroom.model.response.SignupResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    //resultado del signup (éxito o error).
    private val _signupResult = MutableLiveData<String>()
    //LiveData público que expone el resultado del signup a la vista
    val signupResult: LiveData<String> = _signupResult

    fun signup(firstName: String, lastName: String, email: String, password: String, points: Double) {
        //corrutina en el viewModelScope.
        viewModelScope.launch {
            //instancia de LoginService utilizando RetrofitClient.
            val serviceSignup = RetrofitHelp.getInstance().create(ApiService::class.java)
            //llamada a la función login de LoginService.
            Log.d("result Signupviewmodel", email.toString() + password.toString())//ok

            serviceSignup.postUsers(UserDetailsModel(firstName, lastName, email, password, points)).enqueue(object :
                Callback<SignupResponse> {
                //implementación de Callback para manejar la respuesta de la llamada a la API.
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    when (response.code()) {
                        //Si el código de respuesta es 200, actualiza _signupResult con el token.
                        200 -> {
                            //val result = response.body()
                            //Log.d("result body", result.toString())
                            //Log.d("result token", result?.accessToken.toString()) //accessToken // token
                            //_signupResult.value = "${Constants.TOKEN_PROPERTY}: ${result?.token}" //accessToken
                            _signupResult.value = "Signup successful"
                        }
                        201 -> {
                            //val result = response.body()
                            //Log.d("result body", result.toString())
                            //Log.d("result token", result?.accessToken.toString()) //accessToken // token
                            //_signupResult.value = "${Constants.TOKEN_PROPERTY}: ${result?.token}" //accessToken
                            _signupResult.value = "Signup successful"
                        }
                        400 -> {
                            _signupResult.value = "Error 400" // O un mensaje más específico
                        }
                        else -> {
                            Log.d("result Signupviewmodel", response.code().toString())
                            Log.d("result error", response.toString())//.errorBody().toString()
                            // run login para ver repetido **

                            _signupResult.value = "Email registrado anteriormente" // O un mensaje más específico
                        }
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("result err82", t.message.toString())
                    _signupResult.value = "Error de conexión" // O un mensaje más específico
                }
            })
        }
    }
}