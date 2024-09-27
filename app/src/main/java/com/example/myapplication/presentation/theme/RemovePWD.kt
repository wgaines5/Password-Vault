package com.example.myapplication.presentation.theme

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class RemovePWD : ComponentActivity() {
    private val dataB = FirebaseFirestore.getInstance()
    private val selectedPasswords = mutableListOf<String>()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.removepw)

        auth = FirebaseAuth.getInstance()
        val curUser = auth.currentUser

        val pwdLayout = findViewById<LinearLayout>(R.id.passwordListLayout)
        val deleteButton = findViewById<Button>(R.id.deleteB)

        if (curUser != null) {

            dataB.collection("Passwords")
                .whereEqualTo("userId", curUser.uid)
                .get()
                .addOnSuccessListener { result ->
                    for (document: QueryDocumentSnapshot in result) {
                        val siteOrAppName = document.getString("siteOrAppName")
                        val btn = Button(this)
                        btn.text = siteOrAppName
                        btn.layoutParams = LinearLayout.LayoutParams(230, 130)

                        btn.setOnClickListener {
                            if (selectedPasswords.contains(document.id)) {
                                selectedPasswords.remove(document.id)
                                btn.setBackgroundColor(resources.getColor(android.R.color.black))
                            } else {
                                selectedPasswords.add(document.id)
                                btn.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark))
                            }
                        }
                        pwdLayout.addView(btn)
                    }
                }
                .addOnFailureListener {
                    val ebtn = Button(this)
                    ebtn.text = "Loading Failed"
                    pwdLayout.addView(ebtn)
                }
        }
        else{
            val eTextView = TextView(this)
            eTextView.text = "Authentication Failed"
            pwdLayout.addView(eTextView)
        }
            deleteButton.setOnClickListener {
                if (selectedPasswords.isNotEmpty()) {
                    AlertDialog.Builder(this)
                        .setTitle("Confirm Deletion")
                        .setMessage("ARE YOU SURE ABOUT THIS??")
                        .setPositiveButton("YES") { _, _ -> deletePasswords() }
                        .setNegativeButton("NO", null)
                        .create()
                        .show()
                } else {
                    Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainScreen::class.java)
                    startActivity(intent)
                    finish()
                }
            }

    }
    private fun deletePasswords(){
        for (passwordId in selectedPasswords ){
            dataB.collection("Passwords")
                .document(passwordId)
                .delete()
                .addOnSuccessListener { Toast.makeText(this, "Password(s) GONE FOREVER", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(this, "Failed STILL THERE", Toast.LENGTH_SHORT).show()}
        }
        val intent = Intent(this, MainScreen::class.java)
        startActivity(intent)
        finish()
    }
}