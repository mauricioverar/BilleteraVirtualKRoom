package com.mauriciovera.billeteravirtualkroom.view.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mauriciovera.billeteravirtualkroom.R
import com.mauriciovera.billeteravirtualkroom.databinding.ItemDatoBinding
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosEntity

class DatosListAdapter :
    RecyclerView.Adapter<DatosListAdapter.DatosViewHolder>() {

    private var datosList = listOf<DatosEntity>()
    private val datoSelected = MutableLiveData<DatosEntity>()
    fun update(list: List<DatosEntity>) {
        datosList = list
        notifyDataSetChanged()
    }

    fun selectDato(): LiveData<DatosEntity> = datoSelected // val *****

    inner class DatosViewHolder(private val binding: ItemDatoBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        @SuppressLint("SuspiciousIndentation")
        override fun onClick(v: View?) {
            datoSelected.value = datosList[adapterPosition]
            Log.d("DatosListAdapter", "onClick: ${datosList[adapterPosition]}")
        }

        fun bind(datos: DatosEntity) {

            Glide.with(binding.imageView)
                .load(datos.background_image)
                .centerCrop()
                .into(binding.imageView)
            Log.d("DatosListAdapter", "bind: ${datos.background_image}")

            binding.txname.text = datos.name
            binding.txfecha.text = datos.released
            if (datos.rating > 4) {
                binding.txdouble.text =
                    binding.root.context.getString(R.string.valor_mas, datos.rating.toString())
            } else {
                binding.txdouble.text =
                    binding.root.context.getString(R.string.valor_menos, datos.rating.toString())
            }
            itemView.setOnClickListener(this)
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DatosViewHolder {
        return DatosViewHolder(ItemDatoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DatosViewHolder, position: Int) {
        val datos = datosList[position]
        holder.bind(datos)
    }

    override fun getItemCount() = datosList.size
}