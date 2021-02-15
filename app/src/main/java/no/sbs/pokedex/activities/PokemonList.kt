package no.sbs.pokedex.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewParent
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import no.sbs.pokedex.R
import no.sbs.pokedex.data.PokemonList
import no.sbs.pokedex.utils.PokemonGenAdapter
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

        /*val pokemonNameView = findViewById<ListView>(R.id.listview)*/
        val viewPager = findViewById<ViewPager>(R.id.viewPager)

        val pokemonGenAdapter = PokemonGenAdapter(supportFragmentManager)
        val tabLayout = findViewById<TabLayout>(R.id.tablayout)


        viewPager.adapter = pokemonGenAdapter
        tabLayout.setupWithViewPager(viewPager)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.nav_home -> {

                    true
                }
                R.id.nav_pokedex -> {
                    // current view, do nothing!
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
                    //some error message
                    true
                }
            }
        }



    }


    /*
    private fun getIGen(pokemonNameView: ListView) {
        var service = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokemonService::class.java)



        service.getGenI().enqueue(object : Callback<PokemonList> {
            override fun onResponse(
                    call: Call<PokemonList>,
                    response: Response<PokemonList>
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

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                Log.d("TAG_", "An error happen!")
                t.printStackTrace()
            }
        })
    }

    fun renderPokemonDetailView(pokemon: String, hashMap: HashMap<String?, String?>){
        val pokemonClickedUrl = hashMap[pokemon].toString()

        val intent = Intent(this@PokemonList, PokemonDetailActivity::class.java)
        intent.putExtra("ClickedPokemonUrl", pokemonClickedUrl)
        startActivity(intent)
    }
    fun getKey(pokemon: String): String{
        return pokemon
    }
*/
}

