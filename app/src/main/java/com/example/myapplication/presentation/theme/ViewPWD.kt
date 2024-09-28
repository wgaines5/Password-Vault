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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ViewPWD : ComponentActivity() {
    private val dataB = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpw)

        auth = FirebaseAuth.getInstance()

        val pwdLayout = findViewById<LinearLayout>(R.id.passwordListLayout)
        val curUser = auth.currentUser
        val userId = auth.currentUser?.uid ?: return

        if (curUser != null) {

            dataB.collection("Passwords")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { result ->

                    for (document: QueryDocumentSnapshot in result) {
                        val siteOrAppName = document.getString("siteOrAppName")
                        val password = document.getString("password")
                        val expDate = document.getDate("expirationDate")
                        val currentDate = Calendar.getInstance().time
                        val expired = expDate?.before(currentDate) ?: false
                        val btn = Button(this)
                        btn.text = if (expired) "$siteOrAppName (Expired)" else siteOrAppName
                        btn.layoutParams = LinearLayout.LayoutParams(230, 130)
                        btn.setOnClickListener {
                            val message = if (expired) {
                                "Password $siteOrAppName - Password Expired!"
                            } else {
                                "Password: $password"
                            }
                            AlertDialog.Builder(this)
                                .setTitle(siteOrAppName)
                                .setMessage(message)
                                .setPositiveButton("Tap to Return", null)
                                .create()
                                .show()
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
            eTextView.text ="Authentication Failed"
            pwdLayout.addView(eTextView)


        }

        val doneButton = findViewById<Button>(R.id.doneB2)
        doneButton.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}


