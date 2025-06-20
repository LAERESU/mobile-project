package com.example.mobile_project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = DatabaseHelper(this)
        if (!dbHelper.isSoalSudahAda()) {
            // Masukkan soal ke database
            val kuisId = 1L  // Pastikan ID kuis-nya valid
            dbHelper.insertSoalDanOpsi(
                "Siapa presiden pertama Indonesia?",
                10,
                kuisId,
                listOf(
                    "Ir. Soekarno" to true,
                    "Moh. Hatta" to false,
                    "Sutan Sjahrir" to false
                )
            )
        }

        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val nama = sharedPref.getString("nama_lengkap", null)

        if (nama != null) {
            startActivity(Intent(this, LandingPage::class.java))
        } else {
            startActivity(Intent(this, BiodataPage::class.java))
        }
        finish()
    }
}