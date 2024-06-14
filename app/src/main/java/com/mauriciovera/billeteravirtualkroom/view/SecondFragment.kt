package com.mauriciovera.billeteravirtualkroom.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
//import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
//import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.FragmentSecondBinding
import com.mauriciovera.billeteravirtualkroom.viewmodel.DatosViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    private val viewModel: DatosViewModel by activityViewModels()//viewModel
    private var datoId: String? = null // llega como string


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
        //recibiendo dato **********************************************
        arguments?.let { bundle ->
            datoId = bundle.getString("datoId")
            Log.d("selected id", datoId.toString()) //ok
        }

        // getDetail(id)
        datoId?.let { id ->
            viewModel.getDetail(Integer.parseInt(id))// obtener desde internet
            //viewModel.getDetail(id.toInt())
        }

        // getDetail()
        //, Observer { ***************************************************
        viewModel.getDetail().observe(viewLifecycleOwner) {
            Log.d("detail id", datoId.toString())
            //var id = it.id // DetailEntity
            //var name = it.artista

            //val url = it.entradas
            // cargamos datos desde la seleccion *****************

            Glide.with(binding.imageView)
                .load(it.background_image)
                .centerCrop()
                .into(binding.imageView)
            Log.d("imagennnnnnnnnnnnnnnnnnnn", it.background_image) //ok
            // xml Entity
            binding.txname.text = it.name
            //binding.txname.text = binding.root.context.getString(R.string.name, it.name)
            Log.d("title", it.name)
            // datos Entity
            binding.txreleased.text = it.released
            binding.txrating.text = it.rating.toString() // Double

            /*binding.btnBuy.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("datoUrl", url)
                }
                findNavController().navigate(R.id.action_SecondFragment_to_webFragment, bundle)
            }*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}