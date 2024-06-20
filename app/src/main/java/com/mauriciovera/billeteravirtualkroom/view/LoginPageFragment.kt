package com.mauriciovera.billeteravirtualkroom.view

import android.content.Intent
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
    //var SM: SendMessage? = null

    //val args: LoginPageFragmentArgs by navArgs() //agregar en nav_graph
    private var datoEmail : String? = null // llega como string
    private var datoPassword : String? = null // llega como string

    private lateinit var navController: NavController
    private var _binding: FragmentLoginPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    //private var datoEmail : String? = null // llega como string

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        //recibiendo dato **********************************************
        /*val email = args.email
        Log.d("SELECCION datoEmail", email.toString()) */

        //reemplazar con datos de basedato con un token puede ser
        arguments?.let { bundle ->
            //datoEmail = bundle.getString("datoEmail") //********************
            datoEmail = "eve.holt@reqres.in"

            //datoPassword= bundle.getString("datoPassword") //********************
            datoPassword= "cityslicka"


            //Log.d("SELECCION datoEmail, datoPassword", datoEmail.toString() + datoPassword.toString()) //ok // ok
        }

        binding.btnGoToNewSignup.setOnClickListener {
            navController.navigate(R.id.action_loginPageFragment_to_signupPageFragment)
        }

        // validar email y contraseña
        val emailRegex = "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"
        val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"

        /*
    {
        "email": "eve.holt@reqres.in",
        "password": "cityslicka" // pistol
    }
    {
    "email": "Sara_moli.na@hotmail.com",
    "password": "alfa22"
}

    {
    "name": "leny",
    "job": "junior"
    }

    {
    "name": "leny",
    "job": "junior",
    "id": "988",
    "createdAt": "2024-06-18T19:39:28.626Z"
}
     */

        binding.btnGoToHome.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            Log.d("result email, password", email.toString() + password.toString())

            // validar

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

            /*val intent = Intent(context, HomeFragment::class.java)
            intent.putExtra("username", binding.etEmail.text.toString())*/

            //SM?.sendData(binding.etEmail.text.toString().trim())

            //validar con basedato
            /*if (binding.etEmail.text.toString() != datoEmail || binding.etPassword.text.toString() != datoPassword) {
                Toast.makeText(context, "Email o contraseña no existe en basedatos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }*/

            // login
            viewModel.login(email, password)


            //navController.navigate(R.id.action_loginPageFragment_to_HomeFragment)

            //navController.navigate(LoginPageFragmentDirections.actionLoginPageFragmentToHomeFragment(username = binding.etEmail.text.toString().trim()))
            //navController.navigate(LoginPageFragment.ActionLoginPageFragmentToHomeFragment(username = binding.etEmail.text.toString().trim()))
        }
        //Observa loginResult
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            updateUI(result)//Actualiza la UI con el resultado:
        }
    }

    private fun updateUI(result: String) {// ): String?
        /*binding.txtResult.visibility = View.VISIBLE // tomar elemento en xml y cambiar visibilidad
        binding.txtResult.text = result // mostrar //token: QpwL5tke4Pnpja7X4
        */
        Log.d("result", result);
        if (result == "Login successful") {
            //navController.navigate(R.id.action_loginPageFragment_to_HomeFragment(username = binding.etEmail.text.toString().trim()))

            val bundle = Bundle().apply {
                putString("username", binding.etEmail.text.toString().trim())
            }
            findNavController().navigate(R.id.action_loginPageFragment_to_HomeFragment, bundle)
        } else {
            Toast.makeText(context, "No está registrado", Toast.LENGTH_LONG).show()
        }
    }

    /*private fun toast() {
        Toast.makeText(this, "Ingresa nueva contraseña", Toast.LENGTH_SHORT).show()
    }*/

    private fun toastEmailEmpty() {
        binding.etEmail.error = "Campo requerido"
    }

    private fun toastPasswordEmpty() {
        Toast.makeText(context, "Contraseña requerida", Toast.LENGTH_SHORT).show()
    }

    private fun toastEmailFalse() {
        binding.etEmail.error = "Email incorrecto"
    }

    private fun toastPasswordFalse() {
        binding.etPassword.error = "Contraseña debe contener al menos 8 caracteres, una letra y un número"
        //Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
    }

    /*interface SendMessage {
        fun sendData(message: String?)
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        SM = context as SendMessage
    }*/
}