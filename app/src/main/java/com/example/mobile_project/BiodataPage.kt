package com.example.mobile_project

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_project.model.Siswa
import com.google.firebase.database.FirebaseDatabase

class BiodataPage : AppCompatActivity() {

    private lateinit var etNamaLengkap: EditText
    private lateinit var etNISN: EditText
    private lateinit var etNamaSekolah: EditText
    private lateinit var etKelas: EditText
    private lateinit var btnKirim: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biodata)

        etNamaLengkap = findViewById(R.id.etNamaLengkap)
        etNISN = findViewById(R.id.etNisn)
        etNamaSekolah = findViewById(R.id.etNamaSekolah)
        etKelas = findViewById(R.id.etKelas)
        btnKirim = findViewById(R.id.btnKirim)

        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users")

        btnKirim.setOnClickListener {
            val namaLengkap = etNamaLengkap.text.toString()
            val nisn = etNISN.text.toString()
            val namaSekolah = etNamaSekolah.text.toString()
            val kelas = etKelas.text.toString()

            if (namaLengkap.isNotBlank() && nisn.isNotBlank() && namaSekolah.isNotBlank() && kelas.isNotBlank()) {

                val siswa = Siswa(namaLengkap, nisn, namaSekolah, kelas)

                userRef.child(nisn).setValue(siswa).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("nama_lengkap", namaLengkap)
                            putString("nisn", nisn)
                            putString("nama_sekolah", namaSekolah)
                            putString("kelas", kelas)
                            apply()
                        }

                        Toast.makeText(this, "Biodata berhasil disimpan ke Firebase!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LandingPage::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Gagal simpan ke Firebase: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(this, "Semua kolom wajib diisi.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
