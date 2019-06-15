package com.example.pokedroid.model

open class Pokemon(
    var name: String = "",
    var abilities: List<Abilities> = listOf(),
    var base_experience: Int = 0,
    var height: Int = 0,
    var id: Int = 0,
    var weight: Int = 0
)