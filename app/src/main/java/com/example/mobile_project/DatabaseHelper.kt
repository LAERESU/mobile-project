package com.example.mobile_project

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "mobile_project.db"
        private const val DATABASE_VERSION = 2

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
        const val COLUMN_OPSI_IS_CORRECT = "is_correct"

        // Tabel Biodata
        const val TABLE_BIODATA = "biodata"
        const val COLUMN_BIODATA_ID = "id_biodata"
        const val COLUMN_BIODATA_NAMA_LENGKAP = "nama_lengkap"
        const val COLUMN_BIODATA_NAMA_PANGGILAN = "nama_panggilan"
        const val COLUMN_BIODATA_NAMA_SEKOLAH = "nama_sekolah"
        const val COLUMN_BIODATA_KELAS = "kelas"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Tabel Pahlawan
        val createPahlawan = "CREATE TABLE $TABLE_PAHLAWAN (" +
                "$COLUMN_PAHLAWAN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PAHLAWAN_NAMA TEXT, " +
                "$COLUMN_PAHLAWAN_TEMPAT_LAHIR TEXT, " +
                "$COLUMN_PAHLAWAN_TANGGAL_LAHIR TEXT, " +
                "$COLUMN_PAHLAWAN_JULUKAN TEXT)"
        db?.execSQL(createPahlawan)

        // Tabel Peristiwa
        val createPeristiwa = "CREATE TABLE $TABLE_PERISTIWA (" +
                "$COLUMN_PERISTIWA_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PERISTIWA_ISI TEXT, " +
                "$COLUMN_PERISTIWA_LOKASI TEXT, " +
                "$COLUMN_PERISTIWA_TANGGAL TEXT, " +
                "$COLUMN_PERISTIWA_ID_MATERI INTEGER, " +
                "FOREIGN KEY ($COLUMN_PERISTIWA_ID_MATERI) REFERENCES $TABLE_MATERI($COLUMN_MATERI_ID))"
        db?.execSQL(createPeristiwa)

        // Tabel Materi
        val createMateri = "CREATE TABLE $TABLE_MATERI (" +
                "$COLUMN_MATERI_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_MATERI_NAMA TEXT, " +
                "$COLUMN_MATERI_TANGGAL_DIBUAT TEXT)"
        db?.execSQL(createMateri)

        // Tabel Kuis
        val createKuis = "CREATE TABLE $TABLE_KUIS (" +
                "$COLUMN_KUIS_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_KUIS_NAMA TEXT, " +
                "$COLUMN_KUIS_WAKTU_PENGERJAAN TEXT, " +
                "$COLUMN_KUIS_TINGKAT_KESULITAN TEXT, " +
                "$COLUMN_KUIS_ID_MATERI INTEGER, " +
                "FOREIGN KEY ($COLUMN_KUIS_ID_MATERI) REFERENCES $TABLE_MATERI($COLUMN_MATERI_ID))"
        db?.execSQL(createKuis)

        // Tabel Soal
        val createSoal = "CREATE TABLE $TABLE_SOAL (" +
                "$COLUMN_SOAL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_SOAL_ISI TEXT, " +
                "$COLUMN_SOAL_POINT INTEGER, " +
                "$COLUMN_SOAL_ID_KUIS INTEGER, " +
                "FOREIGN KEY ($COLUMN_SOAL_ID_KUIS) REFERENCES $TABLE_KUIS($COLUMN_KUIS_ID))"
        db?.execSQL(createSoal)

        // Tabel Opsi
        val createOpsi = "CREATE TABLE $TABLE_OPSI (" +
                "$COLUMN_OPSI_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_OPSI_ISI TEXT, " +
                "$COLUMN_OPSI_ID_SOAL INTEGER, " +
                "$COLUMN_OPSI_IS_CORRECT INTEGER, " +
                "FOREIGN KEY ($COLUMN_OPSI_ID_SOAL) REFERENCES $TABLE_SOAL($COLUMN_SOAL_ID))"
        db?.execSQL(createOpsi)

        // Tabel Biodata
        val createBiodata = "CREATE TABLE $TABLE_BIODATA (" +
                "$COLUMN_BIODATA_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_BIODATA_NAMA_LENGKAP TEXT, " +
                "$COLUMN_BIODATA_NAMA_PANGGILAN TEXT, " +
                "$COLUMN_BIODATA_NAMA_SEKOLAH TEXT, " +
                "$COLUMN_BIODATA_KELAS TEXT)"
        db?.execSQL(createBiodata)

        // Data contoh untuk soal dan opsi
        val sampleMateri = ContentValues().apply {
            put(COLUMN_MATERI_NAMA, "Sejarah Kemerdekaan")
            put(COLUMN_MATERI_TANGGAL_DIBUAT, "2025-06-20")
        }
        val materiId = db?.insert(TABLE_MATERI, null, sampleMateri) ?: -1

        val sampleKuis = ContentValues().apply {
            put(COLUMN_KUIS_NAMA, "Kuis Proklamasi")
            put(COLUMN_KUIS_WAKTU_PENGERJAAN, "30 menit")
            put(COLUMN_KUIS_TINGKAT_KESULITAN, "Mudah")
            put(COLUMN_KUIS_ID_MATERI, materiId)
        }
        val kuisId = db?.insert(TABLE_KUIS, null, sampleKuis) ?: -1

        val sampleSoal = ContentValues().apply {
            put(COLUMN_SOAL_ISI, "Siapa presiden pertama Indonesia?")
            put(COLUMN_SOAL_POINT, 10)
            put(COLUMN_SOAL_ID_KUIS, kuisId)
        }
        val soalId = db?.insert(TABLE_SOAL, null, sampleSoal) ?: -1

        val sampleOpsi = listOf(
            ContentValues().apply {
                put(COLUMN_OPSI_ISI, "Ir. Soekarno")
                put(COLUMN_OPSI_ID_SOAL, soalId)
                put(COLUMN_OPSI_IS_CORRECT, 1)
            },
            ContentValues().apply {
                put(COLUMN_OPSI_ISI, "Mohammad Hatta")
                put(COLUMN_OPSI_ID_SOAL, soalId)
                put(COLUMN_OPSI_IS_CORRECT, 0)
            },
            ContentValues().apply {
                put(COLUMN_OPSI_ISI, "Sutan Sjahrir")
                put(COLUMN_OPSI_ID_SOAL, soalId)
                put(COLUMN_OPSI_IS_CORRECT, 0)
            }
        )
        sampleOpsi.forEach { db?.insert(TABLE_OPSI, null, it) }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PAHLAWAN")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PERISTIWA")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MATERI")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_KUIS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SOAL")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_OPSI")
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
        val result = db.insert(TABLE_BIODATA, null, values)
        db.close()
        return result
    }

    fun isSoalSudahAda(): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_SOAL", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count > 0
    }

    fun insertSoalDanOpsi(isiSoal: String, point: Int, idKuis: Long, opsiList: List<Pair<String, Boolean>>) {
        val db = writableDatabase
        val soalValues = ContentValues().apply {
            put(COLUMN_SOAL_ISI, isiSoal)
            put(COLUMN_SOAL_POINT, point)
            put(COLUMN_SOAL_ID_KUIS, idKuis)
        }
        val soalId = db.insert(TABLE_SOAL, null, soalValues)

        opsiList.forEach {
            val opsiValues = ContentValues().apply {
                put(COLUMN_OPSI_ISI, it.first)
                put(COLUMN_OPSI_ID_SOAL, soalId)
                put(COLUMN_OPSI_IS_CORRECT, if (it.second) 1 else 0)
            }
            db.insert(TABLE_OPSI, null, opsiValues)
        }
        db.close()
    }

    fun getSoalDanOpsi(): List<SoalPage.Soal> {
        val db = readableDatabase
        val list = mutableListOf<SoalPage.Soal>()
        val query = "SELECT * FROM $TABLE_SOAL"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val idSoal = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SOAL_ID))
            val isi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOAL_ISI))
            val point = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SOAL_POINT))

            val opsiCursor = db.rawQuery(
                "SELECT * FROM $TABLE_OPSI WHERE $COLUMN_OPSI_ID_SOAL = ?",
                arrayOf(idSoal.toString())
            )

            val opsiList = mutableListOf<String>()
            var jawabanBenar = ""
            while (opsiCursor.moveToNext()) {
                val isiOpsi = opsiCursor.getString(opsiCursor.getColumnIndexOrThrow(COLUMN_OPSI_ISI))
                val isCorrect = opsiCursor.getInt(opsiCursor.getColumnIndexOrThrow(COLUMN_OPSI_IS_CORRECT)) == 1
                opsiList.add(isiOpsi)
                if (isCorrect) jawabanBenar = isiOpsi
            }
            opsiCursor.close()

            list.add(SoalPage.Soal(isi, opsiList, "pilihan", jawabanBenar))
        }
        cursor.close()
        db.close()
        return list
    }


}