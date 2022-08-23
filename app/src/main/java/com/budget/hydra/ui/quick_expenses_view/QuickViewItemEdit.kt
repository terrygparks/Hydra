package com.budget.hydra.ui.quick_expenses_view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.budget.hydra.R

class QuickViewItemEdit : AppCompatActivity() {

    // Global variables representing the components on the screen
    private lateinit var itemNameEditText: EditText
    private lateinit var frequencyEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var nextDueEditText: EditText
    private lateinit var saveButton: Button

    /**
     * Override the onCreate function in order to implement functionality for the activity.
     * Initialize the global variables that will be used throughout the application and are
     * not required to be deactivated in between activity switches
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Set up and present the layout for the activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quick_expense_edit)

        // Initialize the global variables with their IDs
        itemNameEditText = findViewById(R.id.quick_budget_name_edit)
        frequencyEditText = findViewById(R.id.quick_frequency_edit)
        amountEditText = findViewById(R.id.quick_amount_edit)
        nextDueEditText = findViewById(R.id.quick_next_due_edit)
        saveButton = findViewById(R.id.quick_edit_save_button)

        itemNameEditText.setText(intent?.getStringExtra("itemName"))
        frequencyEditText.setText(intent?.getStringExtra("frequency"))
        amountEditText.setText(intent?.getStringExtra("amount"))
        nextDueEditText.setText(intent?.getStringExtra("nextDue"))


        // The click listener for the save button
        saveButton.setOnClickListener { saveButtonClicked() }
    }

    /**
     * Once the user taps the save button, we create an intent to be sent back to the QuickViewItem
     * activity with the given
     */
    private fun saveButtonClicked() {
        val intent = Intent()

        intent.putExtra("itemName", itemNameEditText.text.toString())
        intent.putExtra("frequency", frequencyEditText.text.toString())
        intent.putExtra("amount", amountEditText.text.toString())
        intent.putExtra("nextDue", nextDueEditText.text.toString())

        setResult(RESULT_OK, intent)
        finish()
    }

}