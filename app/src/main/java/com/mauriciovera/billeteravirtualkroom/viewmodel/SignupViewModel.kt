package com.mauriciovera.billeteravirtualkroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauriciovera.billeteravirtualkroom.model.UserDetailsModel
import com.mauriciovera.billeteravirtualkroom.model.network.ApiService
import com.mauriciovera.billeteravirtualkroom.model.network.RetrofitHelp
import com.mauriciovera.billeteravirtualkroom.model.response.SignupResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    private val _signupResult = MutableLiveData<String>()
    val signupResult: LiveData<String> = _signupResult

    fun signup(
        first_name: String,
        last_name: String,
        email: String,
        password: String,
        points: Double,
        roleId: Int
    ) {
        viewModelScope.launch {
            val serviceSignup = RetrofitHelp.getInstance().create(ApiService::class.java)
            Log.d("result Signupviewmodel", email + password)

            serviceSignup.postUsers(
                UserDetailsModel(
                    first_name,
                    last_name,
                    email,
                    password,
                    points,
                    roleId
                )
            ).enqueue(object :
                Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            _signupResult.value = "Signup successful"
                        }

                        201 -> {
                            _signupResult.value = "Signup successful"
                        }

                        400 -> {
                            _signupResult.value = "Error 400"
                        }

                        else -> {
                            Log.d("result Signupviewmodel", response.code().toString())
                            Log.d("result error", response.toString())

                            _signupResult.value = "Email registrado anteriormente"
                        }
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("result err82", t.message.toString())
                    _signupResult.value = "Error de conexión"
                }
            })
        }
    }
}