package com.budget.hydra.ui.quick_expenses_view

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.budget.hydra.R
import com.budget.hydra.ui.custom_login.RegisterScreen
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.model.GradientColor


/**
 * Allows the user to view details about a particular budget item. Activity will
 * show the user the frequency, cost, and the date of the next payment due, along with
 * various other tidbits of information
 */
class QuickViewItem : AppCompatActivity() {

    // Global variables for the components on the screen
    private lateinit var budgetTitle: TextView
    private lateinit var budgetPercentage: TextView
    private lateinit var budgetTotal: TextView
    private lateinit var lineChart: LineChart
    private lateinit var oneMonthButton: Button
    private lateinit var threeMonthButton: Button
    private lateinit var sixMonthButton: Button
    private lateinit var oneYearButton: Button
    private lateinit var allButton: Button
    private lateinit var frequencyValue: TextView
    private lateinit var currentCostValue: TextView
    private lateinit var nextDueValue: TextView

    // Launcher for the edit activity
    private lateinit var editQuickViewActivityLauncher: ActivityResultLauncher<Intent>

    /**
     * Override the onCreate function in order to implement functionality for the activity.
     * Initialize the global variables that will be used throughout the application and are
     * not required to be deactivated in between activity switches
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // Set up and present the layout for the activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quick_expenses_view)

        // Initialize all of the global variables
        budgetTitle = findViewById(R.id.budget_title)
        budgetPercentage = findViewById(R.id.budget_percent_value)
        budgetTotal = findViewById(R.id.budget_total_value)
        lineChart = findViewById(R.id.quick_expense_chart)
        oneMonthButton = findViewById(R.id.one_month_button)
        threeMonthButton = findViewById(R.id.three_month_button)
        sixMonthButton = findViewById(R.id.six_month_button)
        oneYearButton = findViewById(R.id.one_year_button)
        allButton = findViewById(R.id.all_button)
        frequencyValue = findViewById(R.id.frequency_value)
        currentCostValue = findViewById(R.id.current_cost_value)
        nextDueValue = findViewById(R.id.next_due_value)

        // Hardcoded data, will need to be dynamically added through an intent or through the DB
        budgetTitle.text = "Netflix"
        frequencyValue.text = "Monthly"
        currentCostValue.text = "$11"
        nextDueValue.text = "August 31, 2022"

        // Initialize the line chart (if we even want it)
        initializeLineChart()

        // Initialize the RegisterUser activity result launcher
        editQuickViewActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                // If the RegisterUser activity finished successfully
                if (it.resultCode == RESULT_OK) {

                    budgetTitle.text = it.data?.getStringExtra("itemName")
                    frequencyValue.text = it.data?.getStringExtra("frequency")
                    currentCostValue.text = it.data?.getStringExtra("amount")
                    nextDueValue.text = it.data?.getStringExtra("nextDue")

                    Toast.makeText(this, "Successfully edited", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * Creates the menu bar at the top of the screen that contains the edit button
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.edit_only_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    // If the user selects one of the options in the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.edit_only_menu_button -> {
                // Create the explicit intent to go to the RegisterUser screen
                val intent = Intent(this@QuickViewItem, QuickViewItemEdit::class.java)

                // Getting the values of text we'll be editing in the next activity
                intent.putExtra("itemName",  budgetTitle.text.toString())
                intent.putExtra("frequency",  frequencyValue.text.toString())
                intent.putExtra("amount",  currentCostValue.text.toString())
                intent.putExtra("nextDue", nextDueValue.text.toString())

                // Launch the intent
                editQuickViewActivityLauncher.launch(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Initialize all of the extra details with the LineChart component
     */
    private fun initializeLineChart() {

        // Hide the grid lines
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        // xAxis.setDrawAxisLine(false)

        //remove right y-axis
        lineChart.axisRight.isEnabled = false

        //remove legend
        lineChart.legend.isEnabled = false

        //remove description label
        lineChart.description.text = "Total Amount Spent"

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

        val dataEntries = ArrayList<Entry>()

        for (i in 1..10) {
            dataEntries.add(Entry(i.toFloat(), i.toFloat()))
        }

        val lineDataSet = LineDataSet(dataEntries, "")

        val data = LineData(lineDataSet)

        lineChart.data = data
        lineChart.invalidate()
    }
}