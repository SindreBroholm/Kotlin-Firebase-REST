package no.sbs.pokedex.utils

import no.sbs.pokedex.data.FirstGen
import no.sbs.pokedex.data.PokemonDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon?limit=150")
    fun getPokemons() : Call<FirstGen>

    @GET("pokemon/{id}/")
    fun getPokemonDetail(@Path("id") id: String) : Call<PokemonDetails>


}

