package com.example.vodafonetask.ui.fragment.airlinesfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vodafonetask.databinding.AirlineItemBinding
import com.example.vodafonetask.models.Airline

class AirlinesAdapter(private val clickListener: AirlineClickListener) : ListAdapter<Airline, AirlinesAdapter.MyViewHolder>(
    AirlineDiffCallback()
) {

    class MyViewHolder(private val binding: AirlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(airline: Airline, clickListener: AirlineClickListener) {
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

    class AirlineDiffCallback : DiffUtil.ItemCallback<Airline>() {
        override fun areItemsTheSame(oldItem: Airline, newItem: Airline): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Airline, newItem: Airline): Boolean {
            return oldItem == newItem
        }
    }

    class AirlineClickListener(val clickListener: (airline: Airline) -> Unit) {
        fun onClick(airline: Airline) = clickListener(airline)
    }
}