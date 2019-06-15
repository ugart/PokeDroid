package com.example.pokedroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedroid.R
import com.example.pokedroid.model.Abilities
import kotlinx.android.synthetic.main.pokemon_abilities_view_holder.view.*

class PokemonAbilitiesAdapter(private val context: Context) :
        RecyclerView.Adapter<PokemonAbilitiesAdapter.ViewHolder>() {

    var pokemonAbilities: List<Abilities> = listOf()

    fun refresh(pokemonAbilities: List<Abilities>) {
        this.pokemonAbilities = pokemonAbilities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_abilities_view_holder, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonAbilities.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val abilities = pokemonAbilities[p1]
        p0.ability.text = abilities.ability?.name.toString()
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ability = itemView.pokemonAbility
    }

}