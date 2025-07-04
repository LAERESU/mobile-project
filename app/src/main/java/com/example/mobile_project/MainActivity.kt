package com.example.mobile_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deleteDatabase("mobile_project.db")

        val dbHelper = DatabaseHelper(this)
        val seeder = InitialDataSeeder(dbHelper)

        if (dbHelper.getAllKuis().isEmpty()) {
            Log.d("MainActivity", "Database kosong, melakukan seed data awal...")
            seeder.seed()
        } else {
            Log.d("MainActivity", "Data kuis sudah ada, tidak perlu seeding ulang.")
        }

        if (dbHelper.getAllPahlawan().isEmpty()) {
            Log.d("MainActivity", "Database pahlawan kosong, melakukan seed data awal...")
            seeder.seedPahlawan()
        } else {
            Log.d("MainActivity", "Data pahlawan sudah ada, tidak perlu seeding ulang.")
        }


        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val nama = sharedPref.getString("nama_lengkap", null)

        if (nama != null) {
            Log.d("MainActivity", "Biodata ditemukan: $nama. Menuju LandingPage.")
            startActivity(Intent(this, LandingPage::class.java))
        } else {
            Log.d("MainActivity", "Biodata belum diisi. Menuju BiodataPage.")
            startActivity(Intent(this, BiodataPage::class.java))
        }

        finish()
    }
}
