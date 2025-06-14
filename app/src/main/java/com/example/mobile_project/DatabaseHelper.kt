package com.example.mobile_project

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "mobile_project.db"
        private const val DATABASE_VERSION = 1

        // Tabel Pahlawan
        const val TABLE_PAHLAWAN = "pahlawan"
        const val COLUMN_PAHLAWAN_ID = "id_pahlawan"
        const val COLUMN_PAHLAWAN_NAMA = "nama_pahlawan"
        const val COLUMN_PAHLAWAN_TEMPAT_LAHIR = "tempat_lahir"
        const val COLUMN_PAHLAWAN_TANGGAL_LAHIR = "tanggal_lahir"
        const val COLUMN_PAHLAWAN_JULUKAN = "julukan"

        // Tabel Peristiwa
        const val TABLE_PERISTIWA = "peristiwa"
        const val COLUMN_PERISTIWA_ID = "id_peristiwa"
        const val COLUMN_PERISTIWA_ISI = "isi"
        const val COLUMN_PERISTIWA_LOKASI = "lokasi"
        const val COLUMN_PERISTIWA_TANGGAL = "tanggal"
        const val COLUMN_PERISTIWA_ID_MATERI = "id_materi"

        // Tabel Materi
        const val TABLE_MATERI = "materi"
        const val COLUMN_MATERI_ID = "id_materi"
        const val COLUMN_MATERI_NAMA = "nama_materi"
        const val COLUMN_MATERI_TANGGAL_DIBUAT = "tanggal_dibuat"

        // Tabel Kuis
        const val TABLE_KUIS = "kuis"
        const val COLUMN_KUIS_ID = "id_kuis"
        const val COLUMN_KUIS_NAMA = "nama_kuis"
        const val COLUMN_KUIS_WAKTU_PENGERJAAN = "waktu_pengerjaan"
        const val COLUMN_KUIS_TINGKAT_KESULITAN = "tingkat_kesulitan"
        const val COLUMN_KUIS_ID_MATERI = "id_materi"

        // Tabel Soal
        const val TABLE_SOAL = "soal"
        const val COLUMN_SOAL_ID = "id_soal"
        const val COLUMN_SOAL_ISI = "isi_soal"
        const val COLUMN_SOAL_POINT = "point"
        const val COLUMN_SOAL_ID_KUIS = "id_kuis"

        // Tabel Opsi
        const val TABLE_OPSI = "opsi"
        const val COLUMN_OPSI_ID = "id_opsi"
        const val COLUMN_OPSI_ISI = "isi_opsi"
        const val COLUMN_OPSI_ID_SOAL = "id_soal"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // tabel Pahlawan
        val createPahlawan = "CREATE TABLE $TABLE_PAHLAWAN (" +
                "$COLUMN_PAHLAWAN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PAHLAWAN_NAMA TEXT, " +
                "$COLUMN_PAHLAWAN_TEMPAT_LAHIR TEXT, " +
                "$COLUMN_PAHLAWAN_TANGGAL_LAHIR TEXT, " +
                "$COLUMN_PAHLAWAN_JULUKAN TEXT)"
        db?.execSQL(createPahlawan)

        // tabel Peristiwa
        val createPeristiwa = "CREATE TABLE $TABLE_PERISTIWA (" +
                "$COLUMN_PERISTIWA_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PERISTIWA_ISI TEXT, " +
                "$COLUMN_PERISTIWA_LOKASI TEXT, " +
                "$COLUMN_PERISTIWA_TANGGAL TEXT, " +
                "$COLUMN_PERISTIWA_ID_MATERI INTEGER, " +
                "FOREIGN KEY ($COLUMN_PERISTIWA_ID_MATERI) REFERENCES $TABLE_MATERI($COLUMN_MATERI_ID))"
        db?.execSQL(createPeristiwa)

        // tabel Materi
        val createMateri = "CREATE TABLE $TABLE_MATERI (" +
                "$COLUMN_MATERI_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_MATERI_NAMA TEXT, " +
                "$COLUMN_MATERI_TANGGAL_DIBUAT TEXT)"
        db?.execSQL(createMateri)

        // tabel Kuis
        val createKuis = "CREATE TABLE $TABLE_KUIS (" +
                "$COLUMN_KUIS_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_KUIS_NAMA TEXT, " +
                "$COLUMN_KUIS_WAKTU_PENGERJAAN TEXT, " +
                "$COLUMN_KUIS_TINGKAT_KESULITAN TEXT, " +
                "$COLUMN_KUIS_ID_MATERI INTEGER, " +
                "FOREIGN KEY ($COLUMN_KUIS_ID_MATERI) REFERENCES $TABLE_MATERI($COLUMN_MATERI_ID))"
        db?.execSQL(createKuis)

        // tabel Soal
        val createSoal = "CREATE TABLE $TABLE_SOAL (" +
                "$COLUMN_SOAL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_SOAL_ISI TEXT, " +
                "$COLUMN_SOAL_POINT INTEGER, " +
                "$COLUMN_SOAL_ID_KUIS INTEGER, " +
                "FOREIGN KEY ($COLUMN_SOAL_ID_KUIS) REFERENCES $TABLE_KUIS($COLUMN_KUIS_ID))"
        db?.execSQL(createSoal)

        // tabel Opsi
        val createOpsi = "CREATE TABLE $TABLE_OPSI (" +
                "$COLUMN_OPSI_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_OPSI_ISI TEXT, " +
                "$COLUMN_OPSI_ID_SOAL INTEGER, " +
                "FOREIGN KEY ($COLUMN_OPSI_ID_SOAL) REFERENCES $TABLE_SOAL($COLUMN_SOAL_ID))"
        db?.execSQL(createOpsi)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PAHLAWAN")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PERISTIWA")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MATERI")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_KUIS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SOAL")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_OPSI")
        onCreate(db)
    }
}