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
import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.FragmentSignupPageBinding
import com.mauriciovera.billeteravirtualkroom.viewmodel.SignupViewModel

class SignupPageFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: FragmentSignupPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.btnGoToLogin.setOnClickListener {
            navController.navigate(R.id.action_signupPageFragment_to_loginPageFragment)
        }

        val emailRegex = "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"
        //val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"

        binding.btnGoToNewSignup.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etRePassword.text.toString().trim()
            val points = 120.00
            val roleId = 1

            Log.d("result email, password", email + password)

            if (password != confirmPassword) {
                binding.etRePassword.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            }

            if (binding.etEmail.text.toString().isEmpty()) {
                toastEmailEmpty()
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

            viewModel.signup(firstName, lastName, email, password, points, roleId)
        }
        viewModel.signupResult.observe(viewLifecycleOwner) { result ->
            updateUI(result)
        }
    }

    private fun updateUI(result: String) {
        Log.d("result signup", result)
        if (result == "Signup successful") {
            navController.navigate(R.id.action_signupPageFragment_to_loginPageFragment)
        } else {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
        }
    }

    private fun toastEmailEmpty() {
        binding.etEmail.error = "Campo requerido"
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