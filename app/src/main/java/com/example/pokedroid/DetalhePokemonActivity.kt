package com.example.pokedroid

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.pokedroid.adapter.PokemonAbilitiesAdapter
import com.example.pokedroid.model.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhe_pokemon.*

class DetalhePokemonActivity : AppCompatActivity() {

    private lateinit var detalhePokemonViewModel: DetalhePokemonViewModel
    private var adapter = PokemonAbilitiesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_pokemon)

        val intent = intent
        val id = intent.getStringExtra("ID")

        detalhePokemonViewModel = ViewModelProviders.of(this).get(DetalhePokemonViewModel::class.java)
        detalhePokemonViewModel.getPokemon(id.toInt())

        setupView()
        subscribeUI()
    }

    private fun setupView() {
        recyclerViewPokemonAbilities.adapter = adapter

        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.title = "Detalhes do Pokémon"
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }

    }

    private fun bindUI(pokemon: Pokemon) {
        namePokemonDetalhe.text = pokemon.name
        pokemonIDDetalhe.text = getString(R.string.id_pokemon, pokemon.id.toString())
        pokemonAlturaDetalhe.text = getString(R.string.height_pokemon, pokemon.height.toString())
        pokemonPesoDetalhe.text = getString(R.string.weight_pokemon, pokemon.weight.toString())
        pokemonXPDetalhe.text = getString(R.string.xp_pokemon, pokemon.base_experience.toString())
        pokemonImage(pokemon)
    }

    private fun pokemonImage(pokemon: Pokemon) {
        Picasso
            .with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.id}.png")
            .into(pokemonImageDetalhe)
    }

    private fun subscribeUI() {
        with(detalhePokemonViewModel) {

            pokemon.observe(this@DetalhePokemonActivity, Observer { pokemon ->
                pokemon?.let {
                    bindUI(pokemon)
                    adapter.refresh(it.abilities)
                }
            })

            onRequestGetPokemonStarted.observe(this@DetalhePokemonActivity, Observer {
                progressBarDetalhePokemon.visibility = View.VISIBLE
            })

            onRequestGetPokemonFinished.observe(this@DetalhePokemonActivity, Observer {
                progressBarDetalhePokemon.visibility = View.GONE
            })

            onError.observe(this@DetalhePokemonActivity, Observer { message ->

                message?.let {
                    val snack = Snackbar.make(
                        constraintLayoutDetalhePokemonActivity, message.getMessage(this@DetalhePokemonActivity),
                        Snackbar.LENGTH_SHORT
                    )
                    snack.view.setBackgroundColor(
                        ContextCompat.getColor(
                            this@DetalhePokemonActivity,
                            R.color.colorAccent
                        )
                    )
                    snack.show()
                }
            })
        }
    }
}