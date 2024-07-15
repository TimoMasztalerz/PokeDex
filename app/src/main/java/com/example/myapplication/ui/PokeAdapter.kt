package com.example.myapplication.ui

import com.example.myapplication.model.PokemonDetailResponse
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.R
import com.example.myapplication.databinding.ListItemBinding
class PokeAdapter : RecyclerView.Adapter<PokeAdapter.PokemonViewHolder>() {
    private var pokemonList: List<PokemonDetailResponse> = emptyList()

    fun submitList(newList: List<PokemonDetailResponse>) {
        pokemonList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount() = pokemonList.size

    inner class PokemonViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonDetailResponse) {
            binding.pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            binding.pokemonId.text = "ID: ${pokemon.id}"
            binding.pokemonTypes.text = pokemon.types.joinToString(", ") { it.type.name.capitalize() }
            binding.pokemonImage.load(pokemon.sprites.front_default) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            }
        }
    }
}
