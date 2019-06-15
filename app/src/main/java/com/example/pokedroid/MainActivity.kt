package com.example.pokedroid

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import com.example.pokedroid.adapter.PokeAdapter
import com.example.pokedroid.business.PokeBusiness.POKES_LIMIT
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var pokeViewModel: PokeViewModel
    private var adapter = PokeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokeViewModel = ViewModelProviders.of(this).get(PokeViewModel::class.java)

        setupView()
        subscribeUIPoke()
    }

    private fun setupView() {
        recyclerView.adapter = adapter

        adapter.onItemClick = { poke ->
            val idPokemon = poke.getId()
            val intent = Intent(this, DetalhePokemonActivity::class.java)
            intent.putExtra("ID", idPokemon)
            startActivity(intent)
        }

        previous.setOnClickListener {
            pokeViewModel.handlePreviusPage()
        }

        next.setOnClickListener {
            pokeViewModel.handleNextPage()
        }
    }

    private fun subscribeUIPoke() {
        with(pokeViewModel) {

            pokeDroid.observe(this@MainActivity, Observer { pokeDroid ->

                pokeDroid?.let {
                    pageNumber.text = "${(pokePagination.value?.div(POKES_LIMIT) ?: 0) + 1}/${ceil(it.count?.toDouble().div(POKES_LIMIT)).toInt()}"
                    adapter.refresh(it.results)
                }

            })

            onRequestPokeStarted.observe(this@MainActivity, Observer {
                progressBarMainActivity.visibility = VISIBLE
            })

            onRequestPokeFinished.observe(this@MainActivity, Observer {
                progressBarMainActivity.visibility = GONE
            })

            onError.observe(this@MainActivity, Observer { message ->

                message?.let {
                    val snack = Snackbar.make(
                        constraintLayoutMainActivity, message.getMessage(this@MainActivity),
                        Snackbar.LENGTH_SHORT
                    )
                    snack.view.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
                    snack.show()
                }
            })
        }
    }
}
