package com.example.mobile_project

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_project.model.Pahlawan

class DetailActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var handler: Handler
    private var updateSeekBar: Runnable? = null
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val idPahlawan = intent.getIntExtra("idPahlawan", -1)
        if (idPahlawan == -1) {
            Toast.makeText(this, "ID pahlawan tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        dbHelper = DatabaseHelper(this)
        val pahlawan: Pahlawan? = dbHelper.getPahlawanById(idPahlawan)

        if (pahlawan == null) {
            Toast.makeText(this, "Data pahlawan tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val imgTokoh = findViewById<ImageView>(R.id.imgTokoh)
        val tvNama = findViewById<TextView>(R.id.tvNama)
        val tvDeskripsi = findViewById<TextView>(R.id.tvDeskripsi)
        val tvCerita = findViewById<TextView>(R.id.tvCerita)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        // Set data ke UI
        tvNama.text = pahlawan.namaPahlawan
        tvDeskripsi.text = "${pahlawan.julukan}\nLahir di ${pahlawan.tempatLahir} pada ${pahlawan.tanggalLahir}"

        // Ambil ID sumber daya dari nama
        var resFotoId = resources.getIdentifier(pahlawan.foto, "drawable", packageName)
        if (resFotoId == 0) {
            resFotoId = resources.getIdentifier("soekarno", "drawable", packageName)
        }
        var resCeritaId = resources.getIdentifier(pahlawan.cerita, "raw", packageName)
        if (resCeritaId == 0) {
            resCeritaId = resources.getIdentifier("soekarno", "raw", packageName)
        }
        var resAudioId = resources.getIdentifier(pahlawan.audio, "raw", packageName)
        if (resAudioId == 0) {
            resAudioId = resources.getIdentifier("soekarno_audio", "raw", packageName)
        }

        imgTokoh.setImageResource(resFotoId)
        tvCerita.text = getDetailFromRaw(resCeritaId)

        handler = Handler(Looper.getMainLooper())

        val btnSelesai = findViewById<Button>(R.id.btnSelesaiBelajar)
        btnSelesai.setOnClickListener {
            dbHelper.updatePahlawanLearned(pahlawan.idPahlawan)
            Toast.makeText(this, "Berhasil ditandai sebagai sudah belajar", Toast.LENGTH_SHORT).show()
            finish()
        }


        btnPlay.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, resAudioId)?.apply {
                    seekBar.max = duration
                    start()
                }
                btnPlay.text = "Stop Audio"
                startSeekBarUpdate(seekBar)
            } else {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.pause()
                    btnPlay.text = "Play Audio"
                    stopSeekBarUpdate()
                } else {
                    mediaPlayer?.start()
                    btnPlay.text = "Stop Audio"
                    startSeekBarUpdate(seekBar)
                }
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun startSeekBarUpdate(seekBar: SeekBar) {
        updateSeekBar = object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    seekBar.progress = it.currentPosition
                    if (it.isPlaying) {
                        handler.postDelayed(this, 500)
                    }
                }
            }
        }
        handler.post(updateSeekBar!!)
    }

    private fun stopSeekBarUpdate() {
        updateSeekBar?.let { handler.removeCallbacks(it) }
    }

    private fun getDetailFromRaw(resId: Int): String {
        return try {
            if (resId == 0) return "Cerita tidak tersedia"
            resources.openRawResource(resId).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            "Detail tidak tersedia"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        stopSeekBarUpdate()
    }
}
