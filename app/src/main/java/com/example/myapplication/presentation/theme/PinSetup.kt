package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PinSetup : ComponentActivity(){

    private val dataB = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinsetup)

        auth = FirebaseAuth.getInstance()

        val pinET = findViewById<EditText>(R.id.pin)
        val confPinET = findViewById<EditText>(R.id.cPin)
        val sPinB = findViewById<Button>(R.id.pinB)

        sPinB.setOnClickListener {
            val pin = pinET.text.toString()
            val confPin = confPinET.text.toString()

            if (pin.isNotEmpty() && pin == confPin){
                val userId = auth.currentUser?.uid?: return@setOnClickListener

                val usrPinD = hashMapOf(
                    "pin" to pin,
                    "pinRequired" to true)

                dataB.collection("Users").document(userId)
                    .set(usrPinD)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Pin Approved", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainScreen::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Pin Denied",Toast.LENGTH_SHORT).show()
                    }
            }
            else{
                Toast.makeText(this, "Pin Mismatch", Toast.LENGTH_SHORT).show()
            }
        }
    }
}