package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.StringBuilder
import java.security.SecureRandom
import java.util.Calendar
import java.util.Date


class AddPWD : ComponentActivity(){

    private val dataB = FirebaseFirestore.getInstance()
    private val pwdChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#&"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addpw)

        val siteOrAppNameET = findViewById<EditText>(R.id.siteOrAppName)
        val passwordEntryET = findViewById<EditText>(R.id.passwordEntry)
        val doneButton = findViewById<Button>(R.id.doneB)
        val passwordExp = findViewById<Spinner>(R.id.pwdexpiration)
        val generatePwdB = findViewById<Button>(R.id.generateB)

        val expOptions = arrayOf("Unlimited", "1 day", "7 days", "14 days", "30 days", "60 days", "90 days")
        passwordExp.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,expOptions)

        generatePwdB.setOnClickListener {
            val genPwd = generateRndPwd(12)
            passwordEntryET.setText(genPwd)
            Toast.makeText(this, "Generated Password: $genPwd", Toast.LENGTH_SHORT).show()
        }

        doneButton.setOnClickListener{
            val siteOrAppName = siteOrAppNameET.text.toString()
            val password = passwordEntryET.text.toString()
            val expOption = passwordExp.selectedItem.toString()

            if (siteOrAppName.isNotEmpty() && password.isNotEmpty()){
                val expDate = getExpirationDate(expOption)
                val userPasswordData = hashMapOf("siteOrAppName" to siteOrAppName,"password" to password, "expirationDate" to expDate)

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
    private fun getExpirationDate(option: String): Date {
        val calendar = Calendar.getInstance()
        when (option){
            "Unlimited" -> null
            "1 day" -> calendar.add(Calendar.DAY_OF_YEAR, 1)
            "7 days" -> calendar.add(Calendar.DAY_OF_YEAR, 7)
            "14 days" -> calendar.add(Calendar.DAY_OF_YEAR, 14)
            "30 days" -> calendar.add(Calendar.DAY_OF_YEAR, 30)
            "60 days" -> calendar.add(Calendar.DAY_OF_YEAR, 60)
            "90 days" -> calendar.add(Calendar.DAY_OF_YEAR, 90)
        }
        return calendar.time
    }
    private fun generateRndPwd(length: Int): String{
        val random = SecureRandom()
        val password = StringBuilder(length)
        for (i in 0 until length){
            val randomChar = pwdChars[random.nextInt(pwdChars.length)]
            password.append(randomChar)
        }
        return password.toString()
    }
}