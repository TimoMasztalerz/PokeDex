package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.R
import com.example.myapplication.databinding.ListItemBinding
import com.example.myapplication.model.PokemonDetailResponse

class ItemAdapter(
    private val dataset: List<PokemonDetailResponse>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.pokemonName.text = item.name
        holder.binding.pokemonId.text = "#${item.id.toString().padStart(3, '0')}"
        val imageUrl = item.sprites.front_default
            holder.binding.pokemonImage.load(imageUrl)

        }
    }