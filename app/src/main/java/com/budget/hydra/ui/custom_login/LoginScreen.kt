package com.budget.hydra.ui.custom_login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.budget.hydra.R

class LoginScreen : AppCompatActivity() {

    // Global variables for the components on the screen
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    // Launcher for activities that give a result for registering the user
    private lateinit var registerUserRequest: ActivityResultLauncher<Intent>

    /**
     * Override the onCreate function in order to implement functionality for the activity.
     * Initialize the global variables that will be used throughout the application and are
     * not required to be deactivated in between activity switches
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Set up and present the layout for the activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Initialize the components on the screen
        emailField = findViewById(R.id.login_email)
        passwordField = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.register_button)

        // Initialize the RegisterUser activity result launcher
        registerUserRequest =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                // If the RegisterUser activity finished successfully
                if (it.resultCode == RESULT_OK) {
                    Toast.makeText(this, "Successfully logged in", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
                }
            }

        // Button click listeners
        loginButton.setOnClickListener { login() }
        registerButton.setOnClickListener { registerUser() }
    }

    /**
     * Error checks the user's login credentials, and if valid, signs the user into the
     * application. If a field is not valid, then directs focus to the appropriate field
     */
    private fun login() {

        // Remove whitespace from the EditText fields
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()

        // Check to see if anything is in the email field
        if (email.isEmpty()) {
            emailField.error = getString(R.string.error_email_required)
            emailField.requestFocus()
            return
        }

        // Check to see if the email is in a proper format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.error = getString(R.string.error_email_invalid)
            emailField.requestFocus()
            return
        }

        // Check to see if anything is in the password field
        if (password.isEmpty()) {
            passwordField.error = getString(R.string.error_password_required)
            passwordField.requestFocus()
            return
        }

        // Check to see if  the password is fewer than 6 characters
        if (password.length < 6) {
            passwordField.error = getString(R.string.error_password_short)
            passwordField.requestFocus()
            return
        }
    }

    /**
     * Launches the RegisterUser activity for the user to sign up to use the application
     */
    private fun registerUser() {
        // Create the explicit intent to go to the RegisterUser screen
        val intent = Intent(this@LoginScreen, RegisterScreen::class.java)

        // Launch the intent
        registerUserRequest.launch(intent)
    }

}



