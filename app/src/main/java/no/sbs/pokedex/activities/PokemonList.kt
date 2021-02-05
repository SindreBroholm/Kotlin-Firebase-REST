package no.sbs.pokedex.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import no.sbs.pokedex.R
import no.sbs.pokedex.data.FirstGen
import no.sbs.pokedex.main
import no.sbs.pokedex.utils.PokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        val mainActivity = findViewById<LinearLayout>(R.id.ll_main_activity)

        val pokemonNameView = findViewById<ListView>(R.id.listview)

        var service = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)

        service.getPokemons().enqueue(object: Callback<FirstGen> {
            override fun onResponse(
                call: Call<FirstGen>,
                response: Response<FirstGen>
            ) {
                val pokemon = arrayOfNulls<String>(response.body()?.results!!.size)
                val adapter = ArrayAdapter<String>(
                    applicationContext,
                    android.R.layout.simple_dropdown_item_1line,
                    pokemon
                )

                var listOfPokemons = HashMap<String?, String?>()
                for (i in response.body()?.results!!.indices) {
                    listOfPokemons[response.body()?.results?.get(i)?.name] = response.body()?.results?.get(i)?.url

                    pokemon[i] = response.body()?.results?.get(i)?.name
                }
                pokemonNameView.adapter = adapter
                pokemonNameView.setOnItemClickListener { parent, view, position, id ->
                    val pokemonName = getKey(parent.getItemAtPosition(position) as String)
                    renderPokemonDetailView(pokemonName, listOfPokemons)
                }
            }
            fun getKey(pokemon: String): String{
                return pokemon
            }


            fun renderPokemonDetailView(pokemon: String, hashMap: HashMap<String?, String?>){
                val pokemonClickedUrl = hashMap[pokemon].toString()

                val intent = Intent(this@PokemonList, PokemonDetailActivity::class.java)
                intent.putExtra("ClickedPokemonUrl", pokemonClickedUrl)
                startActivity(intent)
            }

            override fun onFailure(call: Call<FirstGen>, t: Throwable) {
                Log.d("TAG_", "An error happen!")
                t.printStackTrace()
            }
        })

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.nav_home -> {

                    true
                }
                R.id.nav_pokedex -> {
                    /*val intent = Intent(this, PokemonList::class.java)
                    startActivity(intent)*/
                    true
                }
                R.id.nav_profile -> {

                    true
                }
                R.id.nav_world -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    setContent("ERROR")
                    true
                }
            }
        }
    }
    private fun setContent(content: String) {
        title = content
        val tvLabel = findViewById<TextView>(R.id.tvLabel)
        tvLabel.text = content
    }
}