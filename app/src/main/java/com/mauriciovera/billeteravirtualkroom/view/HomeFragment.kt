package com.mauriciovera.billeteravirtualkroom.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.FragmentHomeBinding
import com.mauriciovera.billeteravirtualkroom.model.UserApplication.Companion.prefs
import com.mauriciovera.billeteravirtualkroom.view.adapter.DatosListAdapter
import com.mauriciovera.billeteravirtualkroom.viewmodel.DatosViewModel
import com.mauriciovera.billeteravirtualkroom.viewmodel.HomeViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var username: String? = null
    private var balance: String? = null


    private var _binding: FragmentHomeBinding? = null

    //private val viewModel: DatosViewModel by activityViewModels()
    private val viewModelHome: HomeViewModel by viewModels()

    private lateinit var navController: NavController

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val token = prefs.getToken()


        arguments?.let { bundle ->
            username = bundle.getString("username")
            balance = bundle.getString("balance")

            //token = bundle.getString("token")//.toString()


            Log.d("selected ", username.toString()) //ok
            val name = username?.substringBefore("_")
            binding.tvUsername.text = binding.root.context.getString(R.string.hello, name)
            binding.tvBalance.text = binding.root.context.getString(R.string.balance, balance)

            if (token != null) {
                Log.d("result home prefs token", token)//ok
                viewModelHome.transactions()//token//.toString()//

            }
        }
        viewModelHome.homeResult.observe(viewLifecycleOwner) {
            it?.let {
                Log.d("result *** home result", it.toString())
            }
        }

        val adapter = DatosListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.profilePicture.setOnClickListener {
            navController.navigate(R.id.action_HomeFragment_to_profilePageFragment)
        }

        binding.btnSendMoney.setOnClickListener {
            navController.navigate(R.id.action_HomeFragment_to_sendMoneyFragment)
        }

        binding.btnRequestMoney.setOnClickListener {
            navController.navigate(R.id.action_HomeFragment_to_requestMoneyFragment)
        }

        //,Observer
        /*viewModel.getDatos().observe(viewLifecycleOwner) {

            it?.let {

                Log.d("Listado", it.toString())
                // fun de Adapter
                adapter.update(it)
            }

        }

        adapter.selectDato().observe(viewLifecycleOwner) {
            it.let {
                Log.d("SELECCION", it.toString())
            }
        }*/

        /*val token = prefs.getToken()
        if (token != null) {
            Log.d("result home prefs token", token)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}