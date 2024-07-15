package com.example.myapplication.model

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<TypeResponse>
)

data class Sprites(
    val front_default: String
)

data class TypeResponse(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String,
    val url: String
)