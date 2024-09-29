package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.MainScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.io.File
import java.util.Calendar

class SearchPWD : ComponentActivity(){
    private val dataB = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        auth = FirebaseAuth.getInstance()

        val searchB = findViewById<Button>(R.id.SearchB)
        val searchSOANET = findViewById<EditText>(R.id.searchSOAN)
        val layResult = findViewById<LinearLayout>(R.id.searchResLay)
        val doneB = findViewById<Button>(R.id.doneB4)
        val userId = auth.currentUser?.uid ?: return

        searchB.setOnClickListener {
            val query = searchSOANET.text.toString().trim()
            if(query.isNotEmpty()){
                searchPWD(query, userId, layResult)
            }
            else{
                Toast.makeText(this, "Enter a Stored Site Or App", Toast.LENGTH_SHORT).show()
            }
        }

        doneB.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun searchPWD(query: String, userId: String, layResult: LinearLayout){
        layResult.removeAllViews()

        val lowerQuery = query.lowercase()

        dataB.collection("Passwords")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                var mFound = false

                for (document: QueryDocumentSnapshot in result) {
                    val siteOrAppName = document.getString("siteOrAppName")?.lowercase()
                    if (siteOrAppName != null && siteOrAppName.contains(lowerQuery)) {
                        mFound = true

                        val password = document.getString("password")
                        val expDate = document.getDate("expirationDate")
                        val curDate = Calendar.getInstance().time
                        val expired = expDate?.before(curDate) ?: false
                        val resultB = Button(this)
                        resultB.text = if (expired) "$siteOrAppName (Expired)" else siteOrAppName
                        resultB.layoutParams = LinearLayout.LayoutParams(230, 130)
                        resultB.background = getDrawable(R.drawable.gold_bar)
                        resultB.setTypeface(null, android.graphics.Typeface.BOLD)
                        resultB.setTextColor(Color.parseColor("#000000"))

                        resultB.setOnClickListener {
                            val message = if (expired) {
                                "Password for $siteOrAppName has expired!!"
                            } else {
                                "Password: $password"
                            }

                            AlertDialog.Builder(this)
                                .setTitle(siteOrAppName)
                                .setMessage(message)
                                .setPositiveButton("RETURN", null)
                                .setNeutralButton("EXPORT"){_, _ ->
                                    exportPWD(siteOrAppName, password ?: "No Password")
                                }
                                .create()
                                .show()
                        }

                        layResult.addView(resultB)
                    }
                }
                if (!mFound) {
                    val eRText = TextView(this)
                    eRText.text = "Nothing Found for '$query'"
                }
            }

            .addOnFailureListener {
                val eText = TextView(this)
                eText.text = "Loading Failed"
                layResult.addView(eText)
            }
    }

    private fun exportPWD(siteOrAppName: String, password: String){
        try {

            val fileName = "$siteOrAppName-password.txt"
            val output = getExternalFilesDir(null)
            val pwdF = File(output, fileName)

            pwdF.writeText("Password for $siteOrAppName: $password")
            Toast.makeText(this, "Export Completed to ${pwdF.absolutePath}", Toast.LENGTH_LONG).show()

        }
        catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this, "Export Failed", Toast.LENGTH_SHORT).show()
        }

    }
}