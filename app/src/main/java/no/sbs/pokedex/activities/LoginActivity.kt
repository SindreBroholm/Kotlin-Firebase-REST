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

class LoginActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val etLoginEmail = findViewById<EditText>(R.id.et_login_email)
        val etLoginPassword = findViewById<EditText>(R.id.et_login_password)

        startRegisterUserActivity()
        startForgotPasswordActivity()
        handelLoginAttempt(btnLogin, etLoginEmail, etLoginPassword)
    }

    private fun handelLoginAttempt(
        btnLogin: Button,
        etLoginEmail: EditText,
        etLoginPassword: EditText
    ) {
        btnLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(etLoginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = etLoginEmail.text.toString().trim { it <= ' ' }
                    val password: String = etLoginPassword.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
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

    private fun startForgotPasswordActivity() {
        val tvForgotPassword = findViewById<TextView>(R.id.tv_forgot_password)
        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
    private fun startRegisterUserActivity() {
        val tvRegister = findViewById<TextView>(R.id.tv_register)
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}