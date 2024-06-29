package com.mauriciovera.billeteravirtualkroom.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.FragmentLoginPageBinding
import com.mauriciovera.billeteravirtualkroom.viewmodel.LoginViewModel

class LoginPageFragment : Fragment() {

    private lateinit var navController: NavController
    private var _binding: FragmentLoginPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.btnGoToNewSignup.setOnClickListener {
            navController.navigate(R.id.action_loginPageFragment_to_signupPageFragment)
        }

        val emailRegex = "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"
        //val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
        /*
    { signup ********************
    "first_name": "Soyo",Sam,Sue, Sizi, Sama,Sind
    "last_name": "Molina",
    "email": "Sam_moli.na@hotmail.com",Sizi_moli.na@hotmail.com, Sind_moli.na@hotmail.com
    "password": "beta33",
    "points": 120,
    "roleId": 1,

    auth/login **********************
    "email": "Soyo_moli.na@hotmail.com",
    "password": "beta33"

    users/id ********************
    "id": 3646,
    "first_name": "Soyo",
    "last_name": "Molina",
    "email": "Soyo_moli.na@hotmail.com",
    "password": "$2b$10$/1.yxiDaHDtVVIuayD/0euKgdOyc0FCnFPvgbfKfpVLYByJd8ClTW",
    "points": 120,
    "roleId": 1,
    "createdAt": "2024-06-20T23:31:01.000Z",
    "updatedAt": "2024-06-20T23:31:01.000Z"

    account/me **************** money
    "id": 2317,
    "money": 150,
    "isBlocked": false,
    "userId": 3646,
    "updatedAt": "2024-06-24T20:31:55.232Z",
    "createdAt": "2024-06-24T20:31:55.232Z"

    auth/me ************* id
    "id": 3646,
    "first_name": "Soyo",
    "last_name": "Molina",
    "email": "Soyo_moli.na@hotmail.com",
    "password": "$2b$10$/1.yxiDaHDtVVIuayD/0euKgdOyc0FCnFPvgbfKfpVLYByJd8ClTW",
    "points": 120,
    "roleId": 1,
    "createdAt": "2024-06-20T23:31:01.000Z",
    "updatedAt": "2024-06-20T23:31:01.000Z"

    transactions// ******************
    "id": 6533,
    "amount": 500,
    "concept": "Pago de honorarios",
    "date": "2022-10-26T15:00:00.000Z",
    "type": "topup|payment",
    "accountId": 1,
    "userId": 3645,
    "to_account_id": 5,
    "updatedAt": "2024-06-24T20:57:13.851Z",
    "createdAt": "2024-06-24T20:57:13.851Z"
    }
     */

        binding.btnGoToHome.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            Log.d("result email, password", email + password)

            if (binding.etEmail.text.toString().isEmpty()) {
                binding.etEmail.error = "Campo requerido"
                //toastEmailEmpty()
                return@setOnClickListener
            }
            if (binding.etPassword.text.toString().isEmpty()) {
                toastPasswordEmpty()
                return@setOnClickListener
            }
            if (!binding.etEmail.text.toString().matches(emailRegex.toRegex())) {
                toastEmailFalse()
                return@setOnClickListener
            }
            /*if (!binding.etPassword.text.toString().matches(passwordRegex.toRegex())) {
                toastPasswordFalse()
                return@setOnClickListener
            }*/

            viewModel.login(email, password)
        }
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->

            updateUI(result)
        }
    }

    private fun updateUI(result: String) {

        val partes = result.split("|")
        val stringOriginal = partes[0]
        val intOriginal = partes[1].toInt()
        val stringName = partes[2]
        val id = partes[3].toInt()


        Log.d(
            "result datos",
            stringOriginal + intOriginal.toString() + stringName + id
        )

        if (stringOriginal == "Login successful") {

            val bundle = Bundle().apply {
                putString("username", stringName)
                putString("balance", intOriginal.toString())
                putInt("id", id)
            }
            findNavController().navigate(R.id.action_loginPageFragment_to_HomeFragment, bundle)
        } else {
            //Toast.makeText(context, "Email o contraseña no registrado", Toast.LENGTH_LONG).show()
            Toast.makeText(context, stringOriginal, Toast.LENGTH_LONG).show()

        }
    }

    private fun toastPasswordEmpty() {
        Toast.makeText(context, "Contraseña requerida", Toast.LENGTH_SHORT).show()
    }

    private fun toastEmailFalse() {
        binding.etEmail.error = "Email incorrecto"
    }

    /*private fun toastPasswordFalse() {
        binding.etPassword.error =
            "Contraseña debe contener al menos 8 caracteres, una letra y un número"
    }*/
}