package com.example.vodafonetask.ui.fragment.airlinesfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vodafonetask.databinding.AirlineItemBinding
import com.example.vodafonetask.models.AirLineEntity

class AirlinesAdapter(private val clickListener: AirlineClickListener) : ListAdapter<AirLineEntity, AirlinesAdapter.MyViewHolder>(
    AirlineDiffCallback()
) {

    class MyViewHolder(private val binding: AirlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(airline: AirLineEntity, clickListener: AirlineClickListener) {
            binding.tvTittle.text = airline.name
            binding.airlineCardLayout.setOnClickListener {
                clickListener.onClick(airline)
            }
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AirlineItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class AirlineDiffCallback : DiffUtil.ItemCallback<AirLineEntity>() {
        override fun areItemsTheSame(oldItem: AirLineEntity, newItem: AirLineEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AirLineEntity, newItem: AirLineEntity): Boolean {
            return oldItem == newItem
        }
    }

    class AirlineClickListener(val clickListener: (airline: AirLineEntity) -> Unit) {
        fun onClick(airline: AirLineEntity) = clickListener(airline)
    }
}