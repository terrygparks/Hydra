package com.budget.hydra.ui.custom_login

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.budget.hydra.R


class RegisterScreen : AppCompatActivity() {

    // Global variables for the activities
    private lateinit var nameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var verifyPasswordField: EditText
    private lateinit var registerButton: Button
    private lateinit var progressBar: ProgressBar

    /**
     * Override the onCreate function in order to implement functionality for the activity.
     * Initialize the global variables that will be used throughout the application and are
     * not required to be deactivated in between activity switches
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Set up and present the layout for the activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        // Initializing our global variables
        nameField = findViewById(R.id.register_full_name)
        emailField = findViewById(R.id.register_email)
        passwordField = findViewById(R.id.register_password)
        verifyPasswordField = findViewById(R.id.verify_register_password)
        registerButton = findViewById(R.id.register_screen_button)
        progressBar = findViewById(R.id.progress_bar)

        // Button click listeners
        registerButton.setOnClickListener { registerUser() }
    }

    /**
     * Error checks all EditText fields for valid entries, and then registers the user
     * for the application
     */
    private fun registerUser() {

        // Remove all whitespace from the EditText fields
        val name = nameField.text.toString().trim()
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        val verifyPassword = verifyPasswordField.text.toString().trim()

        // check if the user entered a name
        if (name.isEmpty()) {
            nameField.error = getString(R.string.error_name_required)
            nameField.requestFocus()
            return
        }

        // check if the user entered an email
        if (email.isEmpty()) {
            emailField.error =  getString(R.string.error_email_required)
            emailField.requestFocus()
            return
        }

        // check if the user entered a valid email address
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.error =  getString(R.string.error_email_invalid)
            emailField.requestFocus()
            return
        }

        // check if the user entered a password
        if (password.isEmpty()) {
            passwordField.error =  getString(R.string.error_password_required)
            passwordField.requestFocus()
            return
        }

        // check if the password is greater than 6 characters
        if (password.length < 6) {
            passwordField.error =  getString(R.string.error_password_short)
            passwordField.requestFocus()
            return
        }

        // check if the user verified their password
        if (verifyPassword.isEmpty()) {
            verifyPasswordField.error =  getString(R.string.error_password_verify)
            verifyPasswordField.requestFocus()
            return
        }

        // check if the passwords match each other
        if (password != verifyPassword) {
            verifyPasswordField.error =  getString(R.string.error_password_match)
            verifyPasswordField.requestFocus()
            return
        }

        // Show the progress bar as the app connects to the database
        progressBar.visibility = View.VISIBLE


        // Hide the progress bar
        progressBar.visibility = View.GONE

        // Return to the previous activity with a successful result
        setResult(RESULT_OK)
        finish()


    }
}