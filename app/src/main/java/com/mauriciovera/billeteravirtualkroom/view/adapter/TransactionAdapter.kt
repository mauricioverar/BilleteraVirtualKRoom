package com.mauriciovera.billeteravirtualkroom.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.TransactionItemBinding
import com.mauriciovera.billeteravirtualkroom.model.TransactionModel
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionAdapter(private var transactionsList: List<TransactionModel>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    //private var transactionsList = listOf<TransactionsEntity>()

    fun update(transactions: List<TransactionModel>) {
        this.transactionsList = transactions
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: TransactionModel) {
            Log.d("TransactionAdapter", "bind: $transaction")

            // usuario1 etc nombre
            binding.tvUser.text = binding.tvUser.context.getString(
                R.string.usuario, transaction.id.toString())

            //binding.tvConcept.text = transaction.concept
            //binding.tvAmount.text = transaction.amount.toString()
            binding.tvAmount.text = binding.tvAmount.context.getString(
                R.string.valor_mas, transaction.amount.toString())

            // Formatea la fecha
            /*val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.tvDate.text = dateFormat.format(transaction.date)*/

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Formato de salida deseado

            val date = inputFormat.parse(transaction.date.toString()) // Parsea la fecha
            binding.tvDate.text = outputFormat.format(date) // Formatea la fecha en el formato deseado

            // type
            /*if (transaction.type == 1) {
                binding.tvType.text = "Ingreso"
            } else {
                binding.tvType.text = "Egreso"
            }*/
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionViewHolder {
        return TransactionViewHolder(
            TransactionItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        Log.d("result TransactionAdapter", "getItemCount: ${transactionsList.size}")
        return transactionsList.size//0
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionsList[position]
        holder.bind(transaction)
    }
}
