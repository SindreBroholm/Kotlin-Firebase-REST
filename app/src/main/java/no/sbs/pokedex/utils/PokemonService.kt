package no.sbs.pokedex.utils

import no.sbs.pokedex.data.PokemonList
import no.sbs.pokedex.data.PokemonDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon?limit=151")
    fun getGenI() : Call<PokemonList>

    @GET("pokemon?limit=100&offset=151")
    fun getGenII() : Call<PokemonList>

    @GET("pokemon?limit=135&offset=251")
    fun getGenIII() : Call<PokemonList>

    @GET("pokemon?limit=107&offset=386")
    fun getGenIV() : Call<PokemonList>

    @GET("pokemon?limit=156&offset=493")
    fun getGenV() : Call<PokemonList>

    @GET("pokemon?limit=72&offset=649")
    fun getGenVI() : Call<PokemonList>

    @GET("pokemon?limit=88&offset=721")
    fun getGenVII() : Call<PokemonList>

    @GET("pokemon?limit=89&offset=809")
    fun getGenVIII() : Call<PokemonList>

    @GET("pokemon/{id}/")
    fun getPokemonDetail(@Path("id") id: String) : Call<PokemonDetails>


}

