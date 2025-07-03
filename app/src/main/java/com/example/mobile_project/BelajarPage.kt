package com.example.mobile_project

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_project.adapter.PahlawanAdapter
import com.example.mobile_project.model.Pahlawan

class BelajarPage : AppCompatActivity() {

    private lateinit var rvPahlawan: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_belajar)

        // Inisialisasi view
        rvPahlawan = findViewById(R.id.rvPahlawan)
        backButton = findViewById(R.id.backButton)

        // Ambil data dari SQLite
        dbHelper = DatabaseHelper(this)
        val listPahlawan = dbHelper.getAllPahlawan()

        if (listPahlawan.isEmpty()) {
            Toast.makeText(this, "Data pahlawan kosong", Toast.LENGTH_SHORT).show()
        }

        // Set layout manager dan adapter recycler view
        rvPahlawan.layoutManager = LinearLayoutManager(this)
        rvPahlawan.adapter = PahlawanAdapter(listPahlawan) { selectedPahlawan ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("idPahlawan", selectedPahlawan.idPahlawan)
            startActivity(intent)
        }

        // Tombol back
        backButton.setOnClickListener {
            finish()
        }
    }
}
