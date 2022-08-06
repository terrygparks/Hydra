package com.budget.hydra.ui.custom_login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.budget.hydra.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TempLandingScreen : AppCompatActivity() {

    // Firebase global variables
    private lateinit var auth: FirebaseAuth

    private lateinit var welcomeText: TextView
    private lateinit var signoutButton: Button

    /**
     * Override the onCreate function in order to implement functionality for the activity.
     * Initialize the global variables that will be used throughout the application and are
     * not required to be deactivated in between activity switches
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Set up and present the layout for the activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp_landing_screen)

        welcomeText = findViewById(R.id.temp_signed_in_text)
        signoutButton = findViewById(R.id.temp_signout_button)

        // Initialize the Firebase Authentication variable
        auth = FirebaseAuth.getInstance()

        signoutButton.setOnClickListener { signoutUser() }
    }

    // If the user returns back to this screen
    override fun onResume() {

        // Check if there is a user signed in now. If not, back to login screen
        if (auth.currentUser == null) {
            val intent = Intent(this@TempLandingScreen, LoginScreen::class.java)
            startActivity(intent)
        } else {
            welcomeText.text = "You are logged in as: ${auth.currentUser!!.email}"
        }

        super.onResume()
    }

    private fun signoutUser() {
        auth.signOut()

        val intent = Intent(this@TempLandingScreen, LoginScreen::class.java)
        startActivity(intent)
    }


}