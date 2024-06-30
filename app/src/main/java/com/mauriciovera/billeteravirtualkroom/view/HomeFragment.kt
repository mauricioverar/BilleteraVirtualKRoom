package com.mauriciovera.billeteravirtualkroom.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.FragmentHomeBinding
import com.mauriciovera.billeteravirtualkroom.model.TransactionModel
import com.mauriciovera.billeteravirtualkroom.model.UserApplication.Companion.prefs
import com.mauriciovera.billeteravirtualkroom.model.local.entities.TransactionsEntity
import com.mauriciovera.billeteravirtualkroom.model.response.Transaction
import com.mauriciovera.billeteravirtualkroom.view.adapter.TransactionAdapter
import com.mauriciovera.billeteravirtualkroom.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var username: String? = null
    private var balance: String? = null
    private var id: Int? = null

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
            id = bundle.getInt("id")

            Log.d("selected ", username.toString()) //ok
            val name = username?.substringBefore("_")
            binding.tvUsername.text = binding.root.context.getString(R.string.hello, name)

            if (token != null) {
                Log.d("result home prefs token", token)//ok
                viewModelHome.transactions(id!!)//token//.toString()//

            }
        }
        viewModelHome.homeResult.observe(viewLifecycleOwner) {result ->
            result?.let {
                Log.d("result *** hh", "Tamaño de la lista: ${result.size}")

                Log.d("result *** hh", result.toString())//ok

                // Mapea la lista de Transaction<Any?> a una lista de TransactionModel
                val transactionsModelList = result.map { transaction ->
                    TransactionModel(
                        id = transaction.id,
                        amount = transaction.amount,
                        concept = transaction.concept,
                        date = transaction.date,
                        type = transaction.type?.toString() ?: "Desconocido"
                    )
                }

                // Crea el adaptador con los datos
                val transactionAdapter = TransactionAdapter(transactionsModelList)
                
                Log.d("result *** transactionAdapter", transactionAdapter.toString())//com.mauriciovera.billeteravirtualkroom.view.adapter.TransactionAdapter@f978ad7
                Log.d("result *** transactionAdapter", transactionAdapter.toString())

                // 3. Configura el adaptador en el RecyclerView
                binding.rvTransactions.adapter = transactionAdapter
                
                binding.rvTransactions.layoutManager = LinearLayoutManager(context)

                binding.profilePicture.setOnClickListener {
                    val bundle = Bundle().apply {
                        putString("username", username)
                    }
                    //findNavController().navigate(R.id.action_loginPageFragment_to_HomeFragment, bundle)
                    navController.navigate(R.id.action_HomeFragment_to_profilePageFragment, bundle)
                }

                binding.btnSendMoney.setOnClickListener {
                    navController.navigate(R.id.action_HomeFragment_to_sendMoneyFragment)
                }

                binding.btnRequestMoney.setOnClickListener {
                    navController.navigate(R.id.action_HomeFragment_to_requestMoneyFragment)
                }

                // Actualiza el adaptador
                transactionAdapter.update(transactionsModelList)
            }
        }
    }

    private fun obtenerTransactionsDesdeResultado(result: String): List<TransactionsEntity> {
        val transactionsList = mutableListOf<TransactionsEntity>()

         // 1. Obtén la lista de transacciones del resultado
        val transactions = parseTransactionResponse(result) ?: emptyList() // No necesitas acceder a .data

        // 2. Itera sobre la lista de Transaction y crea objetos TransactionsEntity
        for (transaction in transactions) {
            transactionsList.add(
                TransactionsEntity(
                    id = transaction.id,
                    amount = transaction.amount,
                    concept = transaction.concept,
                    date = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(
                        transaction.date.toString()
                    ) ?: Date(),
                    type = transaction.type.name//.type?.name ?: "Desconocido"
                )
            )
        }

        return transactionsList

    }

    /*private fun TransactionsEntity(id: Int, amount: Double, concept: String, date: String): TransactionsEntity {

    }*/

    // Función auxiliar para parsear la cadena result a un objeto TransactionResponse
    private fun parseTransactionResponse(result: String): List<Transaction<Any?>>? {
        // Implementa la lógica para deserializar la cadena a un objeto TransactionResponse
        // Ejemplo usando Gson:
        val gson = Gson()
        val listType = object : TypeToken<List<Transaction<Any?>>>() {}.type
        return gson.fromJson(result, listType)
        
        // ...
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}