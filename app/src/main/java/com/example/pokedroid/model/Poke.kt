package com.example.pokedroid.model

open class Poke {

    var name: String = ""
    var url: String = ""

    fun getId(): String {
        return url.split("pokemon/")[1].split("/")[0]
    }

}
