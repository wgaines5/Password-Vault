package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
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

        val pwdLayout = findViewById<LinearLayout>(R.id.passwordListLayout)

        dataB.collection("Passwords")
            .get()
            .addOnSuccessListener { result ->

                for (document: QueryDocumentSnapshot in result){
                    val siteOrAppName = document.getString("siteOrAppName")
                    val password = document.getString("password")
                    val btn = Button(this)
                    btn.text = siteOrAppName
                    btn.layoutParams = LinearLayout.LayoutParams(230,130)
                    btn.setOnClickListener{
                        AlertDialog.Builder(this)
                            .setTitle(siteOrAppName)
                            .setMessage("Password: $password")
                            .setPositiveButton("Secured", null)
                            .create()
                            .show()
                    }
                    pwdLayout.addView(btn)
                }

            }
            .addOnFailureListener{
                val ebtn = Button(this)
                ebtn.text = "Loading Failed"
                pwdLayout.addView(ebtn)
            }
        val doneButton = findViewById<Button>(R.id.doneB2)
        doneButton.setOnClickListener{
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}