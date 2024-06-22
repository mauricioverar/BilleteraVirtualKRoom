package com.mauriciovera.billeteravirtualkroom.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.FragmentLoginSignupPageBinding

class LoginSignupPageFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private var _binding: FragmentLoginSignupPageBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginSignupPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        _binding?.btnGoToLogin?.setOnClickListener {
            navController.navigate(R.id.action_loginSignupPageFragment_to_loginPageFragment)
        }

        _binding?.btnGoToSignup?.setOnClickListener {
            navController.navigate(R.id.action_loginSignupPageFragment_to_signupPageFragment)
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnGoToLogin -> navController.navigate(R.id.action_loginSignupPageFragment_to_loginPageFragment)
        }
    }
}