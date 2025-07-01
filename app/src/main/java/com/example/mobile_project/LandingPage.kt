package com.example.mobile_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnBelajar = findViewById<Button>(R.id.btnBelajar)
        btnBelajar.setOnClickListener {
            val intent = Intent(this, BelajarPage::class.java)
            startActivity(intent)
        }
        val btnGame = findViewById<Button>(R.id.btnGame)
        btnGame.setOnClickListener {
            val intent = Intent(this, GamePage::class.java)
            startActivity(intent)
        }

        val btnTes = findViewById<Button>(R.id.btnLatihan)
        btnTes.setOnClickListener {
            val intent = Intent(this, LatihanPage::class.java)
            startActivity(intent)
        }

    }
}
