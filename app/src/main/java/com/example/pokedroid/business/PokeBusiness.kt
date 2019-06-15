package com.example.pokedroid.business

import com.example.pokedroid.model.PokeDroid
import com.example.pokedroid.model.Pokemon
import com.example.pokedroid.network.PokeNetwork
import com.example.pokedroid.util.NetworkError

object PokeBusiness {

    const val POKES_LIMIT = 20

    fun listarPokes(
            offset: Int,
            onSuccess: (pokeDroid: PokeDroid) -> Unit,
            onError: (error: NetworkError) -> Unit
    ) {
        PokeNetwork.requestPokemons(offset, POKES_LIMIT,
                {
                    onSuccess(it)
                },
                { error ->
                    onError(error)
                }
        )

    }

    fun getPokemon(
        id: Int,
        onSuccess: (pokemon: Pokemon) -> Unit,
        onError: (error: NetworkError) -> Unit
    ) {
        PokeNetwork.requestPokemon(id,
            {
                onSuccess(it)
            },
            { error ->
                onError(error)
            }
        )

    }

}