package com.example.myapplication

import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.data.Repository
import com.example.myapplication.model.PokemonDetailResponse
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository = Repository()) : ViewModel() {
    private val _pokemonList = MutableLiveData<List<PokemonDetailResponse>>()
    val pokemonList: LiveData<List<PokemonDetailResponse>> = _pokemonList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _pokemonDetails = MutableLiveData<PokemonDetailResponse>()
    val pokemonDetails: LiveData<PokemonDetailResponse> = _pokemonDetails

    private var originalList: List<PokemonDetailResponse> = emptyList()
    private var currentFilter: String = "All Types"

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val list = repository.fetchPokemonList()
                originalList = list
                _pokemonList.value = list
            } catch (ex: Exception) {
                Log.e("PokeApiCall", "Error fetching Pokemon list", ex)
                _error.value = "Failed to fetch Pokemon: ${ex.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterByType(type: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                currentFilter = type
                if (type == "All Types") {
                    _pokemonList.value = originalList
                } else {
                    val filteredList = originalList.filter { pokemon ->
                        pokemon.types.any { it.type.name.equals(type, ignoreCase = true) }
                    }
                    _pokemonList.value = filteredList
                }
            } catch (ex: Exception) {
                Log.e("PokeApiCall", "Error filtering Pokemon", ex)
                _error.value = "Failed to filter Pokemon: ${ex.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetFilter() {
        currentFilter = "All Types"
        _pokemonList.value = originalList
        _isLoading.value = false
    }

    fun retryFetch() {
        fetchPokemonList()
    }

    fun loadPokemonDetails(pokemonId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val details = repository.getPokemonDetails(pokemonId)
                _pokemonDetails.value = details
                // Update the specific Pokemon in the list
                val currentList = _pokemonList.value?.toMutableList() ?: mutableListOf()
                val index = currentList.indexOfFirst { it.id == pokemonId }
                if (index != -1) {
                    currentList[index] = details
                    _pokemonList.value = currentList
                }
            } catch (ex: Exception) {
                Log.e("PokeApiCall", "Error loading Pokemon details", ex)
                _error.value = "Failed to load Pokemon details: ${ex.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sortById() {
        _pokemonList.value = _pokemonList.value?.sortedBy { it.id }
    }

    fun sortAlphabetically() {
        _pokemonList.value = _pokemonList.value?.sortedBy { it.name }
    }

    fun sortByType() {
        _pokemonList.value = _pokemonList.value?.sortedWith(compareBy<PokemonDetailResponse> {
            it.types.firstOrNull()?.type?.name ?: ""
        }.thenBy { it.id })
    }

    fun applyFilterAndSort(type: String, sortBy: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                var list =
                    if (type == "All Types") originalList else originalList.filter { pokemon ->
                        pokemon.types.any { it.type.name.equals(type, ignoreCase = true) }
                    }
                list = when (sortBy) {
                    "ID" -> list.sortedBy { it.id }
                    "Alphabetical" -> list.sortedBy { it.name }
                    "Type" -> list.sortedWith(compareBy<PokemonDetailResponse> {
                        it.types.firstOrNull()?.type?.name ?: ""
                    }.thenBy { it.id })

                    else -> list
                }
                _pokemonList.value = list
            } catch (ex: Exception) {
                Log.e("PokeApiCall", "Error applying filter and sort", ex)
                _error.value = "Failed to apply filter and sort: ${ex.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}