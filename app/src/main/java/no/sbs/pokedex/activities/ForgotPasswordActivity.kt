package no.sbs.pokedex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import no.sbs.pokedex.R

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnSendForgotPassword = findViewById<Button>(R.id.btn_send_forgot_password)
        val etForgotPasswordEmail = findViewById<TextView>(R.id.et_forgot_password_email)
        handelSendForgotPasswordToUser(btnSendForgotPassword, etForgotPasswordEmail)
    }

    private fun handelSendForgotPasswordToUser(
        btnSendForgotPassword: Button,
        etForgotPasswordEmail: TextView
    ) {
        btnSendForgotPassword.setOnClickListener {
            when {
                TextUtils.isEmpty(etForgotPasswordEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter email address",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email = etForgotPasswordEmail.text.toString()
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Please follow the steps send to your email!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                it.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}