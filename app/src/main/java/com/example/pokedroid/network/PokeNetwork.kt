package com.example.pokedroid.network

import android.annotation.SuppressLint
import com.example.pokedroid.model.Poke
import com.example.pokedroid.model.PokeDroid
import com.example.pokedroid.model.Pokemon
import com.example.pokedroid.util.BaseNetwork
import com.example.pokedroid.util.NetworkError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
object PokeNetwork : BaseNetwork() {

    private val pokeAPI: PokeAPI by lazy {
        getRetrofitBuilder().build().create(PokeAPI::class.java)
    }

    fun requestPokemons(
        offset: Int,
        limit: Int,
        onSuccess: (pokeDroid: PokeDroid) -> Unit,
        onError: (error: NetworkError) -> Unit
    ) {
        pokeAPI.getPokemons(offset, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    onSuccess(response)
                },
                { error ->
                    onError(NetworkError(error))
                }
            )
    }

    fun requestPokemon(
        id: Int,
        onSuccess: (pokemon: Pokemon) -> Unit,
        onError: (error: NetworkError) -> Unit
    ) {
        pokeAPI.getPokemon(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    onSuccess(response)
                },
                { error ->
                    onError(NetworkError(error))
                }
            )
    }

}