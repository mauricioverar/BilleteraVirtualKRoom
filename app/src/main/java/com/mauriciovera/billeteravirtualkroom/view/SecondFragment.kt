package com.mauriciovera.billeteravirtualkroom.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.mauriciovera.billeteravirtualkroom.databinding.FragmentSecondBinding
import com.mauriciovera.billeteravirtualkroom.viewmodel.DatosViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    private val viewModel: DatosViewModel by activityViewModels()
    private var datoId: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            datoId = bundle.getString("datoId")
            Log.d("selected id", datoId.toString()) //ok
        }

        datoId?.let { id ->
            viewModel.getDetail(Integer.parseInt(id))
        }

        viewModel.getDetail().observe(viewLifecycleOwner) {
            Log.d("detail id", datoId.toString())

            Glide.with(binding.imageView)
                .load(it.background_image)
                .centerCrop()
                .into(binding.imageView)
            Log.d("imagennnnnnnnnnnnnnnnnnnn", it.background_image)
            // xml Entity
            binding.txname.text = it.name
            Log.d("title", it.name)
            binding.txreleased.text = it.released
            binding.txrating.text = it.rating.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}