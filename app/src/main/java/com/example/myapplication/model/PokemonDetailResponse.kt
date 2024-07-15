
package com.example.myapplication.model

data class PokemonDetailResponse(
    val sprites: Sprites,
    val name: String,
    val id: Int
)

data class Sprites(
    val front_default: String? // Change to nullable and use the correct field name
)