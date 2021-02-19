package no.sbs.pokedex.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import no.sbs.pokedex.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")
        val tvUserID = findViewById<TextView>(R.id.tv_user_id)
        val tvEmailId = findViewById<TextView>(R.id.tv_email_id)
        val btnLogout = findViewById<Button>(R.id.btn_logout)

        bottomNav()
        logoutUser(btnLogout)

        tvUserID.text = "User Id :: $userId"
        tvEmailId.text = "Email Id :: $emailId"
    }

    private fun logoutUser(btnLogout: Button) {
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    private fun bottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    //current view
                    true
                }
                R.id.nav_pokedex -> {
                    val intent = Intent(this, ShowPokemonByGenerationActivity::class.java)
                    startActivity(intent)
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
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    )
                    true
                }
            }
        }
    }

}

