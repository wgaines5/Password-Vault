package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Settings : ComponentActivity() {

    private val dataB = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configset)

        auth = FirebaseAuth.getInstance()

        val pToggle = findViewById<Switch>(R.id.pinSwitch)
        val saveSButton = findViewById<Button>(R.id.saveB2)
        val pinET = findViewById<EditText>(R.id.nPin)
        val cPinET = findViewById<EditText>(R.id.confPin)

        loadPinReqStatus{ pinRequired -> pToggle.isChecked = pinRequired }

        pToggle.setOnCheckedChangeListener{_, isChecked ->
            updatePinReq(isChecked)
        }

        saveSButton.setOnClickListener {
            val pin = pinET.text.toString()
            val cPin  = cPinET.text.toString()

            if (pin.isNotEmpty() && pin == cPin){
                updatePin(pin)
            }
            else{
                Toast.makeText(this, "Pin Mismatch", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun loadPinReqStatus(callback: (Boolean) -> Unit){
        val userId = auth.currentUser?.uid ?: return

        dataB.collection("Users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()){
                    val pinRequired = document.getBoolean("pinRequired") ?: true
                    callback(pinRequired)
                }
                else{
                    callback(true)
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Load Failed", Toast.LENGTH_SHORT).show()
                callback(true)
            }

    }

    private fun updatePinReq(isChecked: Boolean){
        val userId = auth.currentUser?.uid ?: return

        dataB.collection("Users").document(userId)
            .update("pinRequired", isChecked)
            .addOnSuccessListener {
                val message = if (isChecked){
                    "Pin Required"
                }
                else{
                    "Pin Not Required - Security Minimal"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Nothing Updated", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updatePin(pin: String){
        val userId = auth.currentUser?.uid ?: return
        val userPinData = hashMapOf("pin" to pin)

        dataB.collection("Users").document(userId)
            .update(userPinData as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(this, "Pin Updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Nothing Updated", Toast.LENGTH_SHORT).show()
            }
    }
}