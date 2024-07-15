package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Repository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = Repository()
    val pokemonList = repository.pokemonList

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                repository.fetchPokemonList()
            } catch (ex: Exception) {
                Log.e("PokeApiCall", ex.message.toString())
            }
        }
    }
}