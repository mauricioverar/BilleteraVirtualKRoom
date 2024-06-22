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
    {
    "first_name": "Soyo",Sam
    "last_name": "Molina",
    "email": "Sam_moli.na@hotmail.com",
    "password": "beta33",
    "points": 120,
    "roleId": 1,
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


        Log.d(
            "result datos",
            stringOriginal + intOriginal.toString() + stringName
        )

        if (stringOriginal == "Login successful") {

            val bundle = Bundle().apply {
                putString("username", stringName)
                putString("balance", intOriginal.toString())
            }
            findNavController().navigate(R.id.action_loginPageFragment_to_HomeFragment, bundle)
        } else {
            Toast.makeText(context, "Email o contraseña no registrado", Toast.LENGTH_LONG).show()
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