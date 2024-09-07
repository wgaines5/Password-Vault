package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot


class ViewPWD : ComponentActivity() {
    private val dataB = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpw)

        val pwdTV = findViewById<TextView>(R.id.pwdTView)

        dataB.collection("Passwords")
            .get()
            .addOnSuccessListener { result ->
                val builder = StringBuilder()

                for (document: QueryDocumentSnapshot in result){
                    val siteOrAppName = document.getString("siteOrAppName")
                    val password = document.getString("password")
                    builder.append("Site/App: $siteOrAppName\nPassword: $password\n\n")
                }
                pwdTV.text = builder.toString()
            }
            .addOnFailureListener{
                pwdTV.text = "Loading Failed"
            }
        val doneButton = findViewById<Button>(R.id.doneB2)
        doneButton.setOnClickListener{
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}