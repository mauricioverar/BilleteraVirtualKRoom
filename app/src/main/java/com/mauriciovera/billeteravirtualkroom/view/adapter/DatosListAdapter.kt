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
    RecyclerView.Adapter<DatosListAdapter.DatosViewHolder>() {// cambiar a .DatosViewHolder
    // cambiar a .DatosViewHolder
    // implement members, crear class DatosViewHolder agregar inner
    // y el constructor() con private val binding : ItemDatoBinding sin s): Recycler etc , View.OnClickListener

    // lista datosList + datoSelected + fun update + fun selectDato *****
    private var datosList = listOf<DatosEntity>()
    private val datoSelected = MutableLiveData<DatosEntity>()
    fun update(list: List<DatosEntity>) {
        datosList = list
        notifyDataSetChanged()
    }
    fun selectDato(): LiveData<DatosEntity> = datoSelected // val *****

    inner class DatosViewHolder(private val binding: ItemDatoBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        //binding.root), View.OnClickListener
        // e implement members
        // crear vars arriba datoList y datoselected

        @SuppressLint("SuspiciousIndentation")
        override fun onClick(v: View?) {
            datoSelected.value = datosList[adapterPosition]
            Log.d("DatosListAdapter", "onClick: ${datosList[adapterPosition]}")
        }

        fun bind(datos: DatosEntity) { // tabla con todos los datos
            //item xml y Entity

            // imagen ***********
            Glide.with(binding.imageView)
                .load(datos.background_image)
                .centerCrop() // *** para mostrar bien imagen ajustada al tamaño del item
                .into(binding.imageView)
            Log.d("DatosListAdapter", "bind: ${datos.background_image}") // image ok
            /*Picasso.get()
                .load(datos.imagen)
                .fit() // *** para mostrar bien imagen
                .into(binding.imageView)*/

            // texto ***********
            binding.txname.text = datos.name
            binding.txfecha.text = datos.released
            //val num = 20.00
            if (datos.rating > 4) {
                binding.txdouble.text = binding.root.context.getString(R.string.valor_mas, datos.rating.toString())
            } else {
                binding.txdouble.text = binding.root.context.getString(R.string.valor_menos, datos.rating.toString())
            }
            //binding.txdouble.text = binding.root.context.getString(R.string.valor_mas, datos.rating.toString())

                    //binding.txdouble.text = "-$15.00"
            //binding.txdouble.text = datos.rating.toString()

                    /*binding.txtype.text = datos.tipo
                    binding.txdescription.text = datos.descripcion*/


            // click ***
            itemView.setOnClickListener(this) //escuchador de click
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DatosViewHolder { // *** :DatosListAdapter.DatosViewHolder
        return DatosViewHolder(ItemDatoBinding.inflate(LayoutInflater.from(parent.context)))//, parent, false))
    }

    override fun onBindViewHolder(holder: DatosViewHolder, position: Int) {//holder: DatosListAdapter.DatosViewHolder
        val datos = datosList[position]
        holder.bind(datos)
    }

    override fun getItemCount() = datosList.size
}