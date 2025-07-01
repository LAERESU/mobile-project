package com.example.mobile_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LatihanPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tes_pengetahuan)

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val dbHelper = DatabaseHelper(this)
        val seeder = InitialDataSeeder(dbHelper)

        // Lakukan seed jika belum ada kuis
        if (dbHelper.getAllKuis().isEmpty()) {
            Log.d("LatihanPage", "Database kuis kosong. Melakukan seeding awal.")
            seeder.seed()
        }

        val kuisList = dbHelper.getAllKuis()
        val container = findViewById<LinearLayout>(R.id.latihanContainer)

        if (kuisList.isEmpty()) {
            val emptyText = TextView(this).apply {
                text = "Belum ada kuis yang tersedia."
                textSize = 16f
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 24
                }
            }
            container.addView(emptyText)
        } else {
            kuisList.forEachIndexed { index, kuis ->
                val button = Button(this).apply {
                    text = kuis.namaKuis
                    setBackgroundResource(R.drawable.btn_latihan_shape)
                    textSize = 16f
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        topMargin = if (index == 0) 0 else 16
                    }
                    layoutParams = params
                }

                button.setOnClickListener {
                    val intent = Intent(this@LatihanPage, SoalPage::class.java)
                    intent.putExtra("ID_KUIS", kuis.idKuis)
                    startActivity(intent)
                }

                container.addView(button)
            }
        }
    }
}
