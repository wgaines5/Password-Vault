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

class Login : ComponentActivity() {

    private val correctPin = "1234"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.login)

       val pinEditText = findViewById<EditText>(R.id.pinEdit)
        val submitPinButton = findViewById<android.widget.Button>(R.id.submitPin)
        submitPinButton.setOnClickListener{
            val enteredPin = pinEditText.text.toString()

            if (enteredPin == correctPin){
                val intent = Intent(this, MainScreen::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show()
            }
        }
    }

}