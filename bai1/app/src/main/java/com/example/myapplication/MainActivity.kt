package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var etNumber: EditText
    private lateinit var rgOptions: RadioGroup
    private lateinit var rbEven: RadioButton
    private lateinit var rbOdd: RadioButton
    private lateinit var rbPerfectSquare: RadioButton
    private lateinit var btnShow: Button
    private lateinit var lvNumbers: ListView
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to activity_main.xml
        setContentView(R.layout.activity_main)

        // Initialize UI components
        etNumber = findViewById(R.id.etNumber)
        rgOptions = findViewById(R.id.rgOptions)
        rbEven = findViewById(R.id.rbEven)
        rbOdd = findViewById(R.id.rbOdd)
        rbPerfectSquare = findViewById(R.id.rbPerfectSquare)
        btnShow = findViewById(R.id.btnShow)
        lvNumbers = findViewById(R.id.lvNumbers)
        tvError = findViewById(R.id.tvError)

        // Set the click listener for the Show button
        btnShow.setOnClickListener {
            // Clear previous error message
            tvError.text = ""

            // Get the input from the EditText
            val input = etNumber.text.toString()
            val n: Int

            // Validate the input
            if (input.isBlank()) {
                tvError.text = "Vui lòng nhập số nguyên dương"
                return@setOnClickListener
            }

            try {
                n = input.toInt()
                if (n <= 0) {
                    tvError.text = "Vui lòng nhập số nguyên dương"
                    return@setOnClickListener
                }
            } catch (e: NumberFormatException) {
                tvError.text = "Vui lòng nhập số nguyên dương hợp lệ"
                return@setOnClickListener
            }

            // Check if a RadioButton is selected
            val selectedOptionId = rgOptions.checkedRadioButtonId
            if (selectedOptionId == -1) {
                tvError.text = "Vui lòng chọn loại số"
                return@setOnClickListener
            }

            // Initialize an ArrayList to hold the numbers
            val numbersList = ArrayList<Int>()

            // Generate the list based on the selected option
            when (selectedOptionId) {
                R.id.rbEven -> {
                    // Even numbers from 0 to n
                    for (i in 0..n step 2) {
                        numbersList.add(i)
                    }
                }
                R.id.rbOdd -> {
                    // Odd numbers from 1 to n
                    for (i in 1..n step 2) {
                        numbersList.add(i)
                    }
                }
                R.id.rbPerfectSquare -> {
                    // Perfect squares from 0 to n
                    var i = 0
                    while (i * i <= n) {
                        numbersList.add(i * i)
                        i++
                    }
                }
            }

            // Set up the adapter for the ListView
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbersList)
            lvNumbers.adapter = adapter
        }
    }
}
