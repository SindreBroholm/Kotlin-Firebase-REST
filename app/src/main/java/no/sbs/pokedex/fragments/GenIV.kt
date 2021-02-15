package no.sbs.pokedex.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import no.sbs.pokedex.R
import no.sbs.pokedex.activities.PokemonDetailActivity
import no.sbs.pokedex.data.PokemonList
import no.sbs.pokedex.utils.PokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GenIV.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenIV : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_gen_i_v, container, false)

        val pokemonNameView = v?.findViewById<ListView>(R.id.lv_gen4)

        var service = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)



        service.getGenIV().enqueue(object : Callback<PokemonList> {
            override fun onResponse(
                call: Call<PokemonList>,
                response: Response<PokemonList>
            ) {
                val pokemon = arrayOfNulls<String>(response.body()?.results!!.size)
                val adapter = activity?.let {
                    ArrayAdapter<String>(
                        it,
                        android.R.layout.simple_dropdown_item_1line,
                        pokemon
                    )
                }

                var listOfPokemons = HashMap<String?, String?>()
                for (i in response.body()?.results!!.indices) {
                    listOfPokemons[response.body()?.results?.get(i)?.name] = response.body()?.results?.get(i)?.url

                    pokemon[i] = response.body()?.results?.get(i)?.name
                }
                if (pokemonNameView != null) {
                    pokemonNameView.adapter = adapter
                }
                pokemonNameView?.setOnItemClickListener { parent, view, position, id ->
                    val pokemonName = getKey(parent.getItemAtPosition(position) as String)
                    renderPokemonDetailView(pokemonName, listOfPokemons)
                }
            }

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                Log.d("TAG_", "An error happen!")
                t.printStackTrace()
            }
            fun renderPokemonDetailView(pokemon: String, hashMap: HashMap<String?, String?>){
                val pokemonClickedUrl = hashMap[pokemon].toString()

                val intent =  Intent(activity, PokemonDetailActivity::class.java)
                intent.putExtra("ClickedPokemonUrl", pokemonClickedUrl)
                startActivity(intent)
            }
            fun getKey(pokemon: String): String{
                return pokemon
            }
        })

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GenIV.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GenIV().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}