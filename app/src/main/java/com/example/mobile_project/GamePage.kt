package com.example.mobile_project

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_project.model.Soal

class GamePage : AppCompatActivity() {

    private lateinit var karakter: ImageView
    private lateinit var pos1: ImageView
    private lateinit var pos2: ImageView
    private lateinit var pos3: ImageView
    private lateinit var pos4: ImageView

    private var currentLevel = 1
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permainan_page)

        dbHelper = DatabaseHelper(this)

        val backButton = findViewById<ImageView>(R.id.backButton)
        karakter = findViewById(R.id.imageView10)
        pos1 = findViewById(R.id.pos1)
        pos2 = findViewById(R.id.pos2)
        pos3 = findViewById(R.id.pos3)
        pos4 = findViewById(R.id.pos4)

        backButton.setOnClickListener {
            finish()
        }

        pos1.setOnClickListener {
            if (currentLevel == 1) tampilkanSoalDariLevel(1)
        }

        pos2.setOnClickListener {
            if (currentLevel == 2) tampilkanSoalDariLevel(2)
        }

        pos3.setOnClickListener {
            if (currentLevel == 3) tampilkanSoalDariLevel(3)
        }

        pos4.setOnClickListener {
            if (currentLevel == 4) tampilkanSoalDariLevel(4)
        }
    }

    private fun tampilkanSoalDariLevel(level: Int) {
        val soalList = dbHelper.getSoalDanOpsiByKuisId(level)
        if (soalList.isNotEmpty()) {
            val soal = soalList[0] // satu soal saja per level
            tampilkanDialogSoal(soal)
        } else {
            Toast.makeText(this, "Soal tidak ditemukan.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun tampilkanDialogSoal(soal: Soal) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.pertanyaan, null)

        val posTitle = dialogView.findViewById<TextView>(R.id.pos1)
        val pertanyaanText = dialogView.findViewById<TextView>(R.id.pertanyaan1)
        val pilihan1 = dialogView.findViewById<TextView>(R.id.pilihan1)
        val pilihan2 = dialogView.findViewById<TextView>(R.id.pilihan2)
        val pilihan3 = dialogView.findViewById<TextView>(R.id.pilihan3)

        posTitle.text = "POS $currentLevel"
        pertanyaanText.text = soal.isiSoal

        if (soal.tipe == "pilihan") {
            pilihan1.visibility = View.VISIBLE
            pilihan2.visibility = View.VISIBLE
            pilihan3.visibility = View.VISIBLE

            pilihan1.text = "a. ${soal.opsi.getOrNull(0) ?: "-"}"
            pilihan2.text = "b. ${soal.opsi.getOrNull(1) ?: "-"}"
            pilihan3.text = "c. ${soal.opsi.getOrNull(2) ?: "-"}"

        } else {
            // Tipe isian → ganti pilihan1 jadi EditText
            pilihan1.visibility = View.GONE
            pilihan2.visibility = View.GONE
            pilihan3.visibility = View.GONE
        }

        val builder = AlertDialog.Builder(this).setView(dialogView)
        val dialog = builder.create()
        dialog.show()

        if (soal.tipe == "pilihan") {
            val pilihanViews = listOf(pilihan1, pilihan2, pilihan3)

            for (view in pilihanViews) {
                view.setOnClickListener {
                    val jawabanUser = view.text.toString().substringAfter(". ").trim()
                    if (jawabanUser.equals(soal.jawabanBenar, ignoreCase = true)) {
                        Toast.makeText(this, "Jawaban Benar!", Toast.LENGTH_SHORT).show()
                        moveCharacterTo(currentLevel)
                        currentLevel++
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this, "Jawaban Salah!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        } else {
            // Buat EditText manual untuk jawaban isian
            val inputField = EditText(this)
            inputField.hint = "Tulis jawaban..."
            val layout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(40, 20, 40, 10)
                addView(dialogView)
                addView(inputField)
            }

            val isianDialog = AlertDialog.Builder(this)
                .setView(layout)
                .setPositiveButton("Jawab") { d, _ ->
                    val jawaban = inputField.text.toString().trim()
                    if (jawaban.equals(soal.jawabanBenar, ignoreCase = true)) {
                        Toast.makeText(this, "Jawaban Benar!", Toast.LENGTH_SHORT).show()
                        moveCharacterTo(currentLevel)
                        currentLevel++
                        d.dismiss()
                    } else {
                        Toast.makeText(this, "Jawaban Salah!", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Batal") { d, _ -> d.dismiss() }
                .create()

            dialog.dismiss()
            isianDialog.show()
        }
    }

    private fun moveCharacterTo(level: Int) {
        val target = when (level) {
            1 -> pos1
            2 -> pos2
            3 -> pos3
            4 -> pos4
            else -> null
        }

        target?.let {
            karakter.animate()
                .x(it.x)
                .y(it.y)
                .setDuration(600)
                .start()
        }
    }
}
