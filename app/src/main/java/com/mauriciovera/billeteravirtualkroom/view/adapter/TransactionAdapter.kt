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

    fun update(transactions: List<TransactionModel>) {
        this.transactionsList = transactions
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: TransactionModel) {
            Log.d("TransactionAdapter", "bind: $transaction")

            binding.tvUser.text = binding.tvUser.context.getString(
                R.string.usuario, transaction.id.toString())
            binding.tvAmount.text = binding.tvAmount.context.getString(
                R.string.valor_mas, transaction.amount.toString())

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val date = inputFormat.parse(transaction.date.toString())
            binding.tvDate.text = outputFormat.format(date)

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
