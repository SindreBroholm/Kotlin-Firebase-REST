package no.sbs.pokedex.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import no.sbs.pokedex.R
import no.sbs.pokedex.utils.PokemonGenAdapter

class ShowPokemonByGenerationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val pokemonGenAdapter = PokemonGenAdapter(supportFragmentManager)
        val tabLayout = findViewById<TabLayout>(R.id.tablayout)


        viewPager.adapter = pokemonGenAdapter
        tabLayout.setupWithViewPager(viewPager)

        bottomNavigation()
    }

    private fun bottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_pokedex -> {
                    // current view, do nothing!
                    true
                }
                R.id.nav_profile -> {
                    //todo
                    true
                }
                R.id.nav_world -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    Toast.makeText(
                        this,
                        "Something went wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
            }
        }
    }
}

