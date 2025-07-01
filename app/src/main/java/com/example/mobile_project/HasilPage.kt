package com.example.mobile_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class HasilPage : AppCompatActivity() {

    private lateinit var skorTextView: TextView
    private lateinit var benarButton: Button
    private lateinit var salahButton: Button
    private lateinit var userInfoTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var btnKembali: Button
    private lateinit var backButton: ImageView

    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page7_hasil)

        skorTextView = findViewById(R.id.skorTextView)
        benarButton = findViewById(R.id.btnBenar)
        salahButton = findViewById(R.id.btnSalah)
        userInfoTextView = findViewById(R.id.userInfoTextView)
        statusTextView = findViewById(R.id.statusLulusTextView)
        btnKembali = findViewById(R.id.btnKembali)
        backButton = findViewById(R.id.backButton)

        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val nisn = sharedPref.getString("nisn", "-")
        val namaLengkap = sharedPref.getString("nama_lengkap", "-")
        val idKuis = intent.getStringExtra("ID_KUIS") ?: return

        userInfoTextView.text = "NISN: $nisn\nNama: $namaLengkap"

        if (!nisn.isNullOrBlank()) {
            database = FirebaseDatabase.getInstance()
            ref = database.getReference("score_kuis").child(nisn).child(idKuis)

            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val nilai = snapshot.child("nilai").getValue(Int::class.java) ?: 0
                        val benar = snapshot.child("benar").getValue(Int::class.java) ?: 0
                        val salah = snapshot.child("salah").getValue(Int::class.java) ?: 0

                        val lulus = nilai >= 70
                        skorTextView.text = "$nilai / 100"
                        benarButton.text = "Benar : $benar"
                        salahButton.text = "Salah : $salah"
                        statusTextView.text = if (lulus) "Status: LULUS 🎉" else "Status: BELUM LULUS"
                        statusTextView.setTextColor(
                            resources.getColor(if (lulus) android.R.color.holo_green_light else android.R.color.holo_red_light)
                        )
                    } else {
                        skorTextView.text = "Skor tidak ditemukan"
                        benarButton.text = "Benar : -"
                        salahButton.text = "Salah : -"
                        statusTextView.text = "Status: Tidak tersedia"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseRead", "Gagal membaca skor: ${error.message}")
                }
            })
        }

        backButton.setOnClickListener {
            startActivity(Intent(this, LatihanPage::class.java))
        }

        btnKembali.setOnClickListener {
            startActivity(Intent(this, LatihanPage::class.java))
        }
    }
}
