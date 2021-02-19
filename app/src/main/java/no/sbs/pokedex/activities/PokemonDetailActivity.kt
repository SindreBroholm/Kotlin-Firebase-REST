package no.sbs.pokedex.activities

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonElement
import com.koushikdutta.ion.Ion
import no.sbs.pokedex.R
import no.sbs.pokedex.data.PokemonDetails
import no.sbs.pokedex.utils.PokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.math.MathContext

class PokemonDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_details)

        val pokemonClickedUrl = intent.extras?.get("ClickedPokemonUrl")
        val clickedPokemonUrl = pokemonClickedUrl.toString()
        val getPokemonId = clickedPokemonUrl.split("/")

        val service = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)

        service.getPokemonDetail(getPokemonId[6]).enqueue(object : Callback<PokemonDetails> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<PokemonDetails>,
                response: Response<PokemonDetails>
            ) {
                // get all view input views
                val pokemonImgView = findViewById<ImageView>(R.id.im_pokemonImg)
                val pokemonNameTextView = findViewById<TextView>(R.id.tv_name)
                val pokemonHpTextView = findViewById<TextView>(R.id.tv_pokemon_hp)
                val pokemonPhysicsTextView = findViewById<TextView>(R.id.tv_pokemon_physics)
                val pokemonTypeTextView = findViewById<TextView>(R.id.tv_pokemon_types)
                val pokemonAttackTextView = findViewById<TextView>(R.id.tv_pokemon_attack)
                val pokemonDefenseTextView = findViewById<TextView>(R.id.tv_pokemon_defense)

                var pokemonType = ""
                pokemonType = getPokemonTypes(response)


                val pokemonImgUrl = response.body()?.sprites?.get("front_default")?.asString
                val getpokemonStats = response.body()?.stats
                val getPokemonHeight = response.body()?.height.toString() //m
                val getPokemonWeight = response.body()?.weight.toString() //kg

                // Api result handlers
                if (pokemonImgUrl != null) {
                    Ion.with(pokemonImgView)
                        .load(pokemonImgUrl)
                } else {
                    pokemonImgView.background =
                        Drawable.createFromPath(R.drawable.ic_pokedex.toString())
                    println("Hit an error")
                }
                val pokemonHeight = refactorPokemonHeightAndWeight(getPokemonHeight)
                val pokemonWeight = refactorPokemonHeightAndWeight(getPokemonWeight)


                // API Setters to view
                pokemonNameTextView.text = response.body()?.name
                pokemonHpTextView.text = getpokemonStats?.get(0)?.asJsonObject?.get("base_stat")?.asString + " HP"
                pokemonAttackTextView.text = getpokemonStats?.get(1)?.asJsonObject?.get("base_stat")?.asString + " Atk"
                pokemonDefenseTextView.text = getpokemonStats?.get(2)?.asJsonObject?.get("base_stat")?.asString + " Def"
                pokemonPhysicsTextView.text = "height $pokemonHeight m, Weight $pokemonWeight kg"
                pokemonTypeTextView.text = pokemonType
            }

            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                //todo(do something here)
            }
        })
    }

    private fun getPokemonTypes(response: Response<PokemonDetails>,): String {
        var pokemonType = ""
        val listPokemonResponse: ArrayList<JsonElement?> = ArrayList<JsonElement?>()
        val getPokemonTypes = response.body()?.types?.asJsonArray

        if (getPokemonTypes != null) {
            for (i in getPokemonTypes) {
                listPokemonResponse.add(i?.asJsonObject?.get("type")?.asJsonObject?.get("name"))
            }
        }

        for (i in listPokemonResponse) {
            if (listPokemonResponse.size > 1) {
                pokemonType += "| ${i?.asString?.toUpperCase()} | "
            } else {
                pokemonType = "| ${i?.asString?.toUpperCase()} |"
            }
        }

        return pokemonType
    }

    private fun refactorPokemonHeightAndWeight(ApiInput: String): Double {
        var weightOrHeight = 0.0

        if (ApiInput.length == 2) {
            weightOrHeight = "0.$ApiInput".toDouble() * 10
        } else if (ApiInput.length == 3){
            weightOrHeight = "0.$ApiInput".toDouble() * 100
        } else if (ApiInput.length == 4){
            weightOrHeight = "0.$ApiInput".toDouble() * 1000
        }
        else {
            weightOrHeight = "0.0$ApiInput".toDouble() * 10
        }
        return BigDecimal(weightOrHeight).round(MathContext.DECIMAL32).toDouble()
    }
}