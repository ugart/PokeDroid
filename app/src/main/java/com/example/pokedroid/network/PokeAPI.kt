package com.example.pokedroid.network

import com.example.pokedroid.model.PokeDroid
import com.example.pokedroid.model.Pokemon
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPI {

    @GET("pokemon/")
    fun getPokemons(
            @Query("offset") offset: Int,
            @Query("limit") limit: Int
    ): Observable<PokeDroid>

    @GET("pokemon/{id}/")
    fun getPokemon(@Path("id") id: Int): Observable<Pokemon>
}