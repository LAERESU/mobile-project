package com.example.mobile_project

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_project.model.Skor
import com.example.mobile_project.model.Soal
import com.google.firebase.database.FirebaseDatabase

class SoalPage : AppCompatActivity() {

    private lateinit var container: LinearLayout
    private lateinit var daftarSoal: List<Soal>
    private val jawabanUser = mutableMapOf<Int, String>()
    private var idKuis: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page6_soal)

        container = findViewById(R.id.soalContainer)
        val dbHelper = DatabaseHelper(this)

        idKuis = intent.getIntExtra("ID_KUIS", -1)
        if (idKuis == -1) {
            Toast.makeText(this, "Kuis tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        daftarSoal = dbHelper.getSoalDanOpsiByKuisId(idKuis)

        findViewById<ImageView>(R.id.backButton).setOnClickListener { finish() }

        tampilkanSoal()

        findViewById<Button>(R.id.btnSelesai).setOnClickListener {
            val (nilai, totalBenar, totalSalah) = hitungSkor()

            val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val nisn = sharedPref.getString("nisn", null)
            if (!nisn.isNullOrBlank()) {
                val database = FirebaseDatabase.getInstance()
                val scoreRef = database.getReference("score_kuis")

                val skorData = Skor(
                    idKuis = "kuis_$idKuis",
                    nisn = nisn,
                    nilai = nilai,
                    benar = totalBenar,
                    salah = totalSalah
                )

                scoreRef.child(nisn).child("kuis_$idKuis").setValue(skorData)
            }

            val intent = Intent(this, HasilPage::class.java).apply {
                putExtra("ID_KUIS", "kuis_$idKuis")
            }
            startActivity(intent)
            finish()
        }
    }

    private fun tampilkanSoal() {
        for ((index, soal) in daftarSoal.withIndex()) {
            val tvSoal = TextView(this).apply {
                text = "Soal ${index + 1}:\n${soal.isiSoal}"
                setTextColor(resources.getColor(android.R.color.white))
                textSize = 16f
                setPadding(0, 16, 0, 8)
            }
            container.addView(tvSoal)

            if (soal.tipe == "pilihan" && soal.opsi.isNotEmpty()) {
                val radioGroup = RadioGroup(this).apply {
                    orientation = RadioGroup.VERTICAL
                }
                soal.opsi.forEach { opsi ->
                    val radioButton = RadioButton(this).apply {
                        text = opsi
                        setTextColor(resources.getColor(android.R.color.white))
                        setPadding(12, 12, 12, 12)
                    }
                    radioGroup.addView(radioButton)
                }
                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    val selected = group.findViewById<RadioButton>(checkedId)
                    jawabanUser[index] = selected.text.toString()
                }
                container.addView(radioGroup)
            } else {
                val editText = EditText(this).apply {
                    hint = "Tulis jawabanmu di sini..."
                    setHintTextColor(resources.getColor(android.R.color.darker_gray))
                    setTextColor(resources.getColor(android.R.color.white))
                    setBackgroundResource(R.drawable.edit_text_bg)
                    setPadding(24, 16, 24, 16)
                }
                editText.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        jawabanUser[index] = editText.text.toString().trim()
                    }
                }
                container.addView(editText)
            }
        }
    }

    private fun hitungSkor(): Triple<Int, Int, Int> {
        var nilai = 0
        var benar = 0
        var salah = 0

        for ((index, soal) in daftarSoal.withIndex()) {
            val jawaban = jawabanUser[index]?.trim()?.lowercase() ?: ""
            val kunci = soal.jawabanBenar.trim().lowercase()
            if (jawaban == kunci) {
                benar++
                nilai += soal.point
            } else {
                salah++
            }
        }

        return Triple(nilai, benar, salah)
    }
}
