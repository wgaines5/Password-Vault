package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class UpdatePWD : ComponentActivity() {
    private val dataB = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.updatepw)

        auth = FirebaseAuth.getInstance()
        val curUser = auth.currentUser

        val pwdListContainer = findViewById<LinearLayout>(R.id.pwdLContainer)
        val cancelButton = findViewById<Button>(R.id.cancelB)

        if (curUser != null) {

            dataB.collection("Passwords")
                .whereEqualTo("userId", curUser.uid)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        addPasswordB(document, pwdListContainer)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "No Passwords Retrieved", Toast.LENGTH_SHORT).show()
                }
        }
        else{
            Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addPasswordB(document: QueryDocumentSnapshot, container: LinearLayout){
        val siteOrAppName = document.getString("siteOrAppName") ?: "Unknown"
        val password = document.getString("password") ?: ""
        val button = Button(this)
        button.text = siteOrAppName
        button.layoutParams = LinearLayout.LayoutParams(230, 130)

        button.setOnClickListener {
            val intent = Intent(this, UpwdForm::class.java).apply{
                putExtra("docId", document.id)
                putExtra("siteOrAppName", siteOrAppName)
                putExtra("password", password)
                putExtra("expirationDate", document.getDate("expirationDate"))
            }
            startActivity(intent)
        }
        container.addView(button)
    }
}