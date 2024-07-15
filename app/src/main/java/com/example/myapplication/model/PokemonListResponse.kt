package com.example.myapplication.model

data class PokemonListResponse (
    val count: Int,
    val results: List<Pokemon>
)

data class Pokemon(
    val name: String,
    val url: String,
)