package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.PokemonDetailResponse

class Repository {
    private val _pokemonList = MutableLiveData<List<PokemonDetailResponse>>()
    val pokemonList: LiveData<List<PokemonDetailResponse>> = _pokemonList

    suspend fun fetchPokemonList() {
        val response = PokeApi.apiService.getPokemonList()
        val resultList = mutableListOf<PokemonDetailResponse>()
        for (pokemon in response.results) {
            val pokemonDetail = PokeApi.apiService.getPokemonDetails(pokemon.name)
            resultList.add(pokemonDetail)
            _pokemonList.postValue(resultList)
        }
    }
}