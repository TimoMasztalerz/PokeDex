package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.PokemonDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    private val _pokemonList = MutableLiveData<List<PokemonDetailResponse>>()
    val pokemonList: LiveData<List<PokemonDetailResponse>> = _pokemonList

    private var allPokemonList: List<PokemonDetailResponse> = listOf()

    suspend fun fetchPokemonList(): List<PokemonDetailResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = PokeApi.apiService.getPokemonList()
                allPokemonList = response.results.map { pokemon ->
                    PokeApi.apiService.getPokemonDetails(pokemon.name)
                }
                _pokemonList.postValue(allPokemonList)
                allPokemonList
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    suspend fun filterByType(type: String): List<PokemonDetailResponse> {
        return withContext(Dispatchers.Default) {
            val filteredList = allPokemonList.filter { pokemon ->
                pokemon.types.any { it.type.name.equals(type, ignoreCase = true) }
            }
            _pokemonList.postValue(filteredList)
            filteredList
        }
    }

    suspend fun resetFilter(): List<PokemonDetailResponse> {
        _pokemonList.postValue(allPokemonList)
        return allPokemonList
    }

    suspend fun getPokemonDetails(pokemonId: Int): PokemonDetailResponse {
        return withContext(Dispatchers.IO) {
            try {
                PokeApi.apiService.getPokemonDetails(pokemonId.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }
}