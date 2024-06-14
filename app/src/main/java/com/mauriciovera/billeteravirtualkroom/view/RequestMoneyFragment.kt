package com.mauriciovera.billeteravirtualkroom.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
//import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.FragmentRequestMoneyBinding

class RequestMoneyFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: FragmentRequestMoneyBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRequestMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.left.setOnClickListener {
            navController.popBackStack() // volver a la pantalla anterior
        }

        binding.btnTransRequestMoney.setOnClickListener {
            if (binding.etAmount.text.toString().isNotEmpty()){
                toast()
            } else {
                Toast.makeText(context, "Ingrese una cantidad", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toast() {
        Toast.makeText(context, "Transacción realizada ${binding.etAmount.text.toString().toDouble()}", Toast.LENGTH_SHORT).show()
    }
}