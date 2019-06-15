package com.example.pokedroid

import android.arch.lifecycle.MutableLiveData
import com.example.pokedroid.business.PokeBusiness
import com.example.pokedroid.model.Ability
import com.example.pokedroid.model.Pokemon
import com.example.pokedroid.util.BaseViewModel
import com.example.pokedroid.util.SingleLiveEvent

class DetalhePokemonViewModel : BaseViewModel() {

    val onRequestGetPokemonStarted = SingleLiveEvent<Void>()
    val onRequestGetPokemonFinished = SingleLiveEvent<Void>()
    val pokemon = MutableLiveData<Pokemon>()

    fun getPokemon(id: Int) {
        onRequestGetPokemonStarted.call()

        PokeBusiness.getPokemon(id,
            onSuccess = {
                pokemon.value = it
                onRequestGetPokemonFinished.call()
            },
            onError = {
                onError.value = it.message
                onRequestGetPokemonFinished.call()
            }
        )
    }
}