package com.example.mobile_project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_project.R

class BelajarPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_tokoh)

        // Tombol back (ImageView) untuk kembali
        val backButton = findViewById<android.widget.ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
        val cardBox1 = findViewById<android.widget.LinearLayout>(R.id.cardBox1) // Pastikan ID ini ada di layout yang di-set di setContentView
        cardBox1.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}
