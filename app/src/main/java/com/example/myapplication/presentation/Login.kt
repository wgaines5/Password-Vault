/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.myapplication.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R


import com.example.myapplication.R.layout
import com.google.firebase.firestore.FirebaseFirestore

class Login : ComponentActivity() {

    private val dataB = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.login)

       val pinEditText = findViewById<EditText>(R.id.pinEdit)
        val submitPinButton = findViewById<android.widget.Button>(R.id.submitPin)

        submitPinButton.setOnClickListener{
            val enteredPin = pinEditText.text.toString()

            val userId = "user5"
            dataB.collection("Users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                  if (document != null && document.getBoolean("pinRequired")== true){
                      val correctPin = document.getString("pin")

                      if (enteredPin == correctPin){
                          val intent = Intent(this, MainScreen::class.java)
                          startActivity(intent)
                          finish()
                      }else{
                          Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show()
                      }
                  }
                  else{
                      val intent = Intent(this, MainScreen::class.java)
                      startActivity(intent)
                      finish()
                  }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Pin Fetching Failed", Toast.LENGTH_SHORT).show()
                }


        }
    }

}