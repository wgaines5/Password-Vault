package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.firestore.FirebaseFirestore

class Settings : ComponentActivity(){

    private val dataB = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configset)

        val newPinET = findViewById<EditText>(R.id.newPin)
        val savePinB = findViewById<Button>(R.id.saveB2)
        val pinSwitch = findViewById<Switch>(R.id.pinSwitch)

        val userId = "user5"
        dataB.collection("Users").document(userId)
            .get()
            .addOnSuccessListener{document ->
                if (document != null){
                    pinSwitch.isChecked = document.getBoolean("pinRequired") == true
                }
            }
        savePinB.setOnClickListener{
            val newPin = newPinET.text.toString()
            if (newPin.isNotEmpty()){
                val pinRequired = pinSwitch.isChecked

                val userUpdates = hashMapOf(
                    "pin" to newPin,
                    "pinRequired" to pinRequired
                )

                dataB.collection("Users").document(userId)
                    .update(userUpdates as Map<String, Any>)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Pin Updated", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainScreen::class.java)
                        startActivity(intent)
                        finish()

                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
                    }
            }
            else{
                val intent = Intent(this, MainScreen::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}