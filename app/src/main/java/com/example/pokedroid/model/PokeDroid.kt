package com.example.pokedroid.model

open class PokeDroid(

    var count: Int = 0,
    var next: String = "",
    var previous: String? = null,
    var results: List<Poke> = listOf()
)