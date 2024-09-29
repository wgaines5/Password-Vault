package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.StringBuilder
import java.security.SecureRandom
import java.util.Calendar
import java.util.Date



class UpwdForm : ComponentActivity(){

    private val dataB = FirebaseFirestore.getInstance()
    private val pwdChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#&"
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.updateform)

        auth = FirebaseAuth.getInstance()
        val curUser = auth.currentUser

        val docId = intent.getStringExtra("docId")
        val siteOrAppName = intent.getStringExtra("siteOrAppName")
        val password = intent.getStringExtra("password")

        val siteOrAppNameET = findViewById<EditText>(R.id.siteOrAppName2)
        val passwordEntryET = findViewById<EditText>(R.id.passwordEntry2)
        val passwordExp = findViewById<Spinner>(R.id.pwdexpiration2)
        val updateB = findViewById<Button>(R.id.doneB3)
        val generatePwdB = findViewById<Button>(R.id.generateB2)

        val expOptions = arrayOf("Unlimited", "1 day", "7 days", "14 days", "30 days", "60 days", "90 days")
        passwordExp.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,expOptions)

        generatePwdB.setOnClickListener {
            val genPwd = generateRndPwd2(12)
            passwordEntryET.setText(genPwd)
            Toast.makeText(this, "Generated Password: $genPwd", Toast.LENGTH_SHORT).show()
        }

        siteOrAppNameET.setText(siteOrAppName)
        passwordEntryET.setText(password)

        updateB.text = "Update"

        updateB.setOnClickListener {
            val updatedSiteOrApp = siteOrAppNameET.text.toString()
            val updatedPWD = passwordEntryET.text.toString()
            val expOption = passwordExp.selectedItem.toString()
            val updatedExpDate = getExpDate(expOption)

            if (curUser != null && updatedSiteOrApp.isNotEmpty() && updatedPWD.isNotEmpty()){
                val updatedData = hashMapOf(
                    "siteOrAppName" to updatedSiteOrApp,
                    "password" to updatedPWD,
                    "expirationDate" to updatedExpDate,
                    "userId" to curUser.uid
                )

                if (docId != null){
                    dataB.collection("Passwords").document(docId)
                        .set(updatedData)
                        .addOnSuccessListener {
                            Toast.makeText(this,"Password Has Been Updated!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainScreen::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener{
                            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
                        }

                }
            }
        }
    }
    private fun getExpDate(option: String): Date?{
        val calendar = Calendar.getInstance()
        when (option){
            "Unlimited" -> calendar.add(Calendar.DAY_OF_YEAR,99999)
            "1 day" -> calendar.add(Calendar.DAY_OF_YEAR, 1)
            "7 days" -> calendar.add(Calendar.DAY_OF_YEAR, 7)
            "14 days" -> calendar.add(Calendar.DAY_OF_YEAR, 14)
            "30 days" -> calendar.add(Calendar.DAY_OF_YEAR, 30)
            "60 days" -> calendar.add(Calendar.DAY_OF_YEAR, 60)
            "90 days" -> calendar.add(Calendar.DAY_OF_YEAR, 90)
        }
        return calendar.time
    }
    private fun generateRndPwd2(length: Int): String{
        val random = SecureRandom()
        val password = StringBuilder(length)
        for (i in 0 until length){
            val randomChar = pwdChars[random.nextInt(pwdChars.length)]
            password.append(randomChar)
        }
        return password.toString()
    }
}