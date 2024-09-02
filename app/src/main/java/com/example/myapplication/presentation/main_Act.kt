/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.myapplication.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.myapplication.R
import com.example.myapplication.R.layout

class MainScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(layout.activity_main)

        val addPasswordButton = findViewById<Button>(R.id.addPasswordButton)
        val viewPasswordsButton = findViewById<Button>(R.id.viewPasswordsButton)
        val removePasswordButton = findViewById<Button>(R.id.removePasswordsButton)


        addPasswordButton.setOnClickListener{
            setContentView(layout.addpw)

            val doneButton = findViewById<Button>(R.id.doneB)
            doneButton.setOnClickListener{val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()}
        }

        viewPasswordsButton.setOnClickListener {
            setContentView(layout.viewpw)
        }

        removePasswordButton.setOnClickListener {
            setContentView(layout.removepw)
        }
    }
}