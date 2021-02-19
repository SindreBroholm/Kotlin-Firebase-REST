package no.sbs.pokedex.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import no.sbs.pokedex.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.btn_register)
        val etRegisterEmail = findViewById<EditText>(R.id.et_register_email)
        val etRegisterPassword = findViewById<EditText>(R.id.et_register_password)

        val tvLogin = findViewById<TextView>(R.id.tv_login)
        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnRegister.setOnClickListener {
            when {
                TextUtils.isEmpty(etRegisterEmail.text.toString().trim{ it <= ' '}) ->{
                    Toast.makeText(
                        this,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etRegisterPassword.text.toString().trim{ it <= ' '}) ->{
                    Toast.makeText(
                        this,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = etRegisterEmail.text.toString().trim{ it <= ' '}
                    val password: String = etRegisterPassword.text.toString().trim{ it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if (it.isSuccessful){
                            val firebaseUser: FirebaseUser = it.result!!.user!!
                            Toast.makeText(
                                this,
                                "You are registered !",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", firebaseUser.uid)
                            intent.putExtra("email_id", email)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                it.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}