package com.example.myapplication.presentation.theme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.Login
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.auth.FirebaseAuth

class SignUp : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intsignup)

        auth = FirebaseAuth.getInstance()

        val emailET = findViewById<EditText>(R.id.email)
        val pwdET = findViewById<EditText>(R.id.intPWD)
        val signB = findViewById<Button>(R.id.signB)

        signB.setOnClickListener {
            val email = emailET.text.toString()
            val password = pwdET.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {task ->
                        if (task.isSuccessful){
                            val intent = Intent(this, PinSetup::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Signup Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this, "Enter Email and Password",Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}