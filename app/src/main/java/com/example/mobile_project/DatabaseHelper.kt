package com.example.mobile_project

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mobile_project.model.Kuis
import com.example.mobile_project.model.Soal
import org.json.JSONArray

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mobile_project.db"
        private const val DATABASE_VERSION = 4

        // Table & Column Names
        const val TABLE_PAHLAWAN = "pahlawan"
        const val COLUMN_PAHLAWAN_ID = "id_pahlawan"
        const val COLUMN_PAHLAWAN_NAMA = "nama_pahlawan"
        const val COLUMN_PAHLAWAN_TEMPAT_LAHIR = "tempat_lahir"
        const val COLUMN_PAHLAWAN_TANGGAL_LAHIR = "tanggal_lahir"
        const val COLUMN_PAHLAWAN_JULUKAN = "julukan"

        const val TABLE_PERISTIWA = "peristiwa"
        const val COLUMN_PERISTIWA_ID = "id_peristiwa"
        const val COLUMN_PERISTIWA_ISI = "isi"
        const val COLUMN_PERISTIWA_LOKASI = "lokasi"
        const val COLUMN_PERISTIWA_TANGGAL = "tanggal"
        const val COLUMN_PERISTIWA_ID_MATERI = "id_materi"

        const val TABLE_MATERI = "materi"
        const val COLUMN_MATERI_ID = "id_materi"
        const val COLUMN_MATERI_NAMA = "nama_materi"
        const val COLUMN_MATERI_TANGGAL_DIBUAT = "tanggal_dibuat"

        const val TABLE_KUIS = "kuis"
        const val COLUMN_KUIS_ID = "id_kuis"
        const val COLUMN_KUIS_NAMA = "nama_kuis"
        const val COLUMN_KUIS_WAKTU_PENGERJAAN = "waktu_pengerjaan"
        const val COLUMN_KUIS_TINGKAT_KESULITAN = "tingkat_kesulitan"
        const val COLUMN_KUIS_ID_MATERI = "id_materi"

        const val TABLE_SOAL = "soal"
        const val COLUMN_SOAL_ID = "id_soal"
        const val COLUMN_SOAL_ISI = "isi_soal"
        const val COLUMN_SOAL_POINT = "point"
        const val COLUMN_SOAL_ID_KUIS = "id_kuis"
        const val COLUMN_SOAL_TIPE = "tipe"
        const val COLUMN_SOAL_JAWABAN_BENAR = "jawaban_benar"
        const val COLUMN_SOAL_OPSI_JSON = "opsi"

        const val TABLE_BIODATA = "biodata"
        const val COLUMN_BIODATA_ID = "id_biodata"
        const val COLUMN_BIODATA_NAMA_LENGKAP = "nama_lengkap"
        const val COLUMN_BIODATA_NAMA_PANGGILAN = "nama_panggilan"
        const val COLUMN_BIODATA_NAMA_SEKOLAH = "nama_sekolah"
        const val COLUMN_BIODATA_KELAS = "kelas"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE $TABLE_PAHLAWAN (
                $COLUMN_PAHLAWAN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PAHLAWAN_NAMA TEXT,
                $COLUMN_PAHLAWAN_TEMPAT_LAHIR TEXT,
                $COLUMN_PAHLAWAN_TANGGAL_LAHIR TEXT,
                $COLUMN_PAHLAWAN_JULUKAN TEXT
            )
        """.trimIndent())

        db?.execSQL("""
            CREATE TABLE $TABLE_MATERI (
                $COLUMN_MATERI_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MATERI_NAMA TEXT,
                $COLUMN_MATERI_TANGGAL_DIBUAT TEXT
            )
        """.trimIndent())

        db?.execSQL("""
            CREATE TABLE $TABLE_KUIS (
                $COLUMN_KUIS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_KUIS_NAMA TEXT,
                $COLUMN_KUIS_WAKTU_PENGERJAAN TEXT,
                $COLUMN_KUIS_TINGKAT_KESULITAN TEXT,
                $COLUMN_KUIS_ID_MATERI INTEGER,
                FOREIGN KEY ($COLUMN_KUIS_ID_MATERI) REFERENCES $TABLE_MATERI($COLUMN_MATERI_ID)
            )
        """.trimIndent())

        db?.execSQL("""
            CREATE TABLE $TABLE_SOAL (
                $COLUMN_SOAL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SOAL_ISI TEXT,
                $COLUMN_SOAL_POINT INTEGER,
                $COLUMN_SOAL_ID_KUIS INTEGER,
                $COLUMN_SOAL_TIPE TEXT,
                $COLUMN_SOAL_JAWABAN_BENAR TEXT,
                $COLUMN_SOAL_OPSI_JSON TEXT,
                FOREIGN KEY ($COLUMN_SOAL_ID_KUIS) REFERENCES $TABLE_KUIS($COLUMN_KUIS_ID)
            )
        """.trimIndent())

        db?.execSQL("""
            CREATE TABLE $TABLE_BIODATA (
                $COLUMN_BIODATA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_BIODATA_NAMA_LENGKAP TEXT,
                $COLUMN_BIODATA_NAMA_PANGGILAN TEXT,
                $COLUMN_BIODATA_NAMA_SEKOLAH TEXT,
                $COLUMN_BIODATA_KELAS TEXT
            )
        """.trimIndent())

        db?.execSQL("""
            CREATE TABLE $TABLE_PERISTIWA (
                $COLUMN_PERISTIWA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PERISTIWA_ISI TEXT,
                $COLUMN_PERISTIWA_LOKASI TEXT,
                $COLUMN_PERISTIWA_TANGGAL TEXT,
                $COLUMN_PERISTIWA_ID_MATERI INTEGER,
                FOREIGN KEY ($COLUMN_PERISTIWA_ID_MATERI) REFERENCES $TABLE_MATERI($COLUMN_MATERI_ID)
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SOAL")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_KUIS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MATERI")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PERISTIWA")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PAHLAWAN")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_BIODATA")
        onCreate(db)
    }

    fun insertBiodata(namaLengkap: String, namaPanggilan: String, namaSekolah: String, kelas: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BIODATA_NAMA_LENGKAP, namaLengkap)
            put(COLUMN_BIODATA_NAMA_PANGGILAN, namaPanggilan)
            put(COLUMN_BIODATA_NAMA_SEKOLAH, namaSekolah)
            put(COLUMN_BIODATA_KELAS, kelas)
        }
        return db.insert(TABLE_BIODATA, null, values).also { db.close() }
    }

    fun getAllKuis(): List<Kuis> {
        val list = mutableListOf<Kuis>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_KUIS", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_KUIS_ID))
            val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KUIS_NAMA))
            val waktu = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KUIS_WAKTU_PENGERJAAN))
            val tingkat = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KUIS_TINGKAT_KESULITAN))
            val idMateri = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_KUIS_ID_MATERI))
            list.add(Kuis(id, nama, waktu, tingkat, idMateri))
        }

        cursor.close()
        db.close()
        return list
    }

    fun getSoalDanOpsiByKuisId(idKuis: Int): List<Soal> {
        val list = mutableListOf<Soal>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_SOAL WHERE $COLUMN_SOAL_ID_KUIS = ?", arrayOf(idKuis.toString()))

        while (cursor.moveToNext()) {
            val idSoal = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SOAL_ID))
            val isi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOAL_ISI))
            val point = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SOAL_POINT))
            val tipe = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOAL_TIPE))
            val jawaban = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOAL_JAWABAN_BENAR))
            val opsiJson = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOAL_OPSI_JSON))
            val opsiList = if (opsiJson.isNullOrEmpty()) emptyList() else JSONArray(opsiJson).let { arr ->
                List(arr.length()) { i -> arr.getString(i) }
            }

            list.add(Soal(idSoal, isi, point, idKuis, tipe, jawaban, opsiList))
        }

        cursor.close()
        db.close()
        return list
    }
}
