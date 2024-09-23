/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.myapplication.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R


import com.example.myapplication.R.layout
import com.example.myapplication.presentation.theme.SignUp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login : ComponentActivity() {

    private val dataB = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.login)

        auth = FirebaseAuth.getInstance()

       val pinEditText = findViewById<EditText>(R.id.pinEdit)
        val submitPinButton = findViewById<android.widget.Button>(R.id.submitPin)
        val newUserB = findViewById<Button>(R.id.newUser)

        submitPinButton.setOnClickListener{
            val enteredPin = pinEditText.text.toString()
            val userId = auth.currentUser?.uid

             if (userId != null) {
                 dataB.collection("Users").document(userId)
                     .get()
                     .addOnSuccessListener { document ->
                         val storedPin = document.getString("pin")
                         if (storedPin != null && storedPin == enteredPin) {

                             val intent = Intent(this, MainScreen::class.java)
                             startActivity(intent)
                             finish()
                         }
                         else{
                             Toast.makeText(this, "Pin Incorrect", Toast.LENGTH_SHORT).show()
                         }
                     }
                     .addOnFailureListener {
                         Toast.makeText(this, "Pin Fetching Failed", Toast.LENGTH_SHORT).show()
                     }

             }
        }

        newUserB.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }
    }

}