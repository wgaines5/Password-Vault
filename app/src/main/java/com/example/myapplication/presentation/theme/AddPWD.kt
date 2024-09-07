package com.example.myapplication.presentation.theme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.firestore.FirebaseFirestore


class AddPWD : ComponentActivity(){

    private val dataB = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addpw)

        val siteOrAppNameET = findViewById<EditText>(R.id.siteOrAppName)
        val passwordEntryET = findViewById<EditText>(R.id.passwordEntry)
        val doneButton = findViewById<Button>(R.id.doneB)

        doneButton.setOnClickListener{
            val siteOrAppName = siteOrAppNameET.text.toString()
            val password = passwordEntryET.text.toString()

            if (siteOrAppName.isNotEmpty() && password.isNotEmpty()){
                val userPasswordData = hashMapOf("siteOrAppName" to siteOrAppName,"password" to password)

                dataB.collection("Passwords")
                    .add(userPasswordData)
                    .addOnSuccessListener { Toast.makeText(this, "Password Saved Successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainScreen::class.java)
                    startActivity(intent)
                    finish()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Password Not Saved", Toast.LENGTH_SHORT).show()
                    }
            }
            else{
                intent = Intent(this, MainScreen::class.java)
                startActivity(intent)
                finish()
            }

        }
    }
}