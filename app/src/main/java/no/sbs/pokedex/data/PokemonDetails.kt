package no.sbs.pokedex.data

import com.google.gson.JsonArray
import com.google.gson.JsonObject

class PokemonDetails(
    val height: Int,
    val id: Int,
    val name: String,
    val weight: Int,
    val sprites: JsonObject,
    val stats: JsonArray,
    val types: JsonArray
) {

}