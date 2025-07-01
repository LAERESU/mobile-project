package com.example.mobile_project

import android.content.ContentValues
import android.util.Log
import org.json.JSONArray

class InitialDataSeeder(private val dbHelper: DatabaseHelper) {

    fun seed() {
        val db = dbHelper.writableDatabase

        // Fungsi bantu menambahkan soal ke tabel soal
        fun addSoal(
            isiSoal: String,
            idKuis: Long,
            jawabanBenar: String,
            tipe: String,
            opsi: List<String> = emptyList()
        ) {
            val soalValues = ContentValues().apply {
                put(DatabaseHelper.COLUMN_SOAL_ISI, isiSoal)
                put(DatabaseHelper.COLUMN_SOAL_POINT, 25)
                put(DatabaseHelper.COLUMN_SOAL_ID_KUIS, idKuis)
                put(DatabaseHelper.COLUMN_SOAL_TIPE, tipe)
                put(DatabaseHelper.COLUMN_SOAL_JAWABAN_BENAR, jawabanBenar)
                put(DatabaseHelper.COLUMN_SOAL_OPSI_JSON, JSONArray(opsi).toString()) // konversi List ke JSON String
            }
            db.insert(DatabaseHelper.TABLE_SOAL, null, soalValues)
        }

        // 🔸 DATA KUIS 1
        val materi1Id = db.insert(DatabaseHelper.TABLE_MATERI, null, ContentValues().apply {
            put(DatabaseHelper.COLUMN_MATERI_NAMA, "Perlawanan Rakyat")
            put(DatabaseHelper.COLUMN_MATERI_TANGGAL_DIBUAT, "2025-06-27")
        })
        val kuis1Id = db.insert(DatabaseHelper.TABLE_KUIS, null, ContentValues().apply {
            put(DatabaseHelper.COLUMN_KUIS_NAMA, "Kuis Perlawanan Rakyat")
            put(DatabaseHelper.COLUMN_KUIS_WAKTU_PENGERJAAN, "20 menit")
            put(DatabaseHelper.COLUMN_KUIS_TINGKAT_KESULITAN, "Sedang")
            put(DatabaseHelper.COLUMN_KUIS_ID_MATERI, materi1Id)
        })
        addSoal("Siapa pemimpin Perang Diponegoro?", kuis1Id, "Pangeran Diponegoro", "pilihan", listOf("Pangeran Diponegoro", "Teuku Umar", "Sisingamangaraja"))
        addSoal("Perang Padri dipimpin oleh?", kuis1Id, "Tuanku Imam Bonjol", "pilihan", listOf("Kapitan Pattimura", "Tuanku Imam Bonjol", "Sultan Hasanuddin"))
        addSoal("Sebutkan nama pahlawan dari Aceh!", kuis1Id, "Cut Nyak Dien", "isian")
        addSoal("Siapa pahlawan dari Maluku?", kuis1Id, "Kapitan Pattimura", "isian")

        // 🔸 DATA KUIS 2
        val materi2Id = db.insert(DatabaseHelper.TABLE_MATERI, null, ContentValues().apply {
            put(DatabaseHelper.COLUMN_MATERI_NAMA, "Masa Penjajahan")
            put(DatabaseHelper.COLUMN_MATERI_TANGGAL_DIBUAT, "2025-06-27")
        })
        val kuis2Id = db.insert(DatabaseHelper.TABLE_KUIS, null, ContentValues().apply {
            put(DatabaseHelper.COLUMN_KUIS_NAMA, "Kuis Masa Penjajahan")
            put(DatabaseHelper.COLUMN_KUIS_WAKTU_PENGERJAAN, "25 menit")
            put(DatabaseHelper.COLUMN_KUIS_TINGKAT_KESULITAN, "Sedang")
            put(DatabaseHelper.COLUMN_KUIS_ID_MATERI, materi2Id)
        })
        addSoal("VOC dibentuk pada tahun?", kuis2Id, "1602", "pilihan", listOf("1602", "1708", "1801"))
        addSoal("Tujuan utama VOC adalah?", kuis2Id, "Monopoli dagang", "pilihan", listOf("Pertanian", "Monopoli dagang", "Pendidikan"))
        addSoal("Penjajah pertama kali ke Indonesia?", kuis2Id, "Portugis", "isian")
        addSoal("Siapa Gubernur Jenderal VOC pertama?", kuis2Id, "Pieter Both", "isian")

        // 🔸 DATA KUIS 3
        val materi3Id = db.insert(DatabaseHelper.TABLE_MATERI, null, ContentValues().apply {
            put(DatabaseHelper.COLUMN_MATERI_NAMA, "Menuju Kemerdekaan")
            put(DatabaseHelper.COLUMN_MATERI_TANGGAL_DIBUAT, "2025-06-27")
        })
        val kuis3Id = db.insert(DatabaseHelper.TABLE_KUIS, null, ContentValues().apply {
            put(DatabaseHelper.COLUMN_KUIS_NAMA, "Kuis Menuju Kemerdekaan")
            put(DatabaseHelper.COLUMN_KUIS_WAKTU_PENGERJAAN, "20 menit")
            put(DatabaseHelper.COLUMN_KUIS_TINGKAT_KESULITAN, "Mudah")
            put(DatabaseHelper.COLUMN_KUIS_ID_MATERI, materi3Id)
        })
        addSoal("BPUPKI dibentuk pada tahun?", kuis3Id, "1945", "pilihan", listOf("1942", "1945", "1950"))
        addSoal("Siapa ketua PPKI?", kuis3Id, "Ir. Soekarno", "pilihan", listOf("Sutan Sjahrir", "Radjiman", "Ir. Soekarno"))
        addSoal("Piagam Jakarta dirumuskan oleh?", kuis3Id, "Panitia Sembilan", "isian")
        addSoal("Tanggal proklamasi kemerdekaan?", kuis3Id, "17 Agustus 1945", "isian")

        // 🔸 DATA KUIS 4
        val materi4Id = db.insert(DatabaseHelper.TABLE_MATERI, null, ContentValues().apply {
            put(DatabaseHelper.COLUMN_MATERI_NAMA, "Simbol Kemerdekaan")
            put(DatabaseHelper.COLUMN_MATERI_TANGGAL_DIBUAT, "2025-06-27")
        })
        val kuis4Id = db.insert(DatabaseHelper.TABLE_KUIS, null, ContentValues().apply {
            put(DatabaseHelper.COLUMN_KUIS_NAMA, "Kuis Simbol Negara")
            put(DatabaseHelper.COLUMN_KUIS_WAKTU_PENGERJAAN, "15 menit")
            put(DatabaseHelper.COLUMN_KUIS_TINGKAT_KESULITAN, "Mudah")
            put(DatabaseHelper.COLUMN_KUIS_ID_MATERI, materi4Id)
        })
        addSoal("Apa semboyan Indonesia?", kuis4Id, "Bhinneka Tunggal Ika", "pilihan", listOf("Garuda Sakti", "Satu Nusa", "Bhinneka Tunggal Ika"))
        addSoal("Apa lambang negara Indonesia?", kuis4Id, "Garuda Pancasila", "isian")
        addSoal("Warna bendera Indonesia adalah?", kuis4Id, "Merah Putih", "pilihan", listOf("Merah Kuning", "Putih Biru", "Merah Putih"))
        addSoal("Apa lagu kebangsaan Indonesia?", kuis4Id, "Indonesia Raya", "isian")

        Log.d("Seeder", "✅ Seeder selesai menambahkan soal dengan format terbaru.")
    }
}
