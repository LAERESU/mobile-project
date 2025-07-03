package com.example.mobile_project

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mobile_project.model.Kuis
import com.example.mobile_project.model.Pahlawan
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
        const val COLUMN_PAHLAWAN_TOKOH = "tokoh"
        const val COLUMN_PAHLAWAN_FOTO = "foto"
        const val COLUMN_PAHLAWAN_CERITA = "cerita"
        const val COLUMN_PAHLAWAN_AUDIO = "audio"
        const val COLUMN_PAHLAWAN_IS_LEARNED = "is_learned"

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
                $COLUMN_PAHLAWAN_JULUKAN TEXT,
                $COLUMN_PAHLAWAN_TOKOH TEXT,
                $COLUMN_PAHLAWAN_FOTO TEXT,
                $COLUMN_PAHLAWAN_CERITA TEXT,
                $COLUMN_PAHLAWAN_AUDIO TEXT,
                $COLUMN_PAHLAWAN_IS_LEARNED INTEGER
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

    fun insertPahlawan(pahlawan: Pahlawan): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PAHLAWAN_NAMA, pahlawan.namaPahlawan)
            put(COLUMN_PAHLAWAN_TEMPAT_LAHIR, pahlawan.tempatLahir)
            put(COLUMN_PAHLAWAN_TANGGAL_LAHIR, pahlawan.tanggalLahir)
            put(COLUMN_PAHLAWAN_JULUKAN, pahlawan.julukan)
            put(COLUMN_PAHLAWAN_TOKOH, pahlawan.tokoh)
            put(COLUMN_PAHLAWAN_FOTO, pahlawan.foto)
            put(COLUMN_PAHLAWAN_CERITA, pahlawan.cerita)
            put(COLUMN_PAHLAWAN_AUDIO, pahlawan.audio)
            put(COLUMN_PAHLAWAN_IS_LEARNED, pahlawan.isLearned)
        }
        return db.insert(TABLE_PAHLAWAN, null, values).also { db.close() }


    }

    fun getAllPahlawan(): List<Pahlawan> {
        val list = mutableListOf<Pahlawan>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PAHLAWAN", null)

        if (cursor.moveToFirst()) {
            do {
                val pahlawan = Pahlawan(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_NAMA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_TEMPAT_LAHIR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_TANGGAL_LAHIR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_JULUKAN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_TOKOH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_FOTO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_CERITA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_AUDIO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_IS_LEARNED))
                )
                list.add(pahlawan)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }



    fun getPahlawanById(id: Int): Pahlawan? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PAHLAWAN WHERE $COLUMN_PAHLAWAN_ID = ?", arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_NAMA))
            val tempatLahir = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_TEMPAT_LAHIR))
            val tanggalLahir = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_TANGGAL_LAHIR))
            val julukan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_JULUKAN))
            val tokoh = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_TOKOH))
            val foto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_FOTO))
            val cerita = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_CERITA))
            val audio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_AUDIO))
            val isLearned = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PAHLAWAN_IS_LEARNED))
            cursor.close()
            db.close()
            return Pahlawan(id, nama, tempatLahir, tanggalLahir, julukan, tokoh, foto, cerita, audio, isLearned)
        } else {
            cursor.close()
            db.close()
            return null
        }
    }
    fun updatePahlawanLearned(id: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("isLearned", 1)
        }
        db.update("pahlawan", values, "idPahlawan=?", arrayOf(id.toString()))
    }


}
