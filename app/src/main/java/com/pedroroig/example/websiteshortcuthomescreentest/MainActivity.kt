package com.pedroroig.example.websiteshortcuthomescreentest

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast


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
        if (checkCanCreateShortcut()) {
            Toast.makeText(this, "Selected: ${spinner.selectedItem}", Toast.LENGTH_SHORT).show()

            val title = getTitleFromUrl(spinner.selectedItem as String)
            val pinShortcutInfo = ShortcutInfoCompat.Builder(this, title)
                    .setShortLabel(title)
                    .setLongLabel(title)
                    .setIcon(IconCompat.createWithResource(this, R.drawable.ic_launcher_foreground))
                    .setIntent(Intent(Intent.ACTION_VIEW, Uri.parse(spinner.selectedItem as String)))
                    .build()


            // Only notifies if success, if it fails it does nothing
            val pinnedShortcutCallbackIntent = ShortcutManagerCompat.createShortcutResultIntent(this, pinShortcutInfo)

            val successCallback = PendingIntent.getBroadcast(this, /* request code */ 0,
                    pinnedShortcutCallbackIntent, /* flags */ 0)

            ShortcutManagerCompat.requestPinShortcut(this, pinShortcutInfo, successCallback.intentSender)

            // TODO Implement callback action if you need to know if creation was successful and do something about it

        } else {
            Toast.makeText(this, "Your Android version is too old, " +
                    "it doesn't support pinned shortcuts", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkCanCreateShortcut() =
            ShortcutManagerCompat.isRequestPinShortcutSupported(this)

    private fun getTitleFromUrl(url: String): String =
            when (url) {
                "http://www.google.com" -> "Google"
                "http://www.github.com" -> "GitHub"
                "https://developer.android.com" -> "Android Developers"
                else -> throw IllegalArgumentException("Unexpected URL")
            }
}
