package com.example.mobile_project

import android.content.ContentValues
import android.util.Log
import com.example.mobile_project.model.Pahlawan
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
                put(DatabaseHelper.COLUMN_SOAL_OPSI_JSON, JSONArray(opsi).toString())
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
        db.close()
    }

    fun seedPahlawan() {
        val db = dbHelper.writableDatabase

        val pahlawanList = listOf(
            Pahlawan(1, "Dr. Soetomo", "Surabaya", "30 Juli 1888", "Pendiri Budi Utomo", "dr_soetomo", "dr_soetomo_img", "dr_soetomo_cerita", "dr_soetomo_audio", 0),
            Pahlawan(2, "Ki Hajar Dewantara", "Yogyakarta", "2 Mei 1889", "Bapak Pendidikan Nasional", "ki_hajar_dewantara", "ki_hajar_dewantara_img", "ki_hajar_dewantara_cerita", "ki_hajar_dewantara_audio", 0),
            Pahlawan(3, "Haji Agus Salim", "Minangkabau", "8 Oktober 1884", "Diplomat Ulung", "haji_agus_salim", "haji_agus_salim_img", "haji_agus_salim_cerita", "haji_agus_salim_audio", 0),
            Pahlawan(4, "Ir. Soekarno", "Blitar", "6 Juni 1901", "Proklamator Kemerdekaan", "ir_soekarno", "ir_soekarno_img", "ir_soekarno_cerita", "ir_soekarno_audio",0),
            Pahlawan(5, "KH. Ahmad Dahlan", "Yogyakarta", "1 Agustus 1868", "Pendiri Muhammadiyah", "ahmad_dahlan", "ahmad_dahlan_img", "ahmad_dahlan_cerita", "ahmad_dahlan_audio",0),
            Pahlawan(6, "KH. Hasyim Asy’ari", "Jombang", "14 Februari 1871", "Pendiri NU", "hasyim_asyari", "hasyim_asyari_img", "hasyim_asyari_cerita", "hasyim_asyari_audio",0),
            Pahlawan(7, "Sutan Sjahrir", "Sumatra Barat", "5 Maret 1909", "Perdana Menteri Pertama", "sutan_sjahrir", "sutan_sjahrir_img", "sutan_sjahrir_cerita", "sutan_sjahrir_audio",0),
            Pahlawan(8, "Tan Malaka", "Sumatra Barat", "2 Juni 1897", "Pemikir Revolusioner", "tan_malaka", "tan_malaka_img", "tan_malaka_cerita", "tan_malaka_audio",0),
            Pahlawan(9, "Mohammad Hatta", "Bukittinggi", "12 Agustus 1902", "Proklamator & Wakil Presiden Pertama", "mohammad_hatta", "mohammad_hatta_img", "mohammad_hatta_cerita", "mohammad_hatta_audio",0),
            Pahlawan(10, "Cut Nyak Dien", "Aceh", "1848", "Singa Betina dari Aceh", "cut_nyak_dien", "cut_nyak_dien_img", "cut_nyak_dien_cerita", "cut_nyak_dien_audio",0)
        )

        for (p in pahlawanList) {
            val values = ContentValues().apply {
                put(DatabaseHelper.COLUMN_PAHLAWAN_ID, p.idPahlawan)
                put(DatabaseHelper.COLUMN_PAHLAWAN_NAMA, p.namaPahlawan)
                put(DatabaseHelper.COLUMN_PAHLAWAN_TEMPAT_LAHIR, p.tempatLahir)
                put(DatabaseHelper.COLUMN_PAHLAWAN_TANGGAL_LAHIR, p.tanggalLahir)
                put(DatabaseHelper.COLUMN_PAHLAWAN_JULUKAN, p.julukan)
                put(DatabaseHelper.COLUMN_PAHLAWAN_TOKOH, p.tokoh)
                put(DatabaseHelper.COLUMN_PAHLAWAN_FOTO, p.foto)
                put(DatabaseHelper.COLUMN_PAHLAWAN_CERITA, p.cerita)
                put(DatabaseHelper.COLUMN_PAHLAWAN_AUDIO, p.audio)
                put(DatabaseHelper.COLUMN_PAHLAWAN_IS_LEARNED, p.isLearned)
            }
            db.insert(DatabaseHelper.TABLE_PAHLAWAN, null, values)
        }

        Log.d("Seeder", "✅ Seeder selesai menambahkan data pahlawan.")
        db.close()
    }
}
