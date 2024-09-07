/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.myapplication.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.myapplication.R
import com.example.myapplication.R.layout
import com.example.myapplication.presentation.theme.AddPWD

class MainScreen : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(layout.activity_main)

        val addPasswordButton = findViewById<Button>(R.id.addPasswordButton)
        val viewPasswordsButton = findViewById<Button>(R.id.viewPasswordsButton)
        val removePasswordButton = findViewById<Button>(R.id.removePasswordsButton)



        addPasswordButton.setOnClickListener{
            val intent = Intent(this, AddPWD::class.java)
            startActivity(intent)
        }

        viewPasswordsButton.setOnClickListener {
            setContentView(layout.viewpw)
            val googleButton = findViewById<Button>(R.id.google)
            googleButton.setOnClickListener {
                setContentView(layout.google)
            }
        }

        removePasswordButton.setOnClickListener {
            setContentView(layout.removepw)
        }

    }
}