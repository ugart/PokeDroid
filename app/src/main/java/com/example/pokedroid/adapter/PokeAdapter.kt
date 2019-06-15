package com.example.pokedroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedroid.R
import com.example.pokedroid.model.Poke
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_view_holder.view.*

class PokeAdapter(private val context: Context) :
        RecyclerView.Adapter<PokeAdapter.ViewHolder>() {

    var pokes: List<Poke> = listOf()
    var onItemClick: ((Poke) -> Unit)? = null

    fun refresh(pokes: List<Poke>) {
        this.pokes = pokes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_view_holder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val poke = pokes[position]
        Picasso
            .with(context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${poke.getId()}.png")
            .into(holder.image)
        holder.name.text = poke.name
    }

    override fun getItemCount(): Int = pokes.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.imagePokemon
        val name = itemView.namePokemon

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(pokes[adapterPosition])
            }
        }
    }

}