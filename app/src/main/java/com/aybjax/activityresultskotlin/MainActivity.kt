package com.aybjax.activityresultskotlin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

const val PCK_RAINBOW_COLOR_INTENT = 1 // request code
const val RAINBOW_COLOR_NAME = "RAINBOW_COLOR_NAME"
const val RAINBOW_COLOR = "RAINBOW_COLOR"
const val DEFAULT_COLOR = "#FFFFFF"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.submit_button).setOnClickListener {
            Intent(this, RainbowColorPickerActivity::class.java)
                .also {
                    startActivityForResult(
                        it,
                        PCK_RAINBOW_COLOR_INTENT
                    )
                }
        }

        findViewById<Button>(R.id.submit_button_new).setOnClickListener {
            val intent = Intent(this, RainbowColorPickerActivity::class.java)
            colorPickerRequest.launch(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PCK_RAINBOW_COLOR_INTENT &&
                resultCode == Activity.RESULT_OK) {
            val backgroundColor = data?.getIntExtra(RAINBOW_COLOR,
                        Color.parseColor(DEFAULT_COLOR)) ?: Color.parseColor(DEFAULT_COLOR)
            val colorName = data?.getStringExtra(RAINBOW_COLOR_NAME) ?: ""
            val colorMessage = getString(R.string.color_chosen_message, colorName)

            val rainbowColor = findViewById<TextView>(R.id.rainbo_color)

            rainbowColor.setBackgroundColor(ContextCompat.getColor(this, backgroundColor))
            rainbowColor.text = colorMessage
            rainbowColor.isVisible = true
        }
    }

    private val colorPickerRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val data = it.data
            // do whatever with the data in the callback
            val backgroundColor = data?.getIntExtra(RAINBOW_COLOR,
                Color.parseColor(DEFAULT_COLOR)) ?: Color.parseColor(DEFAULT_COLOR)
            val colorName = data?.getStringExtra(RAINBOW_COLOR_NAME) ?: ""
            val colorMessage = getString(R.string.color_chosen_message, colorName)

            val rainbowColor = findViewById<TextView>(R.id.rainbo_color)

            rainbowColor.setBackgroundColor(ContextCompat.getColor(this, backgroundColor))
            rainbowColor.text = colorMessage
            rainbowColor.isVisible = true
        }
    }
}