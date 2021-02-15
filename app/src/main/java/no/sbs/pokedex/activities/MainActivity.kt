package no.sbs.pokedex.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import no.sbs.pokedex.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav()
    }

    private fun bottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    setContent("Home")
                    true
                }
                R.id.nav_pokedex -> {
                    val intent = Intent(this, PokemonList::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_profile -> {
                    setContent("Profile")
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

