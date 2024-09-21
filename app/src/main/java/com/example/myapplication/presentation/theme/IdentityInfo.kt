package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.firestore.FirebaseFirestore

class IdentityInfo : ComponentActivity(){
    private val dataB = FirebaseFirestore.getInstance()
    private lateinit var driversLicET: EditText
    private lateinit var socialSecET: EditText
    private lateinit var insuranceET: EditText
    private lateinit var vinNumET: EditText
    private lateinit var cryptoKey: EditText
    private lateinit var bankAcct: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pinfo)

        driversLicET = findViewById(R.id.driverLs)
        socialSecET = findViewById(R.id.socialSec)
        insuranceET = findViewById(R.id.insuranceP)
        vinNumET = findViewById(R.id.vinNum)
        cryptoKey = findViewById(R.id.cryptoKey)
        bankAcct = findViewById(R.id.bankAcct)

        loadFirebaseData()

        val saveButton = findViewById<Button>(R.id.saveB)
        saveButton.setOnClickListener { saveFirebaseData()

            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()

        }


    }

    private fun loadFirebaseData(){
        val userId = "user5"
        dataB.collection("IdentityInfo").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null){
                    driversLicET.setText(document.getString("driversLicense"))
                    socialSecET.setText(document.getString("socialSecurity"))
                    insuranceET.setText(document.getString("insurancePolicy"))
                    vinNumET.setText(document.getString("vinNumber"))
                    cryptoKey.setText(document.getString("cryptoKeys"))
                    bankAcct.setText(document.getString("bankAccount"))
                }
            }
            .addOnFailureListener{
                Toast.makeText(this,"Data Load Failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveFirebaseData(){
        val userId = "user5"
        val identityData = hashMapOf(
            "driversLicense" to driversLicET.text.toString(),
            "socialSecurity" to socialSecET.text.toString(),
            "insurancePolicy" to insuranceET.text.toString(),
            "vinNumber" to vinNumET.text.toString(),
            "cryptoKeys" to cryptoKey.text.toString(),
            "bankAccount" to bankAcct.text.toString()
        )

        dataB.collection("IdentityInfo").document(userId)
            .set(identityData)
            .addOnSuccessListener {
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {Toast.makeText (this, "Save Failed", Toast.LENGTH_SHORT).show()}
    }
}