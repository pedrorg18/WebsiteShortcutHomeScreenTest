package com.pedroroig.example.websiteshortcuthomescreentest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*


class MainActivity : AppCompatActivity() {

    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spinnerWebSites)
        populateSpinnerWithWebsites()

        val buttonCreateShortcut = findViewById<Button>(R.id.buttonCreateShortcut)
        buttonCreateShortcut.setOnClickListener {
            createShortcutInHomeMenu()
        }

    }

    private fun populateSpinnerWithWebsites() {

        val items = arrayOf(
                "http://www.google.com",
                "http://www.github.com",
                "https://developer.android.com")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter
    }

    private fun createShortcutInHomeMenu() {
        Toast.makeText(this, "Selected: ${spinner.selectedItem}", Toast.LENGTH_SHORT).show()
    }
}
