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
import com.example.myapplication.presentation.theme.RemovePWD
import com.example.myapplication.presentation.theme.UpdatePWD
import com.example.myapplication.presentation.theme.ViewPWD

class MainScreen : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(layout.activity_main)

        val addPasswordButton = findViewById<Button>(R.id.addPasswordButton)
        val viewPasswordsButton = findViewById<Button>(R.id.viewPasswordsButton)
        val removePasswordButton = findViewById<Button>(R.id.removePasswordsButton)
        val updatePasswordButton = findViewById<Button>(R.id.updatePasswordsButton)


        addPasswordButton.setOnClickListener{
            val intent = Intent(this, AddPWD::class.java)
            startActivity(intent)
        }

        viewPasswordsButton.setOnClickListener {
            val intent = Intent(this, ViewPWD::class.java)
            startActivity(intent)
        }

        removePasswordButton.setOnClickListener {
            val intent = Intent(this, RemovePWD::class.java)
            startActivity(intent)
        }

        updatePasswordButton.setOnClickListener{
            val intent = Intent(this, UpdatePWD::class.java)
            startActivity(intent)
        }

    }
}