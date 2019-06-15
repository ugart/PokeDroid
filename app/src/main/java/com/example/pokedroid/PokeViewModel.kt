package com.example.pokedroid

import android.arch.lifecycle.MutableLiveData
import com.example.pokedroid.business.PokeBusiness
import com.example.pokedroid.business.PokeBusiness.POKES_LIMIT
import com.example.pokedroid.model.Poke
import com.example.pokedroid.model.PokeDroid
import com.example.pokedroid.util.BaseViewModel
import com.example.pokedroid.util.SingleLiveEvent
import kotlin.math.ceil

class  PokeViewModel : BaseViewModel() {

    val onRequestPokeStarted = SingleLiveEvent<Void>()
    val onRequestPokeFinished = SingleLiveEvent<Void>()
    var pokeDroid = MutableLiveData<PokeDroid>()

    var pokePagination = MutableLiveData<Int>()

    init {
        pokes()
        pokePagination.value = 0
    }

    fun handleNextPage() {
        pokePagination.value?.let { page ->

            pokeDroid.value?.let { pokeDroid ->
                if((page.div(POKES_LIMIT) + 1) < ceil(pokeDroid.count.toDouble().div(POKES_LIMIT)))
                    pokePagination.value = page.plus(POKES_LIMIT)

                pokes()
            }

        }
    }

    fun handlePreviusPage() {
        pokePagination.value?.let { page ->

            if(page > 0)
                pokePagination.value = page.minus(POKES_LIMIT)

            pokes()
        }
    }

    fun pokes() {
        onRequestPokeStarted.call()

        PokeBusiness.listarPokes(pokePagination.value ?: 0,
            onSuccess = {
                pokeDroid.value = it
                onRequestPokeFinished.call()
            },
            onError = {
                onError.value = it.message
                onRequestPokeFinished.call()
            }
        )
    }
}