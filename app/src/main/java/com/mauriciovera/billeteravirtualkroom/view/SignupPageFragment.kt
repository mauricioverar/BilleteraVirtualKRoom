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
//import androidx.navigation.fragment.findNavController
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

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.btnGoToLogin.setOnClickListener {
            navController.navigate(R.id.action_signupPageFragment_to_loginPageFragment)
            //navController.navigate(SignupPageFragmentDirections.actionSignupPageFragmentToLoginPageFragment())
        }

        // validar email y contraseña
        val emailRegex = "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"
        val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"

        /*
        {
    "email": "Susan_moli.na@hotmail.com",//Sofia
    "password": "alfa22"
}
        * */

        binding.btnGoToNewSignup.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etRePassword.text.toString().trim()

            Log.d("result email, password", email.toString() + password.toString())

            if (password != confirmPassword) {
                binding.etRePassword.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            }

            if (binding.etEmail.text.toString().isEmpty()) {
                //binding.etEmail.error = "Campo requerido"
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

            // enviar datos a otra pantalla ************************************************************************
            val bundle= Bundle().apply {
                putString("datoEmail",binding.etEmail.text.toString().trim()) //.toString() porq es Int // "datoId", 6
                putString("datoPassword",binding.etPassword.text.toString().trim())
            }

            // signup
            viewModel.signup(email, password)

            //navController.navigate(R.id.action_signupPageFragment_to_newSignupFragment)

            //navController.navigate(SignupPageFragmentDirections.actionSignupPageFragmentToLoginPageFragment())
            //   navController.navigate(SignupPageFragmentDirections.actionSignupPageFragmentToLoginPageFragment())

            /*val bundle= Bundle().apply {
                putString("datoId",it.id.toString()) //.toString() porq es Int
            }*/

            //navController.navigate(R.id.action_signupPageFragment_to_loginPageFragment, bundle)

            /*navController.navigate(SignupPageFragmentDirections
                .actionSignupPageFragmentToLoginPageFragment(email = binding.etEmail.text.toString().trim()))*/
        }
        //Observa loginResult
        viewModel.signupResult.observe(viewLifecycleOwner) { result ->
            updateUI(result)//Actualiza la UI con el resultado:
        }
    }

    private fun updateUI(result: String) {// ): String?
        /*binding.txtResult.visibility = View.VISIBLE // tomar elemento en xml y cambiar visibilidad
        binding.txtResult.text = result // mostrar //token: QpwL5tke4Pnpja7X4*/
        Log.d("result signup", result);
        if (result == "Signup successful") {
            navController.navigate(R.id.action_signupPageFragment_to_loginPageFragment)
        }
    }

    private fun toastEmailEmpty() {
        binding.etEmail.error = "Campo requerido"
    }

    private fun toastPasswordEmpty() {
        // usar snackebar
        Toast.makeText(context, "Contraseña requerida", Toast.LENGTH_SHORT).show()
    }

    private fun toastEmailFalse() {
        binding.etEmail.error = "Email incorrecto"
    }

    private fun toastPasswordFalse() {
        binding.etPassword.error = "Contraseña debe contener al menos 8 caracteres, una letra y un número"
        //Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
    }
}